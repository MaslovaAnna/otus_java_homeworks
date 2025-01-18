package main.java.ru.otus.homeworks.hw19;

public class App {
    public static void main(String[] args) {
        Apple apple = new Apple();
        Orange orange = new Orange();
        FruitBox<Apple> appleBox = new FruitBox<>();
        FruitBox<Orange> orangeBox = new FruitBox<>();
        FruitBox<Fruit> fruitsBox = new FruitBox<>();

        for (int i = 0; i < 10; i++) {
            fruitsBox.addFruit(orange);
            fruitsBox.addFruit(apple);
            appleBox.addFruit(apple);
            orangeBox.addFruit(orange);
        }
        System.out.println(fruitsBox.weight());
        System.out.println(orangeBox.weight());
        System.out.println(appleBox.weight());
        System.out.println(appleBox.compareTo(orangeBox));
        System.out.println(appleBox.compareTo(fruitsBox));

        appleBox.moveTo(fruitsBox);
        System.out.println(appleBox);
    }
}
