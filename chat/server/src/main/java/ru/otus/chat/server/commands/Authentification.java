package ru.otus.chat.server.commands;

import ru.otus.chat.server.ClientHandler;
import ru.otus.chat.server.Server;

import java.time.LocalDateTime;

public class Authentification implements Command {
    @Override
    public void execute(String message, ClientHandler client, Server server) {
        String[] element = message.split(" ", 4);
        if (client.getUsername() == null) {
            if (element.length != 3) {
                client.sendMsg("Неверный формат команды /auth \n" +
                        "Для начала работы надо пройти аутентификацию. Формат команды /auth login password \n" +
                        "или регистрацию. Формат команды /reg login password username");
            } else if (server.getAuthenticatedProvider().checkPermBan(element[1])) {
                client.sendMsg("Пользователь забанен, доступ на сервер запрещен");
            } else if (server.getAuthenticatedProvider().checkBanTime(element[1]) != null) {
                if (LocalDateTime.now().isBefore(server.getAuthenticatedProvider().checkBanTime(element[1]))) {
                    client.sendMsg("User забанен, доступ на сервер запрещен");
                } else server.getAuthenticatedProvider()
                        .authenticate(client, element[1], element[2]);
            } else server.getAuthenticatedProvider()
                    .authenticate(client, element[1], element[2]);
        } else client.sendMsg("Вы уже авторизованы");
        }
    }

