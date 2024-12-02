package ru.otus.java.homeworks.hw8;

public class App {
    /*
   Реализуйте метод, аргументом которого является двумерный строковый массив размером 4х4.
   Если передан массив другого размера необходимо бросить исключение AppArraySizeException.
   Метод должен обойти все элементы массива, преобразовать в int и просуммировать.
   Если в каком-то элементе массива преобразование не удалось (например, в ячейке лежит текст вместо числа),
   должно быть брошено исключение AppArrayDataException с детализацией, в какой именно ячейке лежат неверные данные.
   В методе main() необходимо вызвать полученный метод, обработать возможные исключения AppArraySizeException и AppArrayDataException
   и вывести результат расчета (сумму элементов, при условии, что подали на вход корректный массив).
    */
    public static void main(String[] args) {
        String[][] str = {
                {"1","1","2","3"},
                {"1","2","3","4"},
                {"1","2","3","4"},
                {"1","2","3","4"}
        };
        System.out.println(metod(str));
    }

    public static int metod(String[][] str) {
        int sum = 0;
        if (str.length != 4) {
            throw new AppArraySizeException();
        }
        for (int i = 0; i < str.length; i++) {
            if (str[i].length != 4) {
                throw new AppArraySizeException();
            }
            for (int j = 0; j < str[i].length; j++) {
                try {
                    sum += Integer.parseInt(str[i][j]);
                } catch (NumberFormatException e) {
                    throw new AppArrayDataException(i+1, j+1);
                }
            }
        }
        return sum;
    }
}
