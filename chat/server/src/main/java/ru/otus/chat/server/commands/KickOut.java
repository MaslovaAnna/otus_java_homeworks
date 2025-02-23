package ru.otus.chat.server.commands;

import ru.otus.chat.server.ClientHandler;
import ru.otus.chat.server.Server;

public class KickOut implements Command {
    @Override
    public void execute(String message, ClientHandler client, Server server) {
        String[] startMessage = message.split(" ", 3);
        if ((server.getAuthenticatedProvider()
                .checkRoleAdmin(client.getUsername()))) {
            if (startMessage.length != 2) {
                client.sendMsg("Неверный формат команды /kick");
            } else {
                server.kickOut(startMessage[1]);
                client.sendMsg("С сервера выгнан " + startMessage[1]);
            }
        }
    }
}
