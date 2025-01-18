package main.java.ru.otus.homeworks.hw1;

import java.util.Random;
import java.util.Scanner;

public class HomeWork1 {
    /* Реализуйте метод greetings(),
    который при вызове должен отпечатать в столбец 4 слова:
    Hello, World, from, Java
     */
    public static void main(String[] args) {
        Scanner scr = new Scanner(System.in);
        System.out.println("Введите число от 1 до 5");
        int var = scr.nextInt();
        if (var == 1) {
            greetings();
        } else if (var == 2) {
            int a = (int)(Math.random() * 10) - 5;
            int b = (int)(Math.random() * 10) - 5;
            int c = (int)(Math.random() * 10) - 5;
            checkSign(a, b, c);
        } else if (var == 3) {
            selectColor();
        } else if (var == 4) {
            compareNumbers();
        } else if (var == 5) {
            int initValue = (int)(Math.random() * 10);
            int delta = new Random().nextInt();
            boolean increment = new Random().nextBoolean();
            addOrSubtractAndPrint(initValue, delta, increment);
        } else {
            System.out.println("Вы не попали в указанный диапазон");
        }
    }
    /*
    Реализуйте метод greetings(),
    который при вызове должен отпечатать в столбец 4 слова:
    Hello, World, from, Java;
     */
    public static void greetings() {
        System.out.println("Hello\nWorld\nfrom\nJava");
    }
    /*
    Реализуйте метод checkSign(..),
    принимающий в качестве аргументов 3 int переменные a, b и c.
    Метод должен посчитать их сумму, и если она больше или равна 0,
    то вывести в консоль сообщение “Сумма положительная”,
    в противном случае - “Сумма отрицательная”
     */
    public static void checkSign( int a,int b,int c) {
        int sum = a + b + c;
        if (sum > 0) {
            System.out.println("Сумма положительная"); 
        } else {
            System.out.println("Сумма отрицательная");
        }
    }
    /*
    Реализуйте метод selectColor()
    в теле которого задайте int переменную data с любым начальным значением.
    Если data меньше 10 включительно,
    то в консоль должно быть выведено сообщение “Красный”,
    если от 10 до 20 включительно,
    то “Желтый”, если больше 20 - “Зеленый”
     */
    public static void selectColor() {
        int data = new Random().nextInt(30);
        System.out.println(data);
        if (data <= 10) {
            System.out.println("Красный");
        } else if (data <= 20) {
            System.out.println("Желтый");
        } else {
            System.out.println("Зеленый");
        }
    }
    /*
    Реализуйте метод compareNumbers(),
    в теле которого объявите две int переменные a и b с любыми начальными значениями.
    Если a больше или равно b, то необходимо вывести в консоль сообщение “a >= b”,
    в противном случае “a < b”
     */
    public static void compareNumbers() {
        int a = (int)(Math.random()*10);
        int b = (int)(Math.random()*10);
        if (a >= b) {
            System.out.println("a >= b");
        } else {
            System.out.println("a < b");
        }
    }
    /*
    Создайте метод addOrSubtractAndPrint(int initValue,
    int delta, boolean increment). Если increment = true,
    то метод должен к initValue прибавить delta и отпечатать в консоль результат,
    в противном случае - вычесть
     */
    public static void addOrSubtractAndPrint(int initValue, int delta, boolean increment) {
        if (increment) {
            initValue += delta;
            System.out.println(initValue);
        } else {
            initValue -= delta;
            System.out.println(initValue);
        }
    }
}