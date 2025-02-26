package ru.otus.chat.server;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class AdministrationService {

    private Server server;
    private UserServiceJDBC userServiceJDBC = new UserServiceJDBC();

    public AdministrationService(Server server) throws SQLException {
        this.server = server;
    }

    public void initialize() {
        System.out.println("Инициализация AuthenticaterService");
    }

    public String getRole(String username) {
        return userServiceJDBC.getRole(username);
    }

    public LocalDateTime checkBanTime(String username) {
        return userServiceJDBC.getUserBan(username);
    }

    public void setBan(ClientHandler clientHandler, String username, int time) {
        if (!userServiceJDBC.checkUsername(username)) {
            clientHandler.sendMsg("Пользователь не найден");
            return;
        }
        if (time == -1 && clientHandler.getRole().equals("admin")) {
            userServiceJDBC.setBanTime(username, LocalDateTime.of(9999, 1, 1, 1, 1, 1, 1));
            clientHandler.sendMsg("/banok " + username + " навсегда");
            server.kickOut(username);
            return;
        }
        if (time >= 0 && (clientHandler.getRole().equals("manager") || clientHandler.getRole().equals("admin"))) {
            userServiceJDBC.setBanTime(username, LocalDateTime.now().plusMinutes(time));
            clientHandler.sendMsg("/banok " + username + " на (мин)" + time);
            server.kickOut(username);
            return;
        }
        clientHandler.sendMsg("Недостаточно прав");
    }

    public void unBan(ClientHandler clientHandler, String username) {
        if (!(clientHandler.getRole().equals("manager") || clientHandler.getRole().equals("admin"))) {
            clientHandler.sendMsg("Недостаточно прав");
            return;
        }
        if (!userServiceJDBC.checkUsername(username)) {
            clientHandler.sendMsg("Пользователь не найден");
            return;
        }
        userServiceJDBC.unBan(username);
        clientHandler.sendMsg("/unbanok " + username);
    }

    public void setRole(ClientHandler clientHandler, String userToSet, String role) {
        if (!getRole(clientHandler.getUsername()).equals("admin")) {
            clientHandler.sendMsg("Недостаточно прав");
            return;
        }
        if (!userServiceJDBC.checkUsername(userToSet)) {
            clientHandler.sendMsg("Пользователь не найден");
            return;
        }

        if (getRole(userToSet).equals(role)) {
            clientHandler.sendMsg("Указанная роль уже назначена");
            return;
        }
        userServiceJDBC.setRole(userToSet, role);
        clientHandler.sendMsg("/setroleok " + userToSet + " назначена роль: " + role);
    }

    public void removeRole(ClientHandler clientHandler, String userToSet) {
        if (!getRole(clientHandler.getUsername()).equals("admin")) {
            clientHandler.sendMsg("Недостаточно прав");
            return;
        }
        if (!userServiceJDBC.checkUsername(userToSet)) {
            clientHandler.sendMsg("Пользователь не найден");
            return;
        }
        if (getRole(userToSet).equals("user")) {
            clientHandler.sendMsg("Указанная роль уже назначена");
            return;
        }
        userServiceJDBC.setRole(userToSet, "user");
        clientHandler.sendMsg("/setroleok " + userToSet + " назначена роль: user");
    }
}