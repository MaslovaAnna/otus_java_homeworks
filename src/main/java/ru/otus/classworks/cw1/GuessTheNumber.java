package main.java.ru.otus.classworks.cw1;

import java.util.Scanner;

public class GuessTheNumber {
    //System.exit(0);--полная остановка приложения
    public static void main(String[] args) {
        Scanner scr = new Scanner(System.in);
        System.out.println("Добро пожаловать в игру 'Угадай число'");
        int maxValue;
        int triesCount;
        boolean tipsEnabled;

        System.out.println("Выберите уровень сложности:\n1. Легко\n2. Средне\n3. Сложно");
        int difficult = scr.nextInt();
        if (difficult == 1) {
            maxValue = 10;
            triesCount = 3;
            tipsEnabled = true;
        } else if (difficult == 2) {
            maxValue = 15;
            triesCount = 2;
            tipsEnabled = true;
        } else if (difficult == 3) {
            maxValue = 20;
            triesCount = 3;
            tipsEnabled = false;
        } else {
            System.out.println("Ужасная сложность");
            maxValue = 99999;
            triesCount = 10;
            tipsEnabled = false;
        }

        int quest = (int)(Math.random() * maxValue + 1);
        System.out.println("Компудахтер загадал число от 1 до " + maxValue + ". Попробуйте отгадать! У вас попыток: " + triesCount);

        while (true) {
            int answer;
            do {
                System.out.println("Введите число");
                answer = scr.nextInt();
            } while (answer < 1 || answer > maxValue);

            if (answer == quest) {
                System.out.println("Вы победили!");
                break;
            }
            triesCount--;
            if (triesCount == 0) {
                System.out.println("Попыток больше не осталось. Вы проиграли!");
                break;
            }
            if (tipsEnabled) {
                if (answer > quest) {
                    System.out.println("Загаданное число меньше. У вас осталось попыток: " + triesCount);
                } else {
                    System.out.println("Загаданное число больше. У вас осталось попыток: " + triesCount);
                }
            } else {
                System.out.println("Не угадали. У вас осталось попыток: " + triesCount);
            }
        }
        System.out.println("Игра завершена");
    }
}
