package main.java.ru.otus.homeworks.hw4;

public class Box {
    /*
    Попробуйте реализовать класс по его описания:
    объекты класса Коробка должны иметь размеры и цвет.
    Коробку можно открывать и закрывать. Коробку можно перекрашивать.
    Изменить размер коробки после создания нельзя.
    У коробки должен быть метод, печатающий информацию о ней в консоль.
    В коробку можно складывать предмет (если в ней нет предмета),
    или выкидывать его оттуда (только если предмет в ней есть),
    только при условии, что коробка открыта (предметом читаем просто строку).
    Выполнение методов должно сопровождаться выводом сообщений в консоль.
     */
    private final int dlina;
    private final int shirina;
    private String color;
    private String item;
    private boolean opened = false;

    public Box(int dlina, int shirina, String color) {
        this.dlina = dlina;
        this.shirina = shirina;
        this.color = color;
    }

    public void info() {
        System.out.println("Размеры коробки: " + dlina + "x" + shirina + "\nЦвет: " + color);
        System.out.println(opened ? "Коробка открыта" : "Коробка закрыта");
        if (item != null) {
            System.out.println("В коробке лежит " + item);
        } else {
            System.out.println("В коробке пусто");
        }
    }

    public void open() {
        this.opened = true;
        System.out.println("Коробка открыта");
    }

    public void close() {
        this.opened = false;
        System.out.println("Коробка закрыта");
    }

    public void addItem(String item) {
        if (!opened) {
            System.out.println("Нельзя взаимодействовать с закрытой коробкой!!! Сначала откройте ее!!!");
            return;
        }
        if (this.item != null) {
            System.out.println("В коробку не влезет " + item + ". В коробке уже лежит " + this.item);
            return;
        }
        this.item = item;
        System.out.println("В коробкe лежит " + this.item);
    }

    public void throwOutItem() {
        if (!opened) {
            System.out.println("Нельзя взаимодействовать с закрытой коробкой!!! Сначала откройте ее!!!");
            return;
        }
        if (this.item == null) {
            System.out.println("Нечего выкидывать. В коробке пусто");
            return;
        }
        System.out.println("Выкинули " + this.item);
        this.item = null;
    }

    public void changeColor(String color) {
        this.color = color;
        System.out.println("Коробку покрасили в " + this.color);
    }
}

