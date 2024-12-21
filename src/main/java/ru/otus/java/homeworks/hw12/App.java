package ru.otus.java.homeworks.hw12;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;


public class App {
    public static void main(String[] args) {
        /*
        Реализуйте приложение, позволяющее работать с текстовыми файлами
        При старте приложения, в консоль выводится
        список текстовых файлов из корневого каталоге проекта
        Далее программа запрашивает имя файла,
        с которым хочет работать пользователь
        Содержимое файла выводится в консоль
        Затем любую введенную пользователем строку
        необходимо записывать в указанный файл
         */
        String directory = "files";
        File files = new File(directory);
        System.out.println(Arrays.toString(files.listFiles()));
        System.out.println("Выберите в какой файл записать строку");
        Scanner scanner = new Scanner(System.in);
        File file = new File("files/" + scanner.nextLine());
        if (file.isFile()) {
            try (InputStreamReader in = new InputStreamReader(new FileInputStream(file.getParent() + "\\" + file.getName()))) {
                int n = in.read();
                while (n != -1) {
                    System.out.print((char)n);
                    n = in.read();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("\nЕсли хотите добавить данные в файл напишите true, если заменить то, что написано в файле - false");
            boolean append = scanner.nextBoolean();
            System.out.println(append);
            System.out.println("\nВведите что нужно записать в файл " + file.getName() + ":");
            String data = scanner.next();
            System.out.println(data);
            try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file.getParent() + "\\" + file.getName(),append))) {
                byte[] buffer = data.getBytes(UTF_8);
                for (byte b : buffer) {
                    out.write(b);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Нет такого файла");
        }
    }
}
