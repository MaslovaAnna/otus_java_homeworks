package ru.otus.chat.server;

import java.time.LocalDateTime;

public interface AuthenticatedProvider {
    void initialize();

    boolean authenticate(ClientHandler clientHandler, String login, String password);

    boolean registration(ClientHandler clientHandler, String login, String password, String username);

    boolean checkRoleAdmin(String username);

    boolean changeUsername(ClientHandler clientHandler, String newUsername);

    boolean checkRoleManager(String username);

    boolean checkPermBan(String username);

    LocalDateTime checkBanTime(String username);

    boolean setBan(ClientHandler clientHandler, String username, int time);
    boolean unBan(ClientHandler clientHandler, String username);

    boolean createRoom(ClientHandler client, String name, String password);

    boolean enterRoom(ClientHandler client,String name, String password);
}
