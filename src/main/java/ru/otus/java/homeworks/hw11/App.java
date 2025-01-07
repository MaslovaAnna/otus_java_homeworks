package ru.otus.java.homeworks.hw11;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        List<Integer> array = new ArrayList<>();
        int n = 14;
        for (int i = 0; i < n; i++) {
            array.add((int) (Math.random() * 100));
        }
        ExampleTree T = new ExampleTree(array);
        System.out.println(T.getSortedList());
        T.makeTree();
        Scanner scr = new Scanner(System.in);
        System.out.println("Введите искомое число");
        System.out.println(T.find(scr.nextInt()));

    }
}

