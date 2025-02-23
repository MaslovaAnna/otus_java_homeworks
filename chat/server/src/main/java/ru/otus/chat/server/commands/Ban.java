package ru.otus.chat.server.commands;

import ru.otus.chat.server.ClientHandler;
import ru.otus.chat.server.Server;

import static java.lang.Float.NaN;

public class Ban implements Command {
    @Override
    public void execute(String message, ClientHandler client, Server server) {
        String[] startMessage = message.split(" ", 4);
        if (server.getAuthenticatedProvider()
                .checkRoleManager(client.getUsername())) {
            if (startMessage.length != 3 || Integer.parseInt(startMessage[2]) == NaN) {
                client.sendMsg("Неверный формат команды /ban, для бана пользователя навечно введите -1");
            } else if (!server.getAuthenticatedProvider()
                    .checkRoleAdmin(client.getUsername()) && server.getAuthenticatedProvider()
                    .checkRoleAdmin(startMessage[1])) {
                client.sendMsg("Недостаточно прав");
            } else if (server.getAuthenticatedProvider().setBan(client, startMessage[1], Integer.parseInt(startMessage[2]))) {
                    server.kickOut(startMessage[1]);
                }
            } else client.sendMsg("Недостаточно прав");
        }
    }
