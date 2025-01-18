package main.java.ru.otus.classworks.cw12;

import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        Box box = new Box(1,7);
        Box box1 = new Box(2,7);
        Box box2 = new Box(1,7);
        String str ="112";

        System.out.println(box.toString());
        System.out.println(box==box2);
        //box.equals(str);

        System.out.println(box.equals(box1));

        Box box3 = new Box(2, 4);

        Map<Box, String> map = new HashMap<>();

        map.put(box, "Vasya");
        map.put(box3, "Lena");

        //box3.setSize(11);

        System.out.println(map.get(box));
        System.out.println(map.get(box3));
        System.out.println(map.get(box2));
        System.out.println(box.equals(box2));



        String test = "0123456789ABC";
        String test1 = "0123456789abc".substring(1);

        System.out.println(test == test1);
        System.out.println(test.equalsIgnoreCase(test1));
    }

}
