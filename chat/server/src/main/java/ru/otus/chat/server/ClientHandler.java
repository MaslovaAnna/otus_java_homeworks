package ru.otus.chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class ClientHandler {
    private Socket socket;
    private Server server;
    private DataInputStream in;
    private DataOutputStream out;
    private String username;
    private Dispatcher dispatcher;
    private String room = null;

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public ClientHandler(Socket socket, Server server) throws IOException {
        this.socket = socket;
        this.server = server;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
        this.dispatcher = new Dispatcher();

        new Thread(() -> {
            try {
                System.out.println("Клиент подключился на порту: " + socket.getPort());
                LocalTime currentTime = LocalTime.now();
                if (username == null) {
                    sendMsg("Для начала работы надо пройти аутентификацию. Формат команды /auth login password \n" +
                            "или регистрацию. Формат команды /reg login password username ");
                }
                while (true) {//цикл
                    String message = in.readUTF();
                    if ((currentTime.plusMinutes(10)).isBefore(LocalTime.now())) {
                        sendMsg("Слишком долгое ожидание активности, вы покинули чат");
                        disconnect();
                        break;
                    }
                        currentTime = LocalTime.now();
                        dispatcher.execute(message, this, server);
                    }

        } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendMsg(String message) {
        try {
                out.writeUTF(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void disconnect() {
        server.unsubscribe(this);
        try {
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
