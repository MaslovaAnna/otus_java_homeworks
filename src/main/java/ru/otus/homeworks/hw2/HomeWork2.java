package main.java.ru.otus.homeworks.hw2;

import java.util.Arrays;

public class HomeWork2 {
    public static void main(String[] args) {
        int a = 3;
        int uvelich = 9;
        String str = "домашка";
        int[] arr = {4,2,6,7,8,3,5};
        int[] sravni = {2,4,2,8,6,3,5,2,8,0,5};
        int[] arg = new int[11];
        summa(arr);
        metodPrint(a, str);
        System.out.println(Arrays.toString(fill(a, arg)));
        System.out.println(Arrays.toString(plusC(uvelich, arr)));
        sravniPoloviny(sravni);
    }

    /*
    Реализуйте метод,
    принимающий в качестве аргументов целое число и строку,
    и печатающий в консоль строку указанное количество раз
     */
    public static void metodPrint(int a, String str) {
        for (int i = 0; i < a; i++) {
            System.out.println(str);
        }
    }
    /*
    Реализуйте метод,
    принимающий в качестве аргумента целочисленный массив,
    суммирующий все элементы, значение которых больше 5,
    и печатающий полученную сумму в консоль.
     */
    public static void summa(int[] arr) {
        int sum = 0;
        for (int j : arr) {
            if (j > 5) {
                sum += j;
            }
        }
        System.out.println("Полученная сумма: " + sum);
    }
    /*
    Реализуйте метод,
    принимающий в качестве аргументов целое число и ссылку на целочисленный массив,
    метод должен заполниться каждую ячейку массива указанным числом.
     */
    public static int[] fill(int b, int[] args) {
        Arrays.fill(args, b); //использовали на практическом занятии 6
        //for (int i = 0; i < args.length; i++) {
            //args[i]=b;
        //}
        return args;
    }
    /*
    Реализуйте метод,
    принимающий в качестве аргументов целое число и ссылку на целочисленный массив,
    увеличивающий каждый элемент которого на указанное число.
     */
    public static int[] plusC(int c, int[] args) {
        for (int i = 0; i < args.length; i++) {
            args[i] += c;
        }
        return args;
    }
    /*
    Реализуйте метод,
    принимающий в качестве аргумента целочисленный массив,
    и печатающий в консоль сумма элементов какой из половин массива больше.
     */
    public static void sravniPoloviny(int[] args) {
        int sum1 = 0;
        int sum2 = 0;
        int seredka = args.length/2;
        for (int i = 0; i < args.length; i++) {
            if (i < seredka) {
                sum1 += args[i];
            } else if (i == seredka && args.length % 2 == 0){
                sum2 += args[i];
            } else if (i > seredka){
                sum2 += args[i];
            }
        }
        if (sum1 > sum2) {
            System.out.println("Первая половина массива больше второй: " + sum1 + ">" + sum2);
        } else if (sum1 < sum2) {
            System.out.println("Первая половина массива меньше второй: " + sum1 + "<" + sum2);
        } else {
            System.out.println("Половины равны: " + sum1 + "=" + sum2);
        }
    }
}
