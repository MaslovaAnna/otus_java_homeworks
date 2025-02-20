package ru.otus.chat.server;

import java.sql.SQLException;
import java.util.List;

public class InMemoryAuthenticatedProvider implements AuthenticatedProvider {

    private List<User> users;
    private Server server;
    private UserServiceJDBC userServiceJDBC = new UserServiceJDBC();

    public InMemoryAuthenticatedProvider(Server server) throws SQLException {
        this.server = server;
        this.users = userServiceJDBC.getAll();
    }

    @Override
    public void initialize() {
        System.out.println("Инициализация InMemoryAuthenticatedProvider");
    }

    private String getUsernameByLoginAndPassword(String login, String password) {
        for (User u : users) {
            if (u.getLogin().equals(login) && u.getPassword().equals(password)) {
                return u.getUsername();
            }
        }
        return null;
    }

    @Override
    public boolean authenticate(ClientHandler clientHandler, String login, String password) {
        String authUsername = getUsernameByLoginAndPassword(login, password);
        if (authUsername == null) {
            clientHandler.sendMsg("Неверный логин/пароль");
            return false;
        }
        if (server.isUsernameBusy(authUsername)) {
            clientHandler.sendMsg("Указанная учетная запись уже занята");
            return false;
        }
        clientHandler.setUsername(authUsername);
        server.subscribe(clientHandler);
        clientHandler.sendMsg("/authok " + authUsername);

        return true;
    }

    private boolean isLoginAlreadyExists(String login) {
        for (User u : users) {
            if (u.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }


    private boolean isUsernameAlreadyExists(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkRoleAdmin(String username) {
        return userServiceJDBC.isAdmin(username);
    }

    @Override
    public boolean registration(ClientHandler clientHandler, String login, String password, String username) {
        if (login.length() < 3 || password.length() < 3 || username.length() < 3) {
            clientHandler.sendMsg("Логин 3+ символа, пароль 3+ символа, имя пользователя 3+ символа");
            return false;
        }
        if (isLoginAlreadyExists(login)) {
            clientHandler.sendMsg("Указанный логин уже занят");
            return false;
        }
        if (isUsernameAlreadyExists(username)) {
            clientHandler.sendMsg("Указанное имя пользователя уже занято");
            return false;
        }
        users.add(new User(users.size()+1, username, login, password));
        userServiceJDBC.addUser(users.size()+1, username, login, password);
        clientHandler.setUsername(username);
        server.subscribe(clientHandler);
        clientHandler.sendMsg("/regok " + username);
        return true;
    }

    @Override
    public boolean changeUsername(ClientHandler clientHandler, String username, String newUsername) {
        String login = "";
        if (isUsernameAlreadyExists(newUsername)) {
            clientHandler.sendMsg("Указанное имя пользователя уже занято");
            return false;
        }
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                login = u.getLogin();
            }
        }
        userServiceJDBC.changeUsername(username, login);
        return true;
    }
}
