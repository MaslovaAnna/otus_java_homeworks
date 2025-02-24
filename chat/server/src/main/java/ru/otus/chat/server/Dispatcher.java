package ru.otus.chat.server;

import ru.otus.chat.server.commands.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Dispatcher {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private Map<String, Command> router;


    public Dispatcher() {
        this.router = new HashMap<>();
        this.router.put("/exit", new Exit());
        this.router.put("/auth", new Authentification());
        this.router.put("/reg", new Registration());
        this.router.put("/setrole", new SetRole());
        this.router.put("/removerole", new RemoveRole());
        this.router.put("/online", new Online());
        this.router.put("/onlineroom", new OnlineRoom());
        this.router.put("/changenick", new ChangeUsername());
        this.router.put("/kick", new KickOut());
        this.router.put("/w", new PrivateChat());
        this.router.put("/shutdown", new ShutDown());
        this.router.put("/ban", new Ban());
        this.router.put("/unban", new UnBan());
        this.router.put("/create", new CreateRoom());
        this.router.put("/enter", new EnterRoom());
        this.router.put("/out", new OutRoom());
        this.router.put("/delete", new DeleteRoom());
        this.router.put("/com", (message, client, server) -> client.sendMsg(String.valueOf(router.keySet())));
        this.router.put("/rooms", (message, client, server) -> client.sendMsg(String.valueOf(server.getAuthenticatedProvider().actualRooms())));
    }

    void execute(String message, ClientHandler client, Server server) throws IOException {
        String com = message.split(" ")[0];
        if (client.getUsername() == null) {
            if (!router.containsKey(com)) {
                client.sendMsg("Неверный формат команды / \n" +
                                "Для начала работы надо пройти аутентификацию. Формат команды /auth login password \n" +
                                "или регистрацию. Формат команды /reg login password username");
            } else router.get(com).execute(message, client, server);
        } else {
            server.getAuthenticatedProvider().updateRoomsActivity(client);
            if (com.startsWith("/")) {
                if (!router.containsKey(com)) {
                    client.sendMsg("Неверный формат команды /");
                } else router.get(com).execute(message, client, server);
            } else if (!message.isEmpty()) {
                server.broadcastMessage(LocalDateTime.now().format(formatter) + " " + client.getUsername() + " : " + message, client.getRoom());
            } else client.sendMsg("Вы ничего не написали");
        }
    }
}
