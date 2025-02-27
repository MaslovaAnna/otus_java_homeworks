package ru.otus.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private int port;
    private List<ClientHandler> clients;
    private AuthenticatedProvider authenticatedProvider;
    private ServerSocket serverSocket;

    public Server(int port) throws SQLException, IOException {
        this.port = port;
        clients = new CopyOnWriteArrayList<>();
        authenticatedProvider = new InMemoryAuthenticatedProvider(this);
        serverSocket = new ServerSocket(port);
    }

    public void start() {
        try {
            System.out.println("Сервер запущен на порту: " + port);
            authenticatedProvider.initialize();
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                new ClientHandler(socket, this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void subscribe(ClientHandler clientHandler) {

        broadcastMessage(LocalDateTime.now().format(formatter) + " " + "В чат вошел: " + clientHandler.getUsername(), clientHandler.getRoom());
        clients.add(clientHandler);
    }

    public void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        broadcastMessage(LocalDateTime.now().format(formatter) + " " + "Из чата вышел: " + clientHandler.getUsername(), clientHandler.getRoom());
    }

    public void kickOut(String username) {
        for (ClientHandler c : clients) {
            if (c.getUsername().equals(username)) {
                c.disconnect();
            }
        }
    }

    public void shutDown() {
        try {
            for (ClientHandler c : clients) {
                c.disconnect();
            }
            clients.clear();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void whoIsInRoom(String user, String roomName) {
        List<String> onlineRoomUsers = new ArrayList<>();
        for (ClientHandler c : clients) {
            if (roomName.equals(c.getRoom())) onlineRoomUsers.add(c.getUsername());
        }
            for (ClientHandler c : clients) {
                if (user.equals(c.getUsername())) c.sendMsg("В комнате " + roomName + " в сети: " + onlineRoomUsers);
        }
    }

    public void whoIsOnline(String user) {
        List<String> onlineUsers = new ArrayList<>();
        for (ClientHandler c : clients) {
            onlineUsers.add(c.getUsername());
        }
        for (ClientHandler c : clients) {
                if (user.equals(c.getUsername())) {
                    c.sendMsg("В сети: " + onlineUsers);
                }
            }
    }

    public void isOnline(String username, String checkUsername, String roomName) {
        List<String> onlineUsers = new ArrayList<>();
        for (ClientHandler c : clients) {
            onlineUsers.add(c.getUsername());
        }
        for (ClientHandler c : clients) {
            if (!(roomName == null)) {
                if (username.equals(c.getUsername()) && roomName.equals(c.getRoom())) {
                    if (onlineUsers.contains(checkUsername)) {
                        c.sendMsg(checkUsername + " сейчас в комнате " + roomName);
                    } else
                        c.sendMsg(checkUsername + " сейчас не в комнате " + roomName);
                }
            } else {
                if (username.equals(c.getUsername())) {
                    if (onlineUsers.contains(checkUsername)) {
                        c.sendMsg(checkUsername + " сейчас в сети");
                    } else
                        c.sendMsg(checkUsername + " сейчас не в сети");
                }
            }
        }
    }


    public void broadcastMessage(String message, String room) {
        for (ClientHandler c : clients) {
            if (c.getRoom().equals(room)) {
                c.sendMsg(c.getRoom() + ": " + message);
            }
        }
    }

    public boolean isUsernameBusy(String username) {
        for (ClientHandler c : clients) {
            if (c.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public AuthenticatedProvider getAuthenticatedProvider() {
        return authenticatedProvider;
    }

    public void privateMessage(String user, String message) {
        for (ClientHandler c : clients) {
            if (user.equals(c.getUsername())) {
                c.sendMsg(message);
            }
        }
    }


}
