package ru.otus.chat.server;

import java.io.IOException;
import java.sql.SQLException;

public class ServerApplication {
    public static void main(String[] args) throws SQLException, IOException {
        new Server(8189).start();
    }
}