package ru.otus.chat.server;

import java.sql.*;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

class UserServiceJDBC {

    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5435/otus";
    private static final String USERS_QUERY = "select * from users;";
    private static final String ROLES_QUERY = "select * from roles;";
    private static final String USER_ROLE_QUERY = """
            SELECT r.id, r."name"
            FROM roles r
            join user_to_role utr on r.id = utr.role_id
            WHERE user_id = ?;
            """;
    private static final String IS_ADMIN_QUERY = """
            SELECT count(1)
            FROM roles r
            join user_to_role utr on r.id = utr.role_id
            join users u on u.id = utr.user_id
            WHERE r."name" = 'admin' and u.username = ?;
            """;
    private static final String INSERT_QUERY = """
            INSERT INTO users
            (id, "password", login, username)
            VALUES(?, ?, ?, ?)
            """;
    private static final String UPDATE_USERNAME_QUERY = """
            UPDATE users
            SET username = ?
            WHERE login = ?
            """;
    private static final String CHECK_USERNAME_QUERY = """
            SELECT count(1)
            FROM users
            WHERE username = ?;
            """;
    private static final String CHECK_LOGIN_QUERY = """
            SELECT count(1)
            FROM users
            WHERE login = ?;
            """;
    private static final String IS_MANAGER_QUERY = """
            SELECT count(1)
            FROM roles r
            join user_to_role utr on r.id = utr.role_id
            join users u on u.id = utr.user_id
            WHERE r."name" = 'manager' and u.username = ?;
            """;
    private static final String SET_FOREVER_BAN_QUERY = """
            UPDATE users
            SET is_banned_forever = true
            WHERE username = ?;
            """;
    private static final String SET_BAN_TIME_QUERY = """
            UPDATE users
            SET bantime = ?
            WHERE username = ?;
            """;
    private static final String UNBAN_QUERY = """
            UPDATE users
            SET bantime = null, is_banned_forever = false
            WHERE username = ?;
            """;
    private static final String ROOMS_COUNT_QUERY = """
            SELECT count(r.id)
            FROM rooms r
            join users u on u.id = r.owner_id
            where u.username = ?;
            """;
    private static final String ROOMS_QUERY = """
            SELECT r.id, u.username, r.name, r."password", r.is_deleted
            FROM rooms r
            join users u on u.id = r.owner_id
            """;
    private static final String CHECK_ROOMNAME_QUERY = """
            SELECT count(1)
            FROM rooms
            WHERE name = ?;
            """;
    private static final String INSERT_ROOM_QUERY = """
            INSERT INTO rooms
            (id, owner_id, "password", "name", is_deleted)
            VALUES(?, ?, ?, ?, false);
            """;
    private static final String DELETE_ROOM_QUERY = """
            UPDATE rooms
            SET is_deleted = true
            WHERE name = ?;
            """;
    private static final String SET_ROLE_QUERY = """
            INSERT INTO user_to_role
            (user_id, role_id)
            VALUES(?, ?);
            """;
    private static final String CHECK_USER_ROLE_QUERY = """
            select count(1)
            FROM user_to_role
            WHERE user_id = ? and role_id = ?
            """;
    private static final String REMOVE_ROLE_QUERY = """
            DELETE
            FROM user_to_role
            WHERE user_id = ? and role_id = ?
            """;
    private static final String ENTER_ROOM_QUERY = """
            INSERT INTO user_to_room
            (user_id, room_id)
            VALUES(?, ?);
            """;
    private static final String OUT_ROOM_QUERY = """
            DELETE
            FROM user_to_role
            WHERE user_id = ? and room_id = ?
            """;
    private static final String CHECK_USER_ROOM_QUERY = """
            select count(1)
            FROM user_to_room
            WHERE user_id = ? and room_id = ?
            """;

    private final Connection connection;

    public UserServiceJDBC() throws SQLException {
        this.connection = DriverManager.getConnection(DATABASE_URL, "admin", "password");
    }

    public List<Role> getAllRoles() {
        List<Role> allRoles = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(ROLES_QUERY)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    Role role = new Role(id, name);
                    allRoles.add(role);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allRoles;
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(USERS_QUERY)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String username = resultSet.getString("username");
                    String login = resultSet.getString("login");
                    String password = resultSet.getString("password");
                    User user = new User(id, username, login, password);
                    if (resultSet.getTimestamp("bantime") != null) {
                        LocalDateTime bantime = resultSet.getTimestamp("bantime").toLocalDateTime();
                        user.setBanTime(bantime);
                    }
                    boolean permBan = resultSet.getBoolean("is_banned_forever");
                    user.setPermBan(permBan);
                    allUsers.add(user);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(USER_ROLE_QUERY)) {
            for (User u : allUsers) {
                preparedStatement.setInt(1, u.getId());
                List<Role> roleList = new ArrayList<>();
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        Role role = new Role(id, name);
                        roleList.add(role);
                    }
                    u.setRoles(roleList);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return allUsers;
    }

