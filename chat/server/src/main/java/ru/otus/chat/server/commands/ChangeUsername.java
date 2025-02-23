package ru.otus.chat.server.commands;

import ru.otus.chat.server.ClientHandler;
import ru.otus.chat.server.Server;

public class ChangeUsername implements Command {
    @Override
    public void execute(String message, ClientHandler client, Server server) {
        String[] startMessage = message.split(" ", 3);
        if (startMessage.length != 2) {
            client.sendMsg("Неверный формат команды /changenick");
        } else if (server.getAuthenticatedProvider()
                .changeUsername(client, startMessage[1])) {
            client.sendMsg("Username успешно изменен на " + startMessage[1]);
        }
    }
}
