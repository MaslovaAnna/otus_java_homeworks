package ru.otus.java.homeworks.hw7;

import static ru.otus.java.homeworks.hw7.TransportType.LEGS;

public class Legs implements Transport {
    protected int resurs;
    private int rashod;

    public Legs(int resurs, int rashod) {
        this.resurs = resurs;
        this.rashod = rashod;
    }

    public boolean travel(int distance, TerrainType terrainType) {
        resurs -= distance * rashod;
        if (resurs < 0) {
            System.out.println("Не хватило сил дойти до конца");
            return false;
        }
        System.out.println("Прошли " + distance);
        return true;
    }

    public TransportType getType() {
        return LEGS;
    }
    public int getResurs() {
        return resurs;
    }
}
