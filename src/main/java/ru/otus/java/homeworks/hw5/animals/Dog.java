package ru.otus.java.homeworks.hw5.animals;

public  class Dog extends Animal{

    public Dog(String name, int swimSpeed, int runSpeed, int power) {
        super(name, swimSpeed, runSpeed, power);
        powerPerSwim = 2;
    }

}
