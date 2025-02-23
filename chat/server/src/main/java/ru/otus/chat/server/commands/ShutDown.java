package ru.otus.chat.server.commands;

import ru.otus.chat.server.ClientHandler;
import ru.otus.chat.server.Server;

public class ShutDown implements Command {

    @Override
    public void execute(String message, ClientHandler client, Server server) {
        String[] startMessage = message.split(" ", 2);
        if ((server.getAuthenticatedProvider()
                .checkRoleAdmin(client.getUsername()))) {
            if (startMessage.length > 1) {
                client.sendMsg("Неверный формат команды /shutdown");
            } else {
                server.broadcastMessage("Сервер закрыт", null);
                server.shutDown();
            }
        }
    }
}

