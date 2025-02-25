package ru.otus.chat.server.commands;

import ru.otus.chat.server.ClientHandler;
import ru.otus.chat.server.Server;

public interface Command {
    void execute(String message, ClientHandler client, Server server);
}
