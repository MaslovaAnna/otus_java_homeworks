package ru.otus.java.homeworks.hw3;

public class HomeWork3 {
    public static void main(String[] args) {
        int[][] arr2d_1 = {{-1,-3,8},{-2,6,1,-3,-5},{4,5,1,-8}};
        int[][] arr2d_2 = {{1,-3,8,9,6},{-2,6,1,-3,7},{4,5,1,-8,1},{2,4,-5,6,2},{2,-7,5,4,6},{3,-7,5,4,6}};
        int[][] arr2d_3 = {{1}};
        int a = 4;
        System.out.println(sumOfPositiveElements(arr2d_1));
        printSquare(a);
        diagonal_0(arr2d_2);
        System.out.println(findMax(arr2d_1));
        System.out.println(summaOr_1(arr2d_3));
    }
    /*
    Реализовать метод sumOfPositiveElements(..),
    принимающий в качестве аргумента целочисленный двумерный массив,
    метод должен посчитать и вернуть сумму всех элементов массива,
    которые больше 0;
     */
    public static int sumOfPositiveElements(int[][] args) {
        int sum = 0;
        for (int i = 0; i < args.length; i++) {
            for (int j = 0; j < args[i].length; j++) {
                if (args[i][j] > 0) {
                    sum += args[i][j];
                }
            }

        }
        return sum;
    }
    /*
    Реализовать метод,
    который принимает в качестве аргумента int size
    и печатает в консоль квадрат из символов *
    со сторонами соответствующей длины;
     */
    public static void printSquare(int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print('*');
            }
            System.out.println();
        }
    }
    /*
    Реализовать метод,
    принимающий в качестве аргумента двумерный целочисленный массив,
    и зануляющий его диагональные элементы
    (можете выбрать любую из диагоналей, или занулить обе);
     */
    public static void diagonal_0(int[][] args) {
        for (int i = 0; i < args.length; i++) {
            for (int j = 0; j < args[i].length; j++) {
                //Зануляем диагональ справа налево
                if (i == j) {
                    args[i][j] = 0;
                }
                //Зануляем диагональ слева направо
                if (j == args[i].length-1-i) {
                    args[i][j] = 0;
                }
                System.out.print(args[i][j] + " ");
            }
            System.out.println();
        }
    }
    /*
    Реализовать метод findMax(int[][] array),
    который должен найти и вернуть максимальный элемент массива;
     */
    public static int findMax(int[][] array) {
        int max = array[0][0];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (max < array[i][j]) {
                    max = array[i][j];
                }
            }
        }
        return  max;
    }
    /*
    Реализуйте метод,
    который считает сумму элементов второй строки двумерного массива,
    если второй строки не существует,
    то в качестве результата необходимо вернуть -1
     */
    public static int summaOr_1(int[][] args) {
        if (args.length <= 1) {
            return -1;
        }
        int sum = 0;
        for (int i = 0; i < args[1].length; i++) {
            sum +=args[1][i];
        }
        return sum;
    }
}
