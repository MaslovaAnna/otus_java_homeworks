package ru.otus.chat.server.commands;

import ru.otus.chat.server.ClientHandler;
import ru.otus.chat.server.Server;

public class UnBan implements Command {
    @Override
    public void execute(String message, ClientHandler client, Server server) {
        String[] startMessage = message.split(" ");
            if (startMessage.length != 2) {
                client.sendMsg("Неверный формат команды /unban\n" +
                               "Для удаления роли пользователю введите: /unban username");
            } else {
                server.getAdministrationService().unBan(client, startMessage[1]);
                server.broadcastMessage("На сервере разбанен: " + startMessage[1], null);
            }
    }
}
