package ru.otus.java.homeworks.hw8;

class AppArraySizeException extends RuntimeException {

    public AppArraySizeException() {
        super("Размерность массива должна быть 4 x 4");
    }
}
