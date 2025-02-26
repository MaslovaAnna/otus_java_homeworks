package ru.otus.chat.server;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

class UserServiceJDBC {

    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5435/otus";
    private static final String GET_USERNAME_QUERY = """
            SELECT username
            FROM users
            WHERE login = ? and password = ?;
            """;
    private static final String GET_MAX_USER_ID_QUERY = """
            SELECT max(id)
            FROM users
            """;
    private static final String GET_USER_ID_QUERY = """
            SELECT id
            FROM users
            WHERE username = ?;
            """;
    private static final String GET_USER_BAN_QUERY = """
            SELECT bantime
            FROM users
            WHERE username = ?;
            """;
    private static final String INSERT_USER_QUERY = """
            INSERT INTO users
            (id, "password", login, username)
            VALUES(?, ?, ?, ?)
            """;
    private static final String UPDATE_USERNAME_QUERY = """
            UPDATE users
            SET username = ?
            WHERE id = ?
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
    private static final String GET_ROLE_QUERY = """
            SELECT role
            FROM users
            WHERE username = ?;
            """;
    private static final String SET_USER_BAN_QUERY = """
            UPDATE users
            SET bantime = ?
            WHERE username = ?;
            """;
    private static final String UNBAN_QUERY = """
            UPDATE users
            SET bantime = null
            WHERE username = ?;
            """;
    private static final String ROOMS_QUERY = """
            SELECT r.id, u.username, r.name, r."password",
            FROM rooms r
            join users u on u.id = r.owner_id
            where r.is_deleted = false;
            """;
    private static final String ROOMS_COUNT_QUERY = """
            SELECT count(r.id)
            FROM rooms r
            join users u on u.id = r.owner_id
            where u.username = ?;
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
            UPDATE users
            set role = ?
            where username = ?;
            """;
    private static final String ENTER_ROOM_QUERY = """
            INSERT INTO user_to_room
            (user_id, room_id)
            VALUES(?, ?);
            """;
    private static final String OUT_ROOM_QUERY = """
            DELETE
            FROM user_to_room
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

    public String getUsernameByLoginAndPassword(String login, String password) {
        String username = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_USERNAME_QUERY)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(1, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    username = resultSet.getString("username");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return username;
    }

    public int getUderIdByUsername(String username) {
        int userId = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_ID_QUERY)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    userId = resultSet.getInt("id");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return userId;
    }

    public LocalDateTime getUserBan(String username) {
        LocalDateTime bantime = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BAN_QUERY)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    bantime = resultSet.getTimestamp("bantime").toLocalDateTime();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return bantime;
    }

    public int getMaxUserId() {
        int id = 0;
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(GET_MAX_USER_ID_QUERY)) {
                while (resultSet.next()) {
                    id = resultSet.getInt("id");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id;
    }

    public String getRole(String username) {
        String role = "";
        try (PreparedStatement ps = connection.prepareStatement(GET_ROLE_QUERY)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    role = rs.getString(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }


    public void addUser(int id, String username, String login, String password) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT_USER_QUERY)) {
            ps.setInt(1, id);
            ps.setString(4, username);
            ps.setString(3, login);
            ps.setString(2, password);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void changeUsername(String username, int id) {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE_USERNAME_QUERY)) {
            ps.setString(1, username);
            ps.setInt(2, id);
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

    public void setBanTime(String username, LocalDateTime banTime) {
        try (PreparedStatement ps = connection.prepareStatement(SET_USER_BAN_QUERY)) {
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

    public boolean getUserRole(int userId, int roleId) {
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

    public void setRole(String username, String role) {
        try (PreparedStatement ps = connection.prepareStatement(SET_ROLE_QUERY)) {
            ps.setString(1, role);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Room> getRooms() {
        List<Room> allRooms = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(ROOMS_QUERY)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String username = resultSet.getString("username");
                    String name = resultSet.getString("name");
                    String password = resultSet.getString("password");
                    Room room = new Room(name, id, password, username);
                    allRooms.add(room);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allRooms;
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
        try (PreparedStatement ps = connection.prepareStatement(CHECK_USER_ROOM_QUERY)) {
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
