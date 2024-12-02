package ru.otus.java.homeworks.hw10;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
    Реализуйте класс PhoneBook, который хранит в себе список имен (фио) и телефонных номеров;
    Под одним именем может быть несколько телефонов (в случае однофамильцев, или наличии у одного человека нескольких номеров),
    тогда при запросе такой фамилии должны выводиться все телефоны;
     */
public class PhoneBook {
    private static final Map<String, Set<Integer>> pb = new HashMap<>();

/*
Метод containsPhoneNumber должен проверять наличие телефона в справочнике.
 */

    public void containsPhoneNumber(Integer number) {
        for (Map.Entry<String, Set<Integer>> entry : pb.entrySet()) {
            boolean result = entry.getValue().contains(number);
            if (result) {
                System.out.println("У " + entry.getKey() + " номер телефона: " + number);
                return;
            }
        }
        System.out.println(number + "не найден");
    }
/*
    Метод find() выполнять поиск номер(-а, -ов) телефона по имени;
 */
    public void find(String name) {
        Set<Integer> phone = pb.get(name);
        System.out.println("У " + name + " номер телефона: " + phone);
    }
    /*
        Метод add() должен позволять добавлять запись имя-телефон;
     */
    public void add(String name, Integer number) {
        if (pb.containsKey(name)) {
            pb.get(name).add(number);
        } else {
            Set<Integer> phone = new HashSet<>();
            phone.add(number);
            pb.put(name, phone);
        }

    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Map.Entry<String, Set<Integer>> entry : pb.entrySet()) {
            str.append("имя: ").append(entry.getKey()).append(", номер телефона: ").append(entry.getValue()).append(";\n");
        }
        return str.toString();
    }

}
