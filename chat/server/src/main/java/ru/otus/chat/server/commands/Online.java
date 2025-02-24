package ru.otus.chat.server.commands;

import ru.otus.chat.server.ClientHandler;
import ru.otus.chat.server.Server;

public class Online implements Command {
    @Override
    public void execute(String message, ClientHandler client, Server server) {
        String[] startMessage = message.split(" ", 3);
        if (startMessage.length > 2) {
            client.sendMsg("Неверный формат команды /online");
        } else if (startMessage.length == 2) {
            server.isOnline(client.getUsername(), startMessage[1], null);
        } else server.whoIsOnline(client.getUsername());
    }
}
