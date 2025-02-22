package ru.otus.http.jserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class ClientThreads {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public ClientThreads(Socket socket, Dispatcher dispatcher) throws IOException {
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
            try {
                System.out.println("Подключился новый клиент");
                byte[] buffer = new byte[8192];
                int n = socket.getInputStream().read(buffer);
                HttpRequest request = new HttpRequest(new String(buffer, 0, n));
                request.info(true);
                dispatcher.execute(request, socket.getOutputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
        }
            finally {
                disconnect();
            }

    }
    public void disconnect() {
        try {
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
