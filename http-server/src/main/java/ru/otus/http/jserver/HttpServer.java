package ru.otus.http.jserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private int port;
    private Dispatcher dispatcher;


    public HttpServer(int port) {
        this.port = port;
        this.dispatcher = new Dispatcher();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту: " + port);
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                ExecutorService socketsPool = Executors.newCachedThreadPool();
                socketsPool.execute(() -> {
                    try {
                        new ClientThreads(socket,dispatcher);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}