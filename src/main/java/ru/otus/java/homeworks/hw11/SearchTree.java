package ru.otus.java.homeworks.hw11;

import java.util.List;

public interface SearchTree<Integer> {
    Integer find(Integer element);
    List<Integer> getSortedList();
}
