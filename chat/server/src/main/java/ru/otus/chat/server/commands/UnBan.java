package ru.otus.chat.server.commands;

import ru.otus.chat.server.ClientHandler;
import ru.otus.chat.server.Server;

import static java.lang.Float.NaN;

public class UnBan implements Command{
    @Override
    public void execute(String message, ClientHandler client, Server server) {
        String[] startMessage = message.split(" ", 3);
        if ((server.getAuthenticatedProvider()
                .checkRoleAdmin(client.getUsername()))) {
            if (startMessage.length != 2) {
                client.sendMsg("Неверный формат команды /unban");
            } else {
                server.getAuthenticatedProvider().unBan(client, startMessage[1]);
                server.broadcastMessage("На сервере разбанен: " + startMessage[1], null);
            }
            server.kickOut(startMessage[1]);
        }
    }
}
