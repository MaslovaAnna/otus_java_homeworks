package main.java.ru.otus.java.classworks.cw18;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceJDBCImpl implements UserServiceJDBC {

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
            WHERE r."name" = 'admin' and user_id = ?;
            """;

    private final Connection connection;

    public UserServiceJDBCImpl() throws SQLException {
        this.connection = DriverManager.getConnection(DATABASE_URL, "admin", "password");
    }

    @Override
    public List<User> getAll() {
        List<User> allUsers = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(USERS_QUERY)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String email = resultSet.getString(3);
                    String password = resultSet.getString(2);
                    User user = new User(id, email, password);
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

    @Override
    public boolean isAdmin(int userID) {
        int flag = 0;
        try (PreparedStatement ps = connection.prepareStatement(IS_ADMIN_QUERY)) {
            ps.setInt(1, userID);
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
