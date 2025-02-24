package ru.otus.chat.server.commands;

import ru.otus.chat.server.ClientHandler;
import ru.otus.chat.server.Server;

public class DeleteRoom implements Command{
    @Override
    public void execute(String message, ClientHandler client, Server server) {
        String[] element = message.split(" ");
        if (element.length > 2) {
            client.sendMsg("Неверный формат команды /enter");
        } else {
            server.getAuthenticatedProvider()
                    .deleteRoom(client, element[1]);
            if (client.getRoom().equals(element[1])) client.setRoom("server");
        }
        }
    }

