package ru.otus.chat.server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class UserServiceJDBC{

    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5435/otus";
    private static final String USERS_QUERY = "select * from users;";
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

    private final Connection connection;

    public UserServiceJDBC() throws SQLException {
        this.connection = DriverManager.getConnection(DATABASE_URL, "admin", "password");
    }

    public List<ru.otus.chat.server.User> getAll() {
        List<User> allUsers = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(USERS_QUERY)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String username = resultSet.getString("username");
                    String login = resultSet.getString("login");
                    String password = resultSet.getString("password");
                    User user = new User(id, username, login, password);
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

    public boolean isAdmin(String username) {
        int flag = 0;
        try (PreparedStatement ps = connection.prepareStatement(IS_ADMIN_QUERY)) {
            ps.setString(4, username);
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

    public void addUser(int id,String username, String login, String password){
        try(PreparedStatement ps = connection.prepareStatement(INSERT_QUERY)) {
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
        try(PreparedStatement ps = connection.prepareStatement(UPDATE_USERNAME_QUERY)) {
            ps.setString(1, username);
            ps.setString(2, login);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
