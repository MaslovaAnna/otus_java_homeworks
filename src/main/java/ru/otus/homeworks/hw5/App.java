package main.java.ru.otus.homeworks.hw5;


import main.java.ru.otus.homeworks.hw5.animals.Cat;
import main.java.ru.otus.homeworks.hw5.animals.Dog;
import main.java.ru.otus.homeworks.hw5.animals.Horse;

public class App {
    public static void main(String[] args) {
        /*
        Собаки на 1 метр плавания - 2 ед.
        Лошади на 1 метр плавания тратят 4 единицы
        Кот плавать не умеет.
         */
        Cat cat = new Cat("Barsik",1,1, 5);
        Dog dog = new Dog("Bim", 1,3,10);
        Horse horse = new Horse("Apolon",2, 10, 30);

         cat.run(1);
         dog.run(10);
         horse.run(10);

         cat.info();
         dog.info();
         horse.info();

         cat.swim(3);
         dog.swim(5);
         horse.swim(4);

    }
}
