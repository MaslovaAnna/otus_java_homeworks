package ru.otus.chat.server.commands;

import ru.otus.chat.server.ClientHandler;
import ru.otus.chat.server.Server;

public class CreateRoom implements Command {
    @Override
    public void execute(String message, ClientHandler client, Server server) {
        String[] element = message.split(" ");
        if (element.length < 2 || element.length >= 4) {
            client.sendMsg("Неверный формат команды /create \n" +
                           "Для создания комнаты введите /create name password(при наличии)");
        } else {
            if (element.length == 2) {
                server.getAuthenticatedProvider()
                        .createRoom(client, element[1], null);
            } else server.getAuthenticatedProvider()
                    .createRoom(client, element[1], element[2]);
        }
    }
}
