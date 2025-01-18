package main.java.ru.otus.homeworks.hw6;

public class Cat {

    private String name;
    private int appetite;
    private boolean full;

    //Каждому коту нужно добавить поле сытость (когда создаем котов, они голодны).
    //Если коту удалось покушать (хватило еды), сытость = true.
    public Cat(String name, int appetite) {
        this.name = name;
        this.appetite = appetite;
    }

    public void feeding(Plate plate) {
        if (plate.eatingFood(appetite)) {
            full = true;
            System.out.println(name + " поел");
        } else {
            System.out.println(name + " не поел");
        }
    }

    public String isFull() {
        return full ? name + " сыт" : name + " голоден";
    }
}
