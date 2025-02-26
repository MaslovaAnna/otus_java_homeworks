package ru.otus.http.jserver;

public class Application {
    public static void main(String[] args) {
        new HttpServer(8188).start();
    }
}
