package ru.otus.java.homeworks.hw6;

public class Cat {

    protected String name;
    protected int appetite;
    protected boolean full;

    //Каждому коту нужно добавить поле сытость (когда создаем котов, они голодны).
    //Если коту удалось покушать (хватило еды), сытость = true.
    public Cat(String name, int appetite) {
        this.name = name;
        this.appetite = appetite;
    }

    public void feeding(int food) {
        if (appetite <= food) {
            full = true;
            System.out.println(name + " наелся");
        }
        else {
            full = false;
            System.out.println(name + " мало еды");}
    }

    public int getAppetite() {
        return appetite;
    }

    public String isFull() {
        return full? name + " сыт" : name + " голоден";
    }
}
