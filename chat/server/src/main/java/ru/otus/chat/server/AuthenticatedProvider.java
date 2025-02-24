package ru.otus.chat.server;

import java.time.LocalDateTime;
import java.util.List;

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
    boolean deleteRoom (ClientHandler clientHandler, String name);
    boolean checkRoomOwner(String username, String roomname);
    List<String> actualRooms();

    boolean setRole(ClientHandler clientHandler, String userToSet, String role);
    boolean removeRole (ClientHandler clientHandler, String userToSet, String role);
}
