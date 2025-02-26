package ru.otus.chat.server.commands;

import ru.otus.chat.server.ClientHandler;
import ru.otus.chat.server.Server;

public class ChangeUsername implements Command {
    @Override
    public void execute(String message, ClientHandler client, Server server) {
        String[] startMessage = message.split(" ");
        if (startMessage.length != 2) {
            client.sendMsg("Неверный формат команды /changenick \n" +
                           "для смены имени пользователя введите: /changenick username \n");
        } else if (server.getAuthenticatedProvider()
                .changeUsername(client, startMessage[1])) {
            client.sendMsg("Username успешно изменен на " + startMessage[1]);
        }
    }
}
