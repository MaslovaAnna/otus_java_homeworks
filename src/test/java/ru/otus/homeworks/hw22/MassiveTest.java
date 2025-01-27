package test.java.ru.otus.homeworks.hw22;

import main.java.ru.otus.homeworks.hw22.Massive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;



class MassiveTest {
    private Massive massive;

    @MethodSource
    public static Stream<Arguments> testDataAfterOne() {
        List<Arguments> out = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<Integer> temp = new ArrayList<>();
            List<Integer> result = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                int a = (int) (Math.random() * 10);
                temp.add(a);
                result.add(a);
                if (a == 1) {
                    result.clear();
                }
            }
            if (!temp.equals(result)) out.add(Arguments.arguments(result, temp));
        }
        return out.stream();
    }

    @BeforeEach
    public void setUp() {
        massive = new Massive();
    }

    @ParameterizedTest
    @MethodSource("testDataAfterOne")
    public void testAfterOne(List<Integer> result, List<Integer> array) {
        Assertions.assertEquals(result, massive.afterOne(array));
    }

    @MethodSource
    public static Stream<Arguments> testDataCheckOneOrTwo() {
        return Stream.of(
                Arguments.of(true, new Integer[]{1,2,1,2}),
                Arguments.of(false, new Integer[]{1,1}),
                Arguments.of(false, new Integer[]{2,2}),
                Arguments.of(false, new Integer[]{1,2,3})
        );}
    @ParameterizedTest
    @MethodSource("testDataCheckOneOrTwo")
    public void checkOneOrTwo(boolean result, Integer[] list) {
        List<Integer> array = new ArrayList<>();
        Collections.addAll(array, list);
        Assertions.assertEquals(result, massive.checkOneOrTwo(array));
    }

    @MethodSource
    public static Stream<Arguments> testDataAfterOneException() {
        List<Arguments> out = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<Integer> temp = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                int a = (int) (Math.random() * 10);
                if (a != 1) {
                    temp.add(a);
                }
            }
            out.add(Arguments.arguments(temp));
        }
        return out.stream();
    }
    @ParameterizedTest
    @MethodSource("testDataAfterOneException")
    public void testAfterOneException(List<Integer> array) {
        Assertions.assertThrows(RuntimeException.class, () -> massive.afterOne(array), "Должно выброситься исключение при отсутствии 1 в массиве");
    }

}
