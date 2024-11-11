package ru.otus.java.homeworks.hw4;

public class User {
    /*
    Создайте класс Пользователь (User) с полями:
    фамилия, имя, отчество, год рождения, email;
    */
    private String surname;
    private String name;
    private String lastname;
    private int year;
    private String email;
    /*
    Реализуйте у класса конструктор,
    позволяющий заполнять эти поля при создании объекта;
    */
    public User(String surname, String name, String lastname, int year, String email) {
        this.surname = surname;
        this.name = name;
        this.lastname = lastname;
        this.year = year;
        this.email = email;
    }
    /*
    В классе Пользователь реализуйте метод, выводящий в консоль информацию о пользователе в виде:
    ФИО: фамилия имя отчество
    Год рождения: год рождения
    e-mail: email
    */
    public void info() {
        System.out.println("ФИО: " + surname + " " + name + " " + lastname + "\nГод рождения: " + year + "\ne-mail: " + email);
    }
    /*
    В методе main() Main класса создайте массив из 10 пользователей и заполните его объектами и с помощью цикла выведите информацию только о пользователях старше 40 лет.
    Попробуйте реализовать класс по его описания: объекты класса Коробка должны иметь размеры и цвет. Коробку можно открывать и закрывать. Коробку можно перекрашивать. Изменить размер коробки после создания нельзя. У коробки должен быть метод, печатающий информацию о ней в консоль. В коробку можно складывать предмет (если в ней нет предмета), или выкидывать его оттуда (только если предмет в ней есть), только при условии что коробка открыта (предметом читаем просто строку). Выполнение методов должно сопровождаться выводом сообщений в консоль.
     */

    public int getYear() {
        return year;
    }
}