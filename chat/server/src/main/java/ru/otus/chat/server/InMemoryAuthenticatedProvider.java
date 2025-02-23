package ru.otus.chat.server;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class InMemoryAuthenticatedProvider implements AuthenticatedProvider {

    private List<User> users;
    private List<Room> rooms;
    private Server server;
    private UserServiceJDBC userServiceJDBC = new UserServiceJDBC();

    public InMemoryAuthenticatedProvider(Server server) throws SQLException {
        this.server = server;
        this.users = userServiceJDBC.getAllUsers();
        this.rooms = userServiceJDBC.getAllRooms();
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

    private boolean checkIsDeletedRoom(String name, String password) {
        for (Room r : rooms) {
            if (r.getName().equals(name) && r.getPassword().equals(password)) {
                return r.isDeleted();
            }
        }
        return true;
    }

    private boolean checkNamePasswordRoom(String name, String password) {
        for (Room r : rooms) {
            if (r.getName().equals(name) && r.getPassword().equals(password)) {
                return true;
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
            }
        }
        userServiceJDBC.changeUsername(newUsername, login);
        clientHandler.sendMsg("/changenick " + newUsername);
        return true;
    }

    public boolean checkRoleManager(String username) {
        return userServiceJDBC.isManager(username);
    }

    public LocalDateTime checkBanTime(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                System.out.println("ban " +u.getBanTime());
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
                if (time == -1) {
                    u.setPermBan(true);
                    userServiceJDBC.setPermBan(username);
                    clientHandler.sendMsg("/banok " + username + " навсегда");
                    return true;
                } else if (time >= 0){
                    u.setBanTime(LocalDateTime.now().plusMinutes(time));
                    userServiceJDBC.setBanTime(username, LocalDateTime.now().plusMinutes(time));
                    clientHandler.sendMsg("/banok " + username + " на (мин)" + time);
                    return true;
                } else return false;

            }
        }
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
        return false;
    }

    public boolean createRoom(ClientHandler clientHandler, String name, String password) {
        if (name.length() < 3) {
            clientHandler.sendMsg("Название комнаты 3+ символа");
            return false;
        }
        if (isRoomNameAlreadyExists(name)) {
            clientHandler.sendMsg("Указанный логин уже занят");
            return false;
        }
        if (isTooMuchRooms(clientHandler.getUsername())) {
            clientHandler.sendMsg("Достигнуто предельное количество комнат на пользователя");
            return false;
        }
        int maxId = 0;
        for (Room r : rooms) {
            if (r.getId() > maxId) {
                maxId = r.getId();
            }
        }
        userServiceJDBC.addRoom(maxId + 1, name, password, clientHandler.getUsername());
        clientHandler.sendMsg("/createok " + name);
        return true;
    }

    public boolean isTooMuchRooms(String owner) {
        return userServiceJDBC.roomsCount(owner);
    }

    public boolean enterRoom(ClientHandler clientHandler, String name, String password) {
        if (checkIsDeletedRoom(name, password)) {
            clientHandler.sendMsg("Комната удалена");
            return false;
        } else if (!checkNamePasswordRoom(name, password)) {
            clientHandler.sendMsg("Неверный название/пароль");
            return false;
        }
        clientHandler.sendMsg("/enterok " + name);
        clientHandler.setRoom(name);
        return true;
    }



}
