package ru.otus.java.homeworks.hw7;

import static ru.otus.java.homeworks.hw7.TerrainType.SWAMP;
import static ru.otus.java.homeworks.hw7.TransportType.BICYCLE;


public class Bicycle implements Transport{
    protected int resurs;
    protected int rashod;

    public Bicycle(int resurs, int rashod) {
        this.resurs = resurs;
        this.rashod = rashod/2;
    }
    @Override
    public boolean travel(int distance, TerrainType terrainType) {
        if (terrainType == SWAMP){
            System.out.println("Проехать на велосипеде невозможно");
            return false;
        }
        resurs -= distance * rashod;
        if (resurs < 0) {
            System.out.println("Не хватило сил доехать до конца");
            return false;
        }
        System.out.println("Проехали " + distance);
        return true;
    }

    public TransportType getType() {
        return BICYCLE;
    }

    public int getResurs() {
        return resurs;
    }
}

