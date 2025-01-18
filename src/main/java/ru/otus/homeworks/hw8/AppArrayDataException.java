package main.java.ru.otus.homeworks.hw8;

public class AppArrayDataException extends RuntimeException {

    public AppArrayDataException(int row, int col) {
      super("Неверные данные находятся в ячейке " + row + ", " + col);
    }
}

