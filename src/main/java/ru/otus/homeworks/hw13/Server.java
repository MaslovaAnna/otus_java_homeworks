package main.java.ru.otus.homeworks.hw13;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    private static String result;
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(8080);
        System.out.println("SERVER APPLICATION RUN!");
        while (true) {
            Socket client = socket.accept();
            DataInputStream inputStream = new DataInputStream(client.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(client.getOutputStream());
            System.out.println("Клиент с портом :" + client.getPort() + " подключился!");
            String userInput = inputStream.readUTF();
            if (userInput.equals("exit")) {
                System.out.println("Клиент с портом :" + client.getPort() + " отключился!");
                client.close();
                continue;
            }
            System.out.println(userInput);
            String[] strings = userInput.split(" ");
            System.out.println(strings[2]);
            if ("+".equals(strings[2])) {
                result = summa(strings);
            }
            else if ("-".equals(strings[2])) {
                result = minus(strings);
            }
            else if ("/".equals(strings[2])) {
                System.out.println("123");
                result = delit(strings);
            }
            else if ("*".equals(strings[2])) {
                result = umnojit(strings);
            }
            else result = "Неверная операция";
            outputStream.writeUTF(result);
            outputStream.flush();
            System.out.println("result = " + result);
        }
    }

    private static String summa(String[] strings) {
        System.out.println("выполняем операцию!");
        return  String.valueOf(Integer.parseInt(strings[0]) + Integer.parseInt(strings[1]));
    }

    private static String minus(String[] strings) {
        System.out.println("выполняем операцию!");
        return String.valueOf(Integer.parseInt(strings[0]) - Integer.parseInt(strings[1]));
    }

    private static String delit(String[] strings) {
        System.out.println("выполняем операцию!");
        return  String.valueOf(Integer.parseInt(strings[0]) / Integer.parseInt(strings[1]));
    }

    private static String umnojit(String[] strings) {
        System.out.println("выполняем операцию!");
        return  String.valueOf(Integer.parseInt(strings[0]) * Integer.parseInt(strings[1]));
    }
}