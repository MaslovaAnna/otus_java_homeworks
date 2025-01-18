package main.java.ru.otus.classworks.cw5;

public class ClassWork5 {
    public static void main(String[] args) {
        Cat cat1 = new Cat();
        cat1.name ="Barsik";
        cat1.age = 3;
        cat1.color = "Black";

        Cat cat2 = new Cat();
        cat2.name ="Murzik";
        cat2.age = 4;
        cat2.color = "White";

        cat1.jump();
        cat1.info();
        cat1.sleep();

        cat2.jump();
        cat2.info();
    }
}
