package ru.otus.java.classworks.cw3;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class classwork3 {
    public static void main(String[] args) {
        int[] arr = {4, 3, 23, 545, 3, 56, 432, 6};
        String[] fruits = {
                "apple", "plum",
                "kiwi", "pear",
                "pineapple", "grape",
                "strawberry", "cherry",
                "banana", "orange"
        };
        //minMax(arr);
        //autoSort(arr);
        //guessfruit(fruits);
        //diffFruit(fruits);
        poleChudes(fruits);
        //ruksak();
    }

    public static void minMax(int[] arr) {
        int max = Max(arr);
        int min = Min(arr);
        System.out.println("max = " + max);
        System.out.println("min = " + min);
    }

    /**
     * @param arr - входной массив
     * @return - максимальный элемент массива
     */
    public static int Max(int[] arr) {
        //int max = Integer.MIN_VALUE;
        int max = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

    /**
     * @param arr - входной массив
     * @return - минимальный элемент массива
     */
    public static int Min(int[] arr) {
        //int max = Integer.MIN_VALUE;
        int min = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        return min;
    }

    public static void autoSort(int[] arr) {
        Arrays.sort(arr);
        int min = arr[0];
        int max = arr[arr.length - 1];
        System.out.println(Arrays.toString(arr));
        System.out.println("max = " + max);
        System.out.println("min = " + min);
    }

    public static void guessfruit(String[] fruits) {
        Scanner scr = new Scanner(System.in);
        boolean win = false;
        int randomIndex = (int) (Math.random() * fruits.length);
        String wordToGuess = fruits[randomIndex];

        while (!win) {
            System.out.println("Угадайте фрукт: ");
            //дебаггер?
            String inputFruit = scr.next();
            if (wordToGuess.equals(inputFruit)) {
                System.out.println("Угадали!");
                win = true;
            } else {
                System.out.println("Не угадали!");
            }
        }
    }

    public static void diffFruit(String[] fruits) {
        Scanner scr = new Scanner(System.in);
        boolean win = false;
        int randomIndex = (int) (Math.random() * fruits.length);
        String wordToGuess = fruits[randomIndex];
        System.out.println("Угадайте фрукт: ");

        while (!win) {
            String inputFruit = scr.next();

            if (wordToGuess.equals(inputFruit)) {
                System.out.println("Угадали!");
                win = true;
            } else {
                char[] toGuess = wordToGuess.toCharArray();
                char[] input = inputFruit.toCharArray();

                for (int i = 0; i < toGuess.length && i < input.length; i++) {
                    if (toGuess[i] == input[i]) {
                        System.out.print(input[i]);
                    } else {
                        System.out.print("*");
                    }
                }
                for (int i = 0; i < (11 - toGuess.length); i++) {
                    System.out.print("*");
                }
                System.out.println();
            }

        }
    }

    public static void poleChudes(String[] fruits) {
        Scanner scr = new Scanner(System.in);
        Random rnd = new Random();
        int randomIndex = rnd.nextInt(fruits.length);
        String wordToGuess = fruits[randomIndex];
        boolean win = false;
        System.out.print("Крутите барабан, ");
        char[] result = new char[wordToGuess.length()];
        Arrays.fill(result, '*');
        while (!win) {
            System.out.println("назовите букву: ");
            char latter = scr.next().charAt(0);
            char[] toGuess = wordToGuess.toCharArray();
            System.out.println("Слово: ");

            for (int i = 0; i < toGuess.length; i++) {
                if (toGuess[i] == latter) {
                    result[i] = latter;
                }
            }
            for (int i = 0; i < result.length; i++) {
                System.out.print(result[i]);
            }
            if (!containsStar(result)) {
                win = true;
                System.out.println("\nВы победили!");
            }
            System.out.println();
        }

    }

    private static boolean containsStar(char[] result) {
        boolean flag = false;
        for (int i = 0; i < result.length; i++) {
            if (result[i] == '*') {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public static void ruksak() {
        Inventary inventary = new Inventary();
        String bread = "хлеб";
        String board = "доска";
        inventary.add(bread);
        inventary.print();
        inventary.add(board);
        inventary.print();
        System.out.println("Всего предметов " + inventary.count());
        //inventary.drop(bread);
        inventary.use(bread);
        inventary.print();
    }


}