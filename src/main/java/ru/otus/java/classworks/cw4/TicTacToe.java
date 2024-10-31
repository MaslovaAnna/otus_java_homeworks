package ru.otus.java.classworks.cw4;

import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    private static final int SIZE = 3;
    private static final int DOTS_TO_WIN = 3;

    private static final char DOT_X = 'X';
    private static final char DOT_O = 'O';
    private static final char DOT_EMPTY = '.';

    private static char[][] map;

    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) {
        init();
        printMap();

        while (true) {
            humanTurn();
            printMap();
            if (checkWinLines(DOT_X, DOTS_TO_WIN)) {
                System.out.println("Вы Победили!");
                break;
            }
            if (isFull()) {
                System.out.println("Ничья!");
                break;
            }

            aiTurn();
            printMap();
            if (checkWinLines(DOT_O, DOTS_TO_WIN)) {
                System.out.println("Компьютер победил!");
                break;
            }
            if (isFull()) {
                System.out.println("Ничья!");
                break;
            }
        }
    }

    public static void init() {
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    public static void printMap() {
        System.out.print("  ");
        for (int i = 1; i <= SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void humanTurn() {
        int x, y;
        do {
            System.out.println("Введите координаты X Y");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        } while (!isCellValid(x, y));
        map[x][y] = DOT_X;
    }

    public static void aiTurn() {
        int x, y;
        //Попытка победить
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) {
                    map[i][j] = DOT_O;
                    if (checkWinLines(DOT_O, DOTS_TO_WIN)) {
                        return;
                    } else {
                        map[i][j] = DOT_EMPTY;
                    }
                }
            }
        }
        //Попытка не дать игроку следующим ходом победить
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) {
                    map[i][j] = DOT_X;
                    if (checkWinLines(DOT_X, DOTS_TO_WIN)) {
                        map[i][j] = DOT_O;
                        return;
                    } else {
                        map[i][j] = DOT_EMPTY;
                    }
                }
            }
        }

        do {
            x = random.nextInt(SIZE);
            y = random.nextInt(SIZE);
        } while (!isCellValid(x, y));
        map[x][y] = DOT_O;
    }

    public static boolean isCellValid(int x, int y) {
        if (x >= SIZE || y >= SIZE || x < 0 || y < 0) {
            return false;
        }
        return map[x][y] == DOT_EMPTY;
    }

    public static boolean isFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean checkWin(char c) {
        if (map[0][0] == c && map[0][1] == c && map[0][2] == c) {
            return true;
        }
        if (map[1][0] == c && map[1][1] == c && map[1][2] == c) {
            return true;
        }
        if (map[2][0] == c && map[2][1] == c && map[2][2] == c) {
            return true;
        }

        if (map[0][0] == c && map[1][0] == c && map[2][0] == c) {
            return true;
        }
        if (map[0][1] == c && map[1][1] == c && map[2][1] == c) {
            return true;
        }
        if (map[0][2] == c && map[1][2] == c && map[2][2] == c) {
            return true;
        }

        if (map[0][0] == c && map[1][1] == c && map[2][2] == c) {
            return true;
        }
        if (map[0][2] == c && map[1][1] == c && map[2][0] == c) {
            return true;
        }
        return false;
    }

    public static boolean checkLine(int cx, int cy, int vx, int vy, char c, int dotsToWin) {
        if (cx + vx * (dotsToWin - 1) > SIZE - 1 || cy + vy * (dotsToWin - 1) > SIZE - 1
                || cy + vy * (dotsToWin - 1) < 0) {
            return false;
        }

        for (int i = 0; i < dotsToWin; i++) {
            if (map[cx + i * vx][cy + i * vy] != c){
                return false;
            }
        }
        return true;
    }

    public static boolean checkWinLines(char c, int dotsToWin){
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (checkLine(i, j, 0, 1, c, dotsToWin)||
                        checkLine(i, j, 1, 0, c, dotsToWin)||
                        checkLine(i, j, 1, 1, c, dotsToWin)||
                        checkLine(i, j, 1, -1, c, dotsToWin)){
                    return true;
                }
            }
        }
        return false;
    }
}
