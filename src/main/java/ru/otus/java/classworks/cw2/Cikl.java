package ru.otus.java.classworks.cw2;

//import java.lang.reflect.Array;
import java.util.Arrays;

public class Cikl {
    public static void main(String[] args) {
        for (int i = 20; i < 40; i += 2) {
            System.out.println((i + 1) + ". Java");
        }
        //int n = 1;
        //int m = n++ + ++n;//n=1+ -> n++==n+1=2 -> ++n==1+n=3-> +3
        int[] arr = new int[14];
        System.out.println(Arrays.toString(arr));
        float[] temperature = { 18.8f, 25.5f,21f,19f,20.33f};
        System.out.println(Arrays.toString(temperature));

    }
}

