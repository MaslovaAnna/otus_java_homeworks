package ru.otus.java.homeworks.hw9;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class App {

    public static void main(String[] args) {
        int min = 20;
        int max = 25;
        System.out.println(list(min, max));
        ArrayList<Integer> str = list(min, max);
        System.out.println(sum(str));
        addA(4, str);
        rewrite(3, str);
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Artem", 56));
        employees.add(new Employee("Nikita", 50));
        employees.add(new Employee("Stas", 35));
        employees.add(new Employee("Gena", 40));
        employees.add(new Employee("Dusha", 45));
        System.out.println(getNames(employees));
        System.out.println(minAge(45, employees));
        if (compareAverageAge(45, employees)) {
            System.out.println("Реальный средний возраст превышает указанный");
        } else {
            System.out.println("Реальный средний возраст меньше либо равен указанному");
        }
        System.out.println(youngestEmp(employees));
    }


    /*
    Реализуйте метод, принимающий в качестве аргументов числа min и max,
    и возвращающий ArrayList с набором последовательных значений в указанном диапазоне
    (min и max включительно, шаг - 1);
     */
    public static ArrayList<Integer> list(int min, int max) {
        ArrayList<Integer> array = new ArrayList<>();
        for (int i = min; i < max + 1; i++) {
            array.add(i);
        }
        return array;
    }

    /*
        Реализуйте метод, принимающий в качестве аргумента список целых чисел,
        суммирующий все элементы, значение которых больше 5, и возвращающий сумму;
     */
    public static int sum(ArrayList<Integer> array) {
        int sum = 0;
        for (Integer a : array) {
            sum += a;
        }
        return sum;
    }

    /*
        Реализуйте метод, принимающий в качестве аргументов целое число и ссылку на список,
        метод должен переписать каждую заполненную ячейку списка указанным числом;
     */
    public static void rewrite(int a, ArrayList<Integer> array) {
        Collections.fill(array, a);
        System.out.println(array);
    }

    /*
        Реализуйте метод, принимающий в качестве аргументов целое число и ссылку на список,
        увеличивающий каждый элемент списка на указанное число;
     */
    public static void addA(int a, ArrayList<Integer> array) {
        array.replaceAll(integer -> integer + a);
        System.out.println(array);
    }

    /*
    Реализуйте метод, принимающий в качестве аргумента список сотрудников, и возвращающий список их имен;
     */
    public static List<String> getNames(List<Employee> employees) {
        List<String> names = new ArrayList<>();
        for (Employee e : employees) {
            names.add(e.getName());
        }
        return names;
    }

    /*
    Реализуйте метод, принимающий в качестве аргумента список сотрудников и минимальный возраст,
    и возвращающий список сотрудников, возраст которых больше либо равен указанному аргументу;
     */
    public static List<Employee> minAge(int min, List<Employee> employees) {
        List<Employee> oldEmps = new ArrayList<>();
        for (Employee e : employees) {
            if (e.getAge() >= min) {
                oldEmps.add(e);
            }
        }
        return oldEmps;
    }

    /*
    Реализуйте метод, принимающий в качестве аргумента список сотрудников и минимальный средний возраст,
    и проверяющий, что средний возраст сотрудников превышает указанный аргумент;
     */
    public static boolean compareAverageAge(int predictAge, List<Employee> employees) {
        double averageAge = 0;
        for (Employee e : employees) {
            averageAge += e.getAge();
        }
        averageAge /= employees.size();
        return averageAge > predictAge;
    }

    /*
    Реализуйте метод, принимающий в качестве аргумента список сотрудников, и возвращающий ссылку на самого молодого сотрудника.
     */
    public static Employee youngestEmp(List<Employee> employees) {
        Employee youngest = new Employee("Young", 10000);
        for (Employee e : employees) {
            if (e.getAge() < youngest.getAge()) {
                youngest = e;
            }
        }
        return youngest;
    }
}