    public List<Room> getAllRooms() {
        List<Room> allRooms = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(ROOMS_QUERY)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String username = resultSet.getString("username");
                    String name = resultSet.getString("name");
                    String password = resultSet.getString("password");
                    boolean isDeleted = resultSet.getBoolean("is_deleted");
                    List<User> users = getAllUsers();
                    User owner = null;
                    for (User u : users) {
                        if (u.getUsername().equals(username)) {
                            owner = u;
                        }
                    }
                    Room room = new Room(name, id, password, owner);
                    room.setDeleted(isDeleted);
                    allRooms.add(room);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allRooms;
    }

    public boolean isAdmin(String username) {
        int flag = 0;
        try (PreparedStatement ps = connection.prepareStatement(IS_ADMIN_QUERY)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    flag = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag == 1;
    }

    public boolean isManager(String username) {
        int flag = 0;
        try (PreparedStatement ps = connection.prepareStatement(IS_MANAGER_QUERY)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    flag = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag == 1;
    }

    public void addUser(int id, String username, String login, String password) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT_QUERY)) {
            ps.setInt(1, id);
            ps.setString(4, username);
            ps.setString(3, login);
            ps.setString(2, password);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void changeUsername(String username, String login) {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE_USERNAME_QUERY)) {
            ps.setString(1, username);
            ps.setString(2, login);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkUsername(String username) {
        int flag = 0;
        try (PreparedStatement ps = connection.prepareStatement(CHECK_USERNAME_QUERY)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    flag = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag == 1;
    }

    public boolean checkLogin(String login) {
        int flag = 0;
        try (PreparedStatement ps = connection.prepareStatement(CHECK_LOGIN_QUERY)) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    flag = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag == 1;
    }

    public void setPermBan(String username) {
        try (PreparedStatement ps = connection.prepareStatement(SET_FOREVER_BAN_QUERY)) {
            ps.setString(1, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setBanTime(String username, LocalDateTime banTime) {
        try (PreparedStatement ps = connection.prepareStatement(SET_BAN_TIME_QUERY)) {
            System.out.println(Timestamp.valueOf(banTime));
            ps.setTimestamp(1, Timestamp.valueOf(banTime));
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void unBan(String username) {
        try (PreparedStatement ps = connection.prepareStatement(UNBAN_QUERY)) {
            ps.setString(1, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkRoomName(String name) {
        int flag = 0;
        try (PreparedStatement ps = connection.prepareStatement(CHECK_ROOMNAME_QUERY)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    flag = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag == 1;
    }

    public boolean checkUserRole(int userId, int roleId) {
        int flag = 0;
        try (PreparedStatement ps = connection.prepareStatement(CHECK_USER_ROLE_QUERY)) {
            ps.setInt(1, userId);
            ps.setInt(2, roleId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    flag = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag == 1;
    }

    public void setRole(int userId, int roleId) {
        try (PreparedStatement ps = connection.prepareStatement(SET_ROLE_QUERY)) {
            ps.setInt(1, userId);
            ps.setInt(2, roleId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeRole(int userId, int roleId) {
        try (PreparedStatement ps = connection.prepareStatement(REMOVE_ROLE_QUERY)) {
            ps.setInt(1, userId);
            ps.setInt(2, roleId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean roomsCount(String owner) {
        int flag = 0;
        try (PreparedStatement ps = connection.prepareStatement(ROOMS_COUNT_QUERY)) {
            ps.setString(1, owner);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    flag = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag > 5;
    }

    public void addRoom(int id, String name, String password, int ownerId) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT_ROOM_QUERY)) {
            ps.setInt(1, id);
            ps.setString(4, name);
            ps.setInt(2, ownerId);
            ps.setString(3, password);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRoom(String name) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_ROOM_QUERY)) {
            ps.setString(1, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void enterRoom(int userId, int roomId) {
        try (PreparedStatement ps = connection.prepareStatement(ENTER_ROOM_QUERY)) {
            ps.setInt(1, userId);
            ps.setInt(2, roomId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void outRoom(int userId, int roomId) {
        try (PreparedStatement ps = connection.prepareStatement(OUT_ROOM_QUERY)) {
            ps.setInt(1, userId);
            ps.setInt(2, roomId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkUserRoom(int userId, int roomId) {
        int flag = 0;
        try (PreparedStatement ps = connection.prepareStatement(CHECK_USER_ROLE_QUERY)) {
            ps.setInt(1, userId);
            ps.setInt(2, roomId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    flag = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag == 1;
    }
}
