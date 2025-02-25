package ru.otus.chat.server.commands;

import ru.otus.chat.server.ClientHandler;
import ru.otus.chat.server.Server;

public class Exit implements Command {
    @Override
    public void execute(String message, ClientHandler client, Server server) {
        client.sendMsg("/exitok");
        client.disconnect();
    }
}
