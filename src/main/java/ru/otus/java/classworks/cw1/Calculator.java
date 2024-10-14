package ru.otus.java.classworks.cw1;

import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        Scanner scr = new Scanner(System.in);
        System.out.println("Введите первое число");
        int a = scr.nextInt();
        System.out.println("Введите второе число");
        int b = scr.nextInt();
        System.out.println("Введите операцию: +, -, *, /");
        char operation = scr.next().charAt(0);
        if (operation == '+') {
            int result = a + b;
            System.out.println(a + " + " + b + " = " + result);
        } else if (operation == '-') {
            int result = a - b;
            System.out.println(a + " - " + b + " = " + result);
        } else if (operation == '*') {
            int result = a * b;
            System.out.println(a + " * " + b + " = " + result);
        } else if (operation =='/') {
            int result = a / b;
            System.out.println(a + " / " + b + " = " + result);
        } else {
            System.out.println("Введена неизвестная операция");
        }
    }
}
