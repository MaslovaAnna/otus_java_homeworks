package main.java.ru.otus.classworks.cw19;


import static org.junit.jupiter.api.Assertions.*;

public class App {
    public static void main(String[] args) {
        SimpleCalculator calc = new SimpleCalculator();
        double result = 0;
        try {
            if (calc.add(8, 4) == 13) {
                result = 12;
            } else {
                throw new AssertionError("Ошибка в методе");
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        assert 6 == calc.multiply(2, 4);
        assertFalse(calc.add(3,5) == 8);
    }
}
