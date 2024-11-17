package ru.otus.java.homeworks.hw5.animals;

public  class Cat extends Animal {

    public Cat(String name, int swimSpeed, int runSpeed, int power) {
        super(name, swimSpeed, runSpeed, power);
    }

    @Override
    public int swim(int distance) {
        System.out.println(name + "не умеет плавать, потому что он кот, и утонул :(");
        return -1;
    }
}
