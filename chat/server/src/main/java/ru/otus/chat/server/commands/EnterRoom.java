package ru.otus.chat.server.commands;

import ru.otus.chat.server.ClientHandler;
import ru.otus.chat.server.Server;

public class EnterRoom implements Command {
    @Override
    public void execute(String message, ClientHandler client, Server server) {
        String[] element = message.split(" ");
        if (element.length > 3) {
            client.sendMsg("Неверный формат команды /enter");
        } else if (client.getRoom().equals("server")) {
            if (element.length == 2) {
                server.getAuthenticatedProvider()
                        .enterRoom(client, element[1], null);
                server.broadcastMessage("В комнату " + element[1] + " вошел " + client.getUsername(), element[1]);
            } else if (element.length == 3) {
                server.getAuthenticatedProvider()
                        .enterRoom(client, element[1], element[2]);
                server.broadcastMessage("В комнату " + element[1] + " вошел " + client.getUsername(), element[1]);
            } else client.sendMsg("Неверный формат команды /enter");
        } else client.sendMsg("Вы уже находитесь в другой комнате");
    }
}
