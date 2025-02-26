package ru.otus.chat.server;

import java.sql.SQLException;

public class AuthenticaterService {

    private Server server;
    private UserServiceJDBC userServiceJDBC = new UserServiceJDBC();

    public AuthenticaterService(Server server) throws SQLException {
        this.server = server;
    }

    public void initialize() {
        System.out.println("Инициализация AuthenticaterService");
    }

    private String getUsernameByLoginAndPassword(String login, String password) {
        return userServiceJDBC.getUsernameByLoginAndPassword(login, password);
    }

    public boolean authenticate(ClientHandler clientHandler, String login, String password) {
        String authUsername = getUsernameByLoginAndPassword(login, password);
        if (authUsername.isEmpty()) {
            clientHandler.sendMsg("Неверный логин/пароль");
            return false;
        }
        if (userServiceJDBC.checkUsername(authUsername)) {
            clientHandler.sendMsg("Указанная учетная запись уже занята");
            return false;
        }
        clientHandler.setUsername(authUsername);
        clientHandler.setRole(userServiceJDBC.getRole(authUsername));
        server.subscribe(clientHandler);
        clientHandler.sendMsg("/authok " + authUsername);
        return true;
    }

    private boolean isLoginAlreadyExists(String login) {
        return userServiceJDBC.checkLogin(login);
    }

    private boolean isUsernameAlreadyExists(String username) {
        return userServiceJDBC.checkUsername(username);
    }

    public void registration(ClientHandler clientHandler, String login, String password, String username) {
        if (login.length() < 3 || password.length() < 3 || username.length() < 3) {
            clientHandler.sendMsg("Логин 3+ символа, пароль 3+ символа, имя пользователя 3+ символа");
            return;
        }
        if (isLoginAlreadyExists(login)) {
            clientHandler.sendMsg("Указанный логин уже занят");
            return;
        }
        if (isUsernameAlreadyExists(username)) {
            clientHandler.sendMsg("Указанное имя пользователя уже занято");
            return;
        }
        userServiceJDBC.addUser(userServiceJDBC.getMaxUserId() + 1, username, login, password);
        clientHandler.setUsername(username);
        server.subscribe(clientHandler);
        clientHandler.sendMsg("/regok " + username);
    }

    public boolean changeUsername(ClientHandler clientHandler, String newUsername) {
        if (isUsernameAlreadyExists(newUsername)) {
            clientHandler.sendMsg("Указанное имя пользователя уже занято");
            return false;
        }
        userServiceJDBC.changeUsername(newUsername, userServiceJDBC.getUderIdByUsername(clientHandler.getUsername()));
        clientHandler.sendMsg("/changenickok " + newUsername);
        return true;
    }

}



