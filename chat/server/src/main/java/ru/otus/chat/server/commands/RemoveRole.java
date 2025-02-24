package ru.otus.chat.server.commands;

import ru.otus.chat.server.ClientHandler;
import ru.otus.chat.server.Server;

public class RemoveRole implements Command{
    @Override
    public void execute(String message, ClientHandler client, Server server) {
        String[] startMessage = message.split(" ", 4);
        if (startMessage.length != 3) {
            client.sendMsg("Неверный формат команды /removerole");
        } else server.getAuthenticatedProvider().removeRole(client, startMessage[1], startMessage[2]);
    }
}
