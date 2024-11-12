package ru.otus.java.homeworks.hw5.animals;

public  class Horse extends Animal{
    private final int powerPerSwim;

    public Horse(String name, int swimSpeed, int runSpeed, int power) {
        super(name, swimSpeed, runSpeed, power);
        powerPerSwim = 4;
    }

    public int swim(int distance) {
        power -= powerPerSwim * distance;
        if (power <= 0) {
            System.out.println("У " + name + " закончилась выносливость и он утонул");
            return -1;
        }
        time = distance/swimSpeed;
        System.out.println(name + " проплыл " + distance + " метра за " + time + " сек");
        return time;
    }
}
