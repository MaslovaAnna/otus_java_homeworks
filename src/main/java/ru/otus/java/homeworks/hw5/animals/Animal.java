package ru.otus.java.homeworks.hw5.animals;

public abstract class Animal {
    /*
    Создайте классы Cat, Dog и Horse с наследованием от класса Animal
    У каждого животного есть имя, скорость бега и плавания (м/с),
     и выносливость (измеряется в условных единицах)
    Затраты выносливости:
    Все животные на 1 метр бега тратят 1 ед выносливости,
    Реализуйте методы run(int distance) и swim(int distance),
    которые должны возвращать время, затраченное на указанное действие,
    и “понижать выносливость” животного.
    Если выносливости не хватает, то возвращаем время -1 и
    указываем что у животного появилось состояние усталости.
    При выполнении действий пишем сообщения в консоль.
    Добавляем метод info(), который выводит в консоль состояние животного.
     */
    protected String name;
    protected int runSpeed;
    protected int swimSpeed;
    protected int power;
    protected int time;
    private final int powerPerRun;


    public  Animal(String name, int swimSpeed, int runSpeed, int power) {
        this.name = name;
        this.runSpeed = runSpeed;
        this.swimSpeed = swimSpeed;
        this.power = power;
        powerPerRun = 1;
    }

    public void info() {
        System.out.println("У " + name + " осталось " + power + " выносливости");
    }

    public int run(int distance) {
        power -= powerPerRun * distance;
        if (power < 0) {
            System.out.println("У " + name + " закончилась выносливость и он устал");
            return -1;
        }
        time = distance/runSpeed;
        System.out.println(name + " пробежал " + distance + " за " + time + " сек");
        if (power == 0) {
            System.out.println(name + " устал");
        }
        return time;
    }
}
