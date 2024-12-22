package ru.otus.java.homeworks.hw12;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;


public class Main {
    public static void main(String[] args) {
        String directory = "files";
        File files = new File(directory);
        if (files.isDirectory()) {
            Scanner scanner = new Scanner(System.in);
            String path = printAndCheck(files, scanner);
            if (!path.equals("не .txt файл")) {
                File file = new File(path);
                if (file.isFile()) {
                    read(file);
                    write(file, scanner);
                } else {
                    System.out.println("Нет такого файла");
                }
            } else {
                System.out.println(path);
            }
        } else {
            System.out.println("Указана не директория");
        }
    }

    public static void read(File file) {
        try (InputStreamReader in = new InputStreamReader(new BufferedInputStream(new FileInputStream(file.getParent() + "\\" + file.getName())))) {
            int n = in.read();
            while (n != -1) {
                System.out.print((char) n);
                n = in.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(File file, Scanner scanner) {
        System.out.println("\nЕсли хотите добавить данные в файл напишите true, если заменить то, что написано в файле - false");
        boolean append = scanner.nextBoolean();
        System.out.println("\nВведите что нужно записать в файл " + file.getName() + ":");
        String data = scanner.next();
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file.getParent() + "\\" + file.getName(), append))) {
            byte[] buffer = data.getBytes(UTF_8);
            for (byte b : buffer) {
                out.write(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String printAndCheck(File files, Scanner scanner) {
        System.out.println(Arrays.toString(files.listFiles()));
        System.out.println("Выберите в какой файл записать строку");
        String path = "files/" + scanner.nextLine();
        if (getFileExtension(path)) {
            return path;
        } else {
            return "не .txt файл";
        }
    }

    private static boolean getFileExtension(String str) {
        int index = str.indexOf('.');
        if (index == -1) {
            return false;
        } else return str.substring(index).equals(".txt");
    }
}

