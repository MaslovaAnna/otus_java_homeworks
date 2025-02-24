package ru.otus.chat.server.commands;

import ru.otus.chat.server.ClientHandler;
import ru.otus.chat.server.Server;

import static java.lang.Float.NaN;

public class Ban implements Command {
    @Override
    public void execute(String message, ClientHandler client, Server server) {
        String[] startMessage = message.split(" ", 4);
            if (startMessage.length != 3) {
                client.sendMsg("Неверный формат команды /ban, для бана пользователя навечно введите: /ban username -1");
            } else if (server.getAuthenticatedProvider().setBan(client, startMessage[1], Integer.parseInt(startMessage[2]))) {
                    server.kickOut(startMessage[1]);
            }
        }
    }
