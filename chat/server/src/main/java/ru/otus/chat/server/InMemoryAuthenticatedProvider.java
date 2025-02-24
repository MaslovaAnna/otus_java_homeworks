package ru.otus.chat.server;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InMemoryAuthenticatedProvider implements AuthenticatedProvider {

    private List<User> users;
    private List<Room> rooms;
    private List<Role> roles;
    private Server server;
    private UserServiceJDBC userServiceJDBC = new UserServiceJDBC();

    public InMemoryAuthenticatedProvider(Server server) throws SQLException {
        this.server = server;
        this.users = userServiceJDBC.getAllUsers();
        this.rooms = userServiceJDBC.getAllRooms();
        this.roles = userServiceJDBC.getAllRoles();
    }

    public List<Room> getRooms() {
        return rooms;
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

    private int getUserIdByUsername(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u.getId();
            }
        }
        return 0;
    }

    private int getRoomIdByRoomName(String roomname) {
        for (Room r : rooms) {
            if (r.getName().equals(roomname)) {
                return r.getId();
            }
        }
        return 0;
    }

    private int getRoleIdByRoleName(String role) {
        for (Role r : roles) {
            if ((r.getName().equals(role))) {
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

    public List<String> actualRooms () {
        List<String> roomNames = new ArrayList<>();
        for (Room r : rooms) {
            if (!r.isDeleted()) {
                roomNames.add(r.getName());
            }
        }
        return roomNames;
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
        return userServiceJDBC.checkLogin(login);
    }

    private boolean isRoomNameAlreadyExists(String login) {
        return userServiceJDBC.checkRoomName(login);
    }


    private boolean isUsernameAlreadyExists(String username) {
        return userServiceJDBC.checkUsername(username);
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
        int maxId = 0;
        for (User u : users) {
            if (u.getId() > maxId) {
                maxId = u.getId();
            }
        }
        userServiceJDBC.addUser(maxId + 1, username, login, password);
        userServiceJDBC.setRole(maxId + 1, 2);
        User u = new User(maxId + 1, username, login, password);
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(2, "user"));
        u.setRoles(roles);
        users.add(u);
        clientHandler.setUsername(username);
        server.subscribe(clientHandler);
        clientHandler.sendMsg("/regok " + username);
        return true;
    }

    @Override
    public boolean changeUsername(ClientHandler clientHandler, String newUsername) {
        String login = "";
        if (isUsernameAlreadyExists(newUsername)) {
            clientHandler.sendMsg("Указанное имя пользователя уже занято");
            return false;
        }
        for (User u : users) {
            if (u.getUsername().equals(clientHandler.getUsername())) {
                login = u.getLogin();
                clientHandler.setUsername(newUsername);
                u.setUsername(newUsername);
            }
        }
        userServiceJDBC.changeUsername(newUsername, login);
        clientHandler.sendMsg("/changenickok " + newUsername);
        return true;
    }

    public boolean checkRoleManager(String username) {
        return userServiceJDBC.isManager(username);
    }

    public LocalDateTime checkBanTime(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u.getBanTime();
            }
        }
        return null;
    }

    public boolean checkPermBan(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u.isPermBan();
            }
        }
        return false;
    }

    public boolean setBan(ClientHandler clientHandler, String username, int time) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                if (time == -1 && checkRoleAdmin(clientHandler.getUsername())) {
                    u.setPermBan(true);
                    userServiceJDBC.setPermBan(username);
                    clientHandler.sendMsg("/banok " + username + " навсегда");
                    return true;
                } else if (time >= 0 && checkRoleManager(clientHandler.getUsername()) && checkRoleManager(username)) {
                    u.setBanTime(LocalDateTime.now().plusMinutes(time));
                    userServiceJDBC.setBanTime(username, LocalDateTime.now().plusMinutes(time));
                    clientHandler.sendMsg("/banok " + username + " на (мин)" + time);
                    return true;
                } else {
                    clientHandler.sendMsg("Недостаточно прав");
                    return false;
                }
            }
        }
        clientHandler.sendMsg("Пользователь не найден");
        return false;
    }
    public boolean unBan(ClientHandler clientHandler, String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                u.setPermBan(false);
                u.setBanTime(null);
                userServiceJDBC.unBan(username);
                clientHandler.sendMsg("/unbanok " + username);
                return true;
            }
        }
        clientHandler.sendMsg("Пользователь не найден");
        return false;
    }

    public boolean createRoom(ClientHandler clientHandler, String name, String password) {
        if (name.length() < 3) {
            clientHandler.sendMsg("Название комнаты 3+ символа");
            return false;
        }
        if (isRoomNameAlreadyExists(name)&&!checkIsDeletedRoom(name)) {
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
        int ownerId = 0;
        User owner = null;
        for (User u: users) {
            if (u.getUsername().equals(clientHandler.getUsername())) {
                ownerId = u.getId();
                owner = u;
            }
        }
        userServiceJDBC.addRoom(maxRoomId + 1, name, password, ownerId);
        rooms.add(new Room( name,maxRoomId + 1, password, owner));
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
        userServiceJDBC.enterRoom(getUserIdByUsername(clientHandler.getUsername()),getRoomIdByRoomName(name));
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
        for (Room r :rooms) {
            if (r.getName().equals(roomname)) {
                return r.getOwner().getUsername().equals(username);
            }
        }
        return false;
    }

    public boolean deleteRoom (ClientHandler clientHandler, String name) {
        if (checkIsDeletedRoom(name)) {
            clientHandler.sendMsg("Комната уже удалена или не существует");
            return false;
        }
            if (checkRoomOwner(clientHandler.getUsername(), name) || checkRoleManager(clientHandler.getUsername())) {
                userServiceJDBC.deleteRoom(name);
                for (Room r: rooms) {
                    if (r.getName().equals(name)) {
                        r.setDeleted(true);
                    }
                }
                server.broadcastMessage("Удалена комната " + name, "server");
                return true;
            } else clientHandler.sendMsg("Недостаточно прав");
            return false;
        }

    private boolean checkUserRole (int userId, int roleId) {
        return userServiceJDBC.checkUserRole(userId, roleId);
    }
    public boolean setRole (ClientHandler clientHandler, String userToSet, String role) {
        int userId = getUserIdByUsername(userToSet);
        int roleId = getRoleIdByRoleName(role);
        if (getRoleIdByRoleName(role) != 0 && getUserIdByUsername(userToSet) != 0) {
            if (checkRoleAdmin(clientHandler.getUsername())) {
                if (!checkUserRole(userId, roleId)) {
                    userServiceJDBC.setRole(userId, roleId);
                    for (User u: users) {
                        if (u.getId() == userId) {
                            List<Role> newroles = u.getRoles();
                            newroles.add(new Role(roleId, role));
                            u.setRoles(newroles);
                            clientHandler.sendMsg("/setroleok " + userToSet + " назначена роль: " + role);
                            return true;
                        }
                    }
                } else {
                    clientHandler.sendMsg("Указанная роль уже назначена");
                    return false;
                }
            } else {
                clientHandler.sendMsg("Недостаточно прав");
                return false;
            }
        }
        clientHandler.sendMsg("Неправильные ник/роль");
        return false;
    }

    public boolean removeRole (ClientHandler clientHandler, String userToSet, String role) {
        int userId = getUserIdByUsername(userToSet);
        int roleId = getRoleIdByRoleName(role);
        if (getRoleIdByRoleName(role) != 0 && getUserIdByUsername(userToSet) != 0 && getRoleIdByRoleName(role) != 2) {
            if (checkRoleAdmin(clientHandler.getUsername())) {
                if (checkUserRole(userId, roleId)) {
                    userServiceJDBC.removeRole(userId, roleId);
                    for (User u: users) {
                        if (u.getId() == userId) {
                            List<Role> newroles = u.getRoles();
                            newroles.remove(new Role(roleId, role));
                            u.setRoles(newroles);
                            clientHandler.sendMsg("/removeroleok " + userToSet + " убрана роль: " + role);
                            return true;
                        }
                    }
                } else {
                    clientHandler.sendMsg("Указанная роль еще не назначена");
                    return false;
                }
            } else {
                clientHandler.sendMsg("Недостаточно прав");
                return false;
            }
        }
        clientHandler.sendMsg("Неправильные ник/роль или пытаетесь удалить основную роль");
        return false;
    }

    public void updateRoomsActivity(ClientHandler clientHandler) {
        for (Room r : rooms) {
            if (r.getLastActivivtyTime().plusMinutes(5).isBefore(LocalDateTime.now())) {
                deleteRoom(clientHandler, r.getName());
            } else if (r.getName().equals(clientHandler.getRoom())) {
                r.setLastActivivtyTime(LocalDateTime.now());
            }
        }
    }
}

