package ru.otus.chat.server.commands;

import ru.otus.chat.server.ClientHandler;
import ru.otus.chat.server.Server;

public class OnlineRoom implements Command {
    @Override
    public void execute(String message, ClientHandler client, Server server) {
        String[] startMessage = message.split(" ");
        if (startMessage.length > 3) {
            client.sendMsg("Неверный формат команды /onlineroom, \n" +
                    "для корректного результата введите: /onlineroom, чтобы увидеть пользователей в сети в вашей комнате \n" +
                    "/onlineroom room, чтобы узнать пользователей в сети в указанной комнате \n" +
                    "/onlineroom room username, чтобы узнать статус указанного пользователя в указанной комнате");
        } else if (startMessage.length == 3 && server.getAuthenticatedProvider().checkRoleManager(client.getUsername())) {
            server.isOnline(client.getUsername(), startMessage[1], startMessage[2]);
        } else if (startMessage.length == 1 ) {
            server.whoIsInRoom(client.getUsername(), client.getRoom());
        } else if (startMessage.length == 2 && server.getAuthenticatedProvider().checkRoleManager(client.getUsername())) {
            server.isOnline(client.getUsername(), client.getRoom(), startMessage[1] );
        } else client.sendMsg("Недостаточно прав для этого действия");
    }
}
