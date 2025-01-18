package main.java.ru.otus.homeworks.hw7;
import static main.java.ru.otus.homeworks.hw7.TransportType.*;
import static main.java.ru.otus.homeworks.hw7.TerrainType.*;

public class App {
    public static void main(String[] args) {
        Person person = new Person("Kirill");
        person.sit(BICYCLE);
        person.travel(5, FORREST);
        System.out.println("У человека осталось сил " + person.getResurs());
    }
}
