package main.java.ru.otus.homeworks.hw21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    private final Object mon = new Object();
    private final TRhreadsABC threadsABC = new TRhreadsABC();

    public static void main(String[] args) throws InterruptedException {
        App app = new App();
        ExecutorService serv = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 5; i++) {
            serv.execute(() -> app.printChar('A','C'));
            serv.execute(() -> app.printChar('B','A'));
            serv.execute(() -> app.printChar('C','B'));
        }
        serv.shutdown();
    }

    public void printChar(char currLetter, char prevLetter) {
        synchronized (mon) {
            try {
                if (!threadsABC.string.isEmpty()) {
                    while (threadsABC.string.charAt(threadsABC.string.length() - 1) != prevLetter) {
                        mon.wait();
                    }
                }
                threadsABC.printString(String.valueOf(currLetter));
                System.out.print(currLetter);
                mon.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public class TRhreadsABC {
        private String string = "";

        public void printString(String str) {
            string += str;
        }
    }
}




