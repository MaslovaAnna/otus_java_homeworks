package ru.otus.chat.server.commands;

import ru.otus.chat.server.ClientHandler;
import ru.otus.chat.server.Server;

public class EnterRoom implements Command {
    @Override
    public void execute(String message, ClientHandler client, Server server) {
        String[] element = message.split(" ");
        if (element.length > 2) {
            client.sendMsg("Неверный формат команды /enter");
        } else if (client.getRoom() == null) {
            if (element.length == 2) {
                server.getAuthenticatedProvider()
                        .enterRoom(client, element[1], null);
            } else server.getAuthenticatedProvider()
                    .enterRoom(client, element[1], element[2]);
        } else client.sendMsg("Вы уже находитесь в другой комнате");
    }
}
