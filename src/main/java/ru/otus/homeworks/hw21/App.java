package main.java.ru.otus.homeworks.hw21;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    private final Object mon = new Object();
    private final TRhreadsABC threadsABC = new TRhreadsABC();

    public static void main(String[] args) throws InterruptedException {
        App app = new App();
        ExecutorService serv = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 5; i++) {
            serv.execute(app::printA);
            serv.execute(app::printB);
            serv.execute(app::printC);
        }
        serv.shutdown();
    }

    public void printA() {
        synchronized (mon) {
            try {
                if (!Objects.equals(TRhreadsABC.string, "")) {
                    while (threadsABC.string.charAt(threadsABC.string.length() - 1) != 'C') {
                        mon.wait();
                    }
                }
                threadsABC.printString("A");
                System.out.print("A");
                mon.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printB() {
        synchronized (mon) {
            try {
                while (threadsABC.string.charAt(threadsABC.string.length() - 1) != 'A') {
                    mon.wait();
                }
                threadsABC.printString("B");
                System.out.print("B");
                mon.notifyAll();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printC() {
        synchronized (mon) {
            try {
                while (threadsABC.string.charAt(threadsABC.string.length() - 1) != 'B') {
                    mon.wait();
                }
                threadsABC.printString("C");
                System.out.print("C");
                mon.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public class TRhreadsABC {
        private static String string = "";

        public static void printString(String str) {
            string += str;
        }
    }
}




