package ru.otus.chat.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private Scanner scanner;

    public Client() throws IOException {
        scanner = new Scanner(System.in);
        socket = new Socket("localhost", 8189);
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());

        new Thread(() -> {
            try {
                while (true) {
                    String message = in.readUTF();
                    if (message.startsWith("/")) {
                        if (message.equalsIgnoreCase("/exitok")) {
                            break;
                        }
                        if (message.startsWith("/authok ")) {
                            System.out.println("Удалось успешно войти в чат с ником "
                                    + message.split(" ")[1]);
                        }
                        if (message.startsWith("/regok ")) {
                            System.out.println("Удалось успешно зарегистрироваться с ником "
                                    + message.split(" ")[1]);
                        }
                        if (message.startsWith("/changenickok ")) {
                            System.out.println("Удалось успешно сменить ник на "
                                    + message.split(" ")[1]);
                        }
                        if (message.startsWith("/banok ")) {
                            System.out.println("Заблокирован пользователь "
                                    + message.split(" ", 2)[1]);
                        }
                        if (message.startsWith("/unbanok ")) {
                            System.out.println("Разблокирован пользователь "
                                    + message.split(" ")[1]);
                        }
                        if (message.startsWith("/createok ")) {
                            System.out.println("Удалось успешно создать комнату "
                                    + message.split(" ")[1]);
                        }
                        if (message.startsWith("/enterok ")) {
                            System.out.println("Удалось успешно войти в комнату "
                                    + message.split(" ")[1]);
                        }
                        if (message.startsWith("/deleteok ")) {
                            System.out.println("Удалось успешно удалить комнату "
                                    + message.split(" ")[1]);
                        }
                        if (message.startsWith("/setroleok ")) {
                            System.out.println("Успешно пользователю "
                                    + message.split(" ", 2)[1]);
                        }
                    } else {
                        System.out.println(message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                disconnect();
            }
        }).start();

        while (true) {
            String message = scanner.nextLine();
            out.writeUTF(message);
            if (message.equalsIgnoreCase("/exit")) {
                break;
            }
        }
    }

    public void disconnect() {
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
}
