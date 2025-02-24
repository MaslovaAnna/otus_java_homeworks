package ru.otus.chat.server.commands;

import ru.otus.chat.server.ClientHandler;
import ru.otus.chat.server.Server;

public class KickOut implements Command {
    @Override
    public void execute(String message, ClientHandler client, Server server) {
        String[] startMessage = message.split(" ");
        if ((server.getAuthenticatedProvider()
                .checkRoleAdmin(client.getUsername()))) {
            if (startMessage.length != 2) {
                client.sendMsg("Неверный формат команды /kick \n" +
                        "для того чтобы выгнать пользователя с сервера введите: /kick username");
            } else {
                server.kickOut(startMessage[1]);
                client.sendMsg("С сервера выгнан " + startMessage[1]);
            }
        } else client.sendMsg("Недостаточно прав");
    }
}
