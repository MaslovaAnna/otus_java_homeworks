package ru.otus.java.homeworks.hw7;
import static ru.otus.java.homeworks.hw7.TransportType.*;
import static ru.otus.java.homeworks.hw7.TerrainType.*;

import java.util.Arrays;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Person person = new Person("Kirill");
        person.sit(BICYCLE);
        person.travel(5, FORREST);
        System.out.println("У человека осталось сил " + person.getResurs());
    }
}
