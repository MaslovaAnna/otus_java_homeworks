package ru.otus.chat.server.commands;

import ru.otus.chat.server.ClientHandler;
import ru.otus.chat.server.Server;

public class ShutDown implements Command {

    @Override
    public void execute(String message, ClientHandler client, Server server) {
        if ((server.getAuthenticatedProvider()
                .checkRoleAdmin(client.getUsername()))) {
            client.sendMsg("Неверный формат команды /shutdown");
            server.broadcastMessage("Сервер закрыт", null);
            server.shutDown();
        }
    }
}

