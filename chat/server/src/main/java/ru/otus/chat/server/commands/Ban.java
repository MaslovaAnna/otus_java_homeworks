package ru.otus.chat.server.commands;

import ru.otus.chat.server.ClientHandler;
import ru.otus.chat.server.Server;

public class Ban implements Command {
    @Override
    public void execute(String message, ClientHandler client, Server server) {
        String[] startMessage = message.split(" ");
        if (startMessage.length != 3) {
            client.sendMsg("Неверный формат команды /ban, \n" +
                           "для бана пользователя навечно введите: /ban username -1 \n" +
                           "для бана на определенное количество минут введите: /ban username time");
        }
        server.getAdministrationService().setBan(client, startMessage[1], Integer.parseInt(startMessage[2]));
}
}
