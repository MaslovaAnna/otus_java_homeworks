package ru.otus.java.homeworks.hw14;

public class App4 {
    public static void main(String[] args) throws InterruptedException {
        /*
            Реализуйте метод, который создает
            double массив длиной 100_000_000 элементов
            Метод должен должен циклом for пройти по каждому элементу
            и посчитать его значение по формуле:
            array[i] = 1.14 * Math.cos(i) * Math.sin(i * 0.2) * Math.cos(i / 1.2);
            Засеките время выполнения цикла и выведите его в консоль.
         */
        final int ARRAY_SIZE = 100_000_000;
        double[] arrD = new double[ARRAY_SIZE];
        Thread createDouble1 = new Thread(() -> {
            for (int i = 0; i < ARRAY_SIZE/4; i++) {
                arrD[i] = 1.14 * Math.cos(i) * Math.sin(i * 0.2) * Math.cos(i / 1.2);
            }
        });
        Thread createDouble2 = new Thread(() -> {
            for (int i = ARRAY_SIZE/4; i < ARRAY_SIZE/2; i++) {
                arrD[i] = 1.14 * Math.cos(i) * Math.sin(i * 0.2) * Math.cos(i / 1.2);
            }
        });
        Thread createDouble3 = new Thread(() -> {
            for (int i = ARRAY_SIZE/2; i < 3*ARRAY_SIZE/4; i++) {
                arrD[i] = 1.14 * Math.cos(i) * Math.sin(i * 0.2) * Math.cos(i / 1.2);
            }
        });
        Thread createDouble4 = new Thread(() -> {
            for (int i = 3*ARRAY_SIZE/4; i < ARRAY_SIZE; i++) {
                arrD[i] = 1.14 * Math.cos(i) * Math.sin(i * 0.2) * Math.cos(i / 1.2);
            }
        });
        long m = System.currentTimeMillis();
        createDouble1.start();
        createDouble2.start();
        createDouble3.start();
        createDouble4.start();

        createDouble1.join();
        createDouble2.join();
        createDouble3.join();
        createDouble4.join();
        System.out.println((double) (System.currentTimeMillis() - m)/1000 + " секунд");
    }
}
