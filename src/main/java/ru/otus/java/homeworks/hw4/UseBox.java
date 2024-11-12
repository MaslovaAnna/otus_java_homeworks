package ru.otus.java.homeworks.hw4;

public class UseBox {
    public static void main(String[] args) {
        Box box = new Box(10, 20, "Красный");
        box.info();
        box.addItem("хлеб");
        box.throwOutItem();
        box.changeColor("Черный");
        box.info();
        box.open();
        box.addItem("мяч");
        box.addItem("хлеб");
        box.close();
        box.throwOutItem();
        box.open();
        box.throwOutItem();
    }
}
