package main.java.ru.otus.java.classworks.cw18;

import java.sql.SQLException;
import java.util.List;

public class App {
    public static void main(String[] args) {
        try {
            UserServiceJDBC userServiceJDBC = new UserServiceJDBCImpl();
            List<User> users = userServiceJDBC.getAll();
            System.out.println("userServiceJDBC.getAll()" + users);
            for (User u : users) {
                System.out.println("Пользователь " + u.getId() + " админ? \n" + userServiceJDBC.isAdmin(u.getId()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
/*
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.7.4</version>
</dependency>*/
