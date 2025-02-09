package main.java.ru.otus.homeworks.hw22;

import java.util.ArrayList;
import java.util.List;

public class Massive {

    public List<Integer> afterOne(List<Integer> array) {
            int lastOne = array.lastIndexOf(1);
            if (lastOne == -1) {
                throw new RuntimeException("В массиве нет 1");
            }
            List<Integer> temp = new ArrayList<>();
            for (int i = lastOne + 1; i < array.size(); i++) {
                temp.add(array.get(i));
            }
            return temp;
    }

    public boolean checkOneOrTwo(List<Integer> array) {
        if (array.contains(1) && array.contains(2)) {
            boolean temp = false;
            for (int i : array) {
                temp = i == 1 || i == 2;
            }
            return temp;
        }
        return false;
    }
}
