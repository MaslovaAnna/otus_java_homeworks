package ru.otus.java.homeworks.hw5.animals;

public  class Horse extends Animal{

    public Horse(String name, int swimSpeed, int runSpeed, int power) {
        super(name, swimSpeed, runSpeed, power);
        powerPerSwim = 4;
    }

}
