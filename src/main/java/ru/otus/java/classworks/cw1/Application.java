package ru.otus.java.classworks.cw1;

public class Application {
    public static void main(String[] args) {
        int n = (int)1.1f;
        float f = (float)1.123;
        int m = (byte)100;
        float k = 10;
        float rand = (int)(Math.random() * 10) + 0.2f;
        byte a = (byte)129;//-127
        int max = 10;
        int min = -10;
        for (int i =0; i<20; i++) {
            int random = (int)(Math.random() * (max - min + 1));
            System.out.println(random);
        }
        System.out.println(Math.random() < 0.5);//рандом булево

        int truecount = 0;
        int falsecount = 0;
        for (int i =0; i<1000000; i++) {
            if (Math.random() < 0.5) {
                truecount++;
            } else {
                falsecount++;
            }
        }
        System.out.println(truecount);
        System.out.println(falsecount);

    }
}
