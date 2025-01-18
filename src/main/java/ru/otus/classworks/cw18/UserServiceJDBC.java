package main.java.ru.otus.classworks.cw18;

import java.util.List;

public interface UserServiceJDBC {

    List<User> getAll();

    boolean isAdmin(int userID);
}
