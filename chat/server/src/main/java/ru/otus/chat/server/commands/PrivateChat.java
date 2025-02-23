package ru.otus.chat.server.commands;

import ru.otus.chat.server.ClientHandler;
import ru.otus.chat.server.Server;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PrivateChat implements Command {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void execute(String message, ClientHandler client, Server server) {
        String[] startMessage = message.split(" ", 3);
        if (startMessage[0].equalsIgnoreCase("/w")) {
            server.privateMessage(startMessage[1], LocalDateTime.now().format(formatter) + " " + client.getUsername() + ": " + startMessage[2]);
        }
    }
}
