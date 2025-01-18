package main.java.ru.otus.homeworks.hw6;

import java.util.Arrays;

public class App {
    public static void main(String[] args) {

        Cat[]  cats = {
                new Cat("Barsik", 10),
                new Cat("Musya", 20),
                new Cat("Petr", 5),
                new Cat("Dobbi", 2),
                new Cat("Bublik", 4)
        };
        String[] fullCats = new String[cats.length];
        Plate plate = new Plate(40);

        /*
        Создать массив котов и тарелку с едой,
        попросить всех котов покушать из этой тарелки
        и потом вывести информацию о сытости котов в консоль.
        Считаем, что если коту мало еды в тарелке, то он её просто не трогает,
        то есть не может быть наполовину сыт (это сделано для упрощения логики программы).
        */
        for (Cat c: cats) {
            c.feeding(plate);
            fullCats[Arrays.asList(cats).indexOf(c)] = c.isFull();
        }
        System.out.println(Arrays.toString(fullCats));
    }

}
