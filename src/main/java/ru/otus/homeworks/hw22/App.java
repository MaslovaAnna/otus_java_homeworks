package main.java.ru.otus.homeworks.hw22;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class App {
    public static void main(String[] args) {
        Integer[] list = {1, 2, 2, 2, 1, 2};
        List<Integer> array = new ArrayList<>();
        Collections.addAll(array, list);
        Massive massive = new Massive();
        System.out.println(massive.afterOne(array));
        System.out.println(massive.checkOneOrTwo(array));
    }
}
