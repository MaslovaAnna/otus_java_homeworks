package ru.otus.java.classworks.cw5;

public class Cat {
    public String name;
    public String color;
    public int age;

    public void jump() {
        System.out.println(name + " подпрыгнул");
    }

    public void info() {
        this.name = name;
        System.out.println(name + " " + color + " " + age);
    }

    public void sleep() {
        System.out.println(name + " поспал");
    }
}
