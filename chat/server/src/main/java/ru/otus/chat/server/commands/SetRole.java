package ru.otus.chat.server.commands;

import ru.otus.chat.server.ClientHandler;
import ru.otus.chat.server.Server;

public class SetRole implements Command{
    @Override
    public void execute(String message, ClientHandler client, Server server) {
        String[] startMessage = message.split(" ");
        if (startMessage.length != 3) {
            client.sendMsg("Неверный формат команды /setrole\n" +
                    "Для удаления роли пользователю введите: /setrole username rolename");
        } else server.getAuthenticatedProvider().setRole(client, startMessage[1], startMessage[2]);
    }
}
