package ru.otus.chat.server.commands;

import ru.otus.chat.server.ClientHandler;
import ru.otus.chat.server.Server;

public class Registration implements Command {
    @Override
    public void execute(String message, ClientHandler client, Server server) {
        String[] element = message.split(" ");
        if (element.length != 4) {
            client.sendMsg("Неверный формат команды /reg \n" +
                    "Для начала работы надо пройти аутентификацию. Формат команды /auth login password \n" +
                    "или регистрацию. Формат команды /reg login password username");
        } else server.getAuthenticatedProvider()
                .registration(client, element[1], element[2], element[3]);
    }
}

