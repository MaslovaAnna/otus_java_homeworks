package main.java.ru.otus.homeworks.hw20;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название файла");
        File file = new File("files/" + scanner.nextLine());
        if (file.isFile()) {
            System.out.println("Введите искомую последовательность:");
            String reg = scanner.nextLine();
            System.out.println("Указанная последовательность встречается " + countRegs(file, reg) + " раза");
        } else System.out.println("Не файл");
    }

    public static int countRegs(File file, String reg) {
        StringBuilder read = new StringBuilder();
        try (FileReader fileReader = new FileReader(file, StandardCharsets.UTF_8)) {
            int bytesRead;
            while ((bytesRead = fileReader.read()) != -1) {
                read.append((char) bytesRead);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        if (read.indexOf(reg) == 0 && read.lastIndexOf(reg) + 1 == read.length()) {
            return read.toString().split(reg).length;
        } else if (read.indexOf(reg) == 0) {
            return read.toString().split(reg).length - 1;
        }
        return read.toString().split(reg).length;
    }
}
