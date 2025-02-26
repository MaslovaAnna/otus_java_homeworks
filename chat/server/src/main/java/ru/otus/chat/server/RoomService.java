package ru.otus.chat.server;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RoomService {

    private List<Room> rooms;
    private Server server;
    private UserServiceJDBC userServiceJDBC = new UserServiceJDBC();

    public RoomService(Server server) throws SQLException {
        this.server = server;
        this.rooms = userServiceJDBC.getRooms();
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void initialize() {
        System.out.println("Инициализация AuthenticaterService");
    }


    private int getRoomIdByRoomName(String roomname) {
        for (Room r : rooms) {
            if (r.getName().equals(roomname)) {
                return r.getId();
            }
        }
        return 0;
    }


    private boolean checkIsDeletedRoom(String name) {
        for (Room r : rooms) {
            if (r.getName().equals(name)) {
                return r.isDeleted();
            }
        }
        return true;
    }

    public List<String> actualRooms() {
        userServiceJDBC.getRooms();
    }

    private boolean checkNamePasswordRoom(String name, String password) {
        for (Room r : rooms) {
            if (r.getName().equals(name)) {
                if (r.getPassword() == null && password != null) {
                    return false;
                } else if (r.getPassword() != null && password == null) {
                    return false;
                } else if (r.getPassword() == null && password == null) {
                    return true;
                } else return r.getPassword().equals(password);
            }
        }
        return false;
    }

    private boolean isRoomNameAlreadyExists(String login) {
        return userServiceJDBC.checkRoomName(login);
    }

    public boolean createRoom(ClientHandler clientHandler, String name, String password) {
        if (name.length() < 3) {
            clientHandler.sendMsg("Название комнаты 3+ символа");
            return false;
        }
        if (isRoomNameAlreadyExists(name) && !checkIsDeletedRoom(name)) {
            clientHandler.sendMsg("Указанный логин уже занят");
            return false;
        }
        if (isTooMuchRooms(clientHandler.getUsername())) {
            clientHandler.sendMsg("Достигнуто предельное количество комнат на пользователя");
            return false;
        }
        int maxRoomId = 0;
        for (Room r : rooms) {
            if (r.getId() > maxRoomId) {
                maxRoomId = r.getId();
            }
        }
        int ownerId = userServiceJDBC.getUderIdByUsername(clientHandler.getUsername());
        userServiceJDBC.addRoom(maxRoomId + 1, name, password, ownerId);
        rooms.add(new Room(name, maxRoomId + 1, password, clientHandler.getUsername()));
        clientHandler.sendMsg("/createok " + name);
        return true;
    }

    public boolean isTooMuchRooms(String owner) {
        return userServiceJDBC.roomsCount(owner);
    }

    public boolean enterRoom(ClientHandler clientHandler, String name, String password) {
        if (userServiceJDBC.checkUserRoom(getUserIdByUsername(clientHandler.getUsername()), getRoomIdByRoomName(clientHandler.getRoom()))) {
            clientHandler.sendMsg("Вы уже находитесь в частной комнате, покиньте ее через команду /out");
            return false;
        } else if (checkIsDeletedRoom(name)) {
            clientHandler.sendMsg("Комната удалена или не существует");
            return false;
        } else if (!checkNamePasswordRoom(name, password)) {
            clientHandler.sendMsg("Неверный название/пароль");
            return false;
        }
        userServiceJDBC.enterRoom(getUserIdByUsername(clientHandler.getUsername()), getRoomIdByRoomName(name));
        clientHandler.sendMsg("/enterok " + name);
        clientHandler.setRoom(name);
        return true;
    }

    public void outRoom(ClientHandler clientHandler) {
        userServiceJDBC.outRoom(getUserIdByUsername(clientHandler.getUsername()), getRoomIdByRoomName(clientHandler.getRoom()));
        clientHandler.sendMsg("/outok " + clientHandler.getRoom());
        clientHandler.setRoom("server");
    }

    public boolean checkRoomOwner(String username, String roomname) {
        for (Room r : rooms) {
            if (r.getName().equals(roomname)) {
                return r.getOwner().getUsername().equals(username);
            }
        }
        return false;
    }

    public boolean deleteRoom(ClientHandler clientHandler, String name, boolean autoDelete) {
        if (checkIsDeletedRoom(name)) {
            clientHandler.sendMsg("Комната уже удалена или не существует");
            return false;
        }
        if (!autoDelete) {
            if (checkRoomOwner(clientHandler.getUsername(), name) || checkRoleManager(clientHandler.getUsername())) {
                for (User u : users) {
                    if (userServiceJDBC.checkUserRoom(u.getId(), getRoomIdByRoomName(name)))
                        userServiceJDBC.outRoom(u.getId(), getRoomIdByRoomName(name));
                }
                for (Room r : rooms) {
                    if (r.getName().equals(name)) {
                        r.setDeleted(true);
                    }
                }
                userServiceJDBC.deleteRoom(name);
                server.broadcastMessage("Удалена комната " + name, "server");
                return true;
            } else clientHandler.sendMsg("Недостаточно прав");
            return false;
        } else {
            for (Room r : rooms) {
                if (r.getName().equals(name)) {
                    r.setDeleted(true);
                }
            }
            for (User u : users) {
                if (userServiceJDBC.checkUserRoom(u.getId(), getRoomIdByRoomName(name)))
                    userServiceJDBC.outRoom(u.getId(), getRoomIdByRoomName(name));
            }
            userServiceJDBC.deleteRoom(name);
            server.broadcastMessage("Удалена комната " + name, "server");
            return true;
        }
    }

    public void updateRoomsActivity(ClientHandler clientHandler) {
        for (Room r : rooms) {
            if (r.getLastActivivtyTime().plusDays(7).isBefore(LocalDateTime.now())) {
                deleteRoom(clientHandler, r.getName(), true);
            } else if (r.getName().equals(clientHandler.getRoom())) {
                r.setLastActivivtyTime(LocalDateTime.now());
            }
        }
    }

    public boolean checkRoom(ClientHandler client, String room) {
        return userServiceJDBC.checkUserRoom(getUserIdByUsername(client.getUsername()), getRoomIdByRoomName(room));
    }
}

