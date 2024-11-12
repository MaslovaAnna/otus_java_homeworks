package ru.otus.java.homeworks.hw4;

import java.time.LocalDate;

public class Users40 {
    public static void main(String[] args) {
        /*
        В методе main() Main класса создайте массив из 10 пользователей
        и заполните его объектами и с помощью цикла выведите информацию
        только о пользователях старше 40 лет.
        */
        User[] users = {
                new User("Maslov", "Petr", "Petrovich", 1980, "p@ma.ru"),
                new User("Karlov", "Oleg", "Nikitich", 1974, "n@ma.ru"),
                new User("Pushkina", "Anna", "Evgenievna", 2000, "e@ma.ru"),
                new User("Alferova", "Dana", "Olegovna", 1960, "o@ma.ru"),
                new User("Efremov", "Roman", "Artemovich", 1999, "a@ma.ru"),
                new User("Filov", "Nikita", "Danilovna", 1990, "d@ma.ru"),
                new User("Pavlov", "Ivan", "Kirillovich", 1989, "k@ma.ru"),
                new User("Borsiov", "Anton", "Leonidovich", 1979, "l@ma.ru"),
                new User("Klimova", "Sveta", "Alekseevna", 1985, "al@ma.ru"),
                new User("Viniloba", "Lida", "Antonovna", 1977, "an@ma.ru"),
        };
        int currentYear = LocalDate.now().getYear();
        for (User u : users) {
            if ((currentYear - u.getYear()) > 40) {
                u.info();
            }
        }

    }
}
