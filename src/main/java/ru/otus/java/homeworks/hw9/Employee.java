package ru.otus.java.homeworks.hw9;

public class Employee {
    /*
    Создайте класс Сотрудник с полями: имя, возраст;
     */
    private final String name;
    private final int age;

    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name = '" + name + '\'' +
                ", age = " + age +
                '}';
    }
}
