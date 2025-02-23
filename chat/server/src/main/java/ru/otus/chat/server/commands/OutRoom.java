package ru.otus.chat.server.commands;

import ru.otus.chat.server.ClientHandler;
import ru.otus.chat.server.Server;

public class OutRoom implements Command{
    @Override
    public void execute(String message, ClientHandler client, Server server) {
        String[] startMessage = message.split(" ");
        if (startMessage.length != 1) {
            client.sendMsg("Неверный формат команды /out");
        } else if (client.getRoom() != null) {
            client.setRoom(null);
            server.broadcastMessage("На общий сервер вернулся " + client.getUsername(), null);
        } else client.sendMsg("Вы не в частной комнате");
    }
}
