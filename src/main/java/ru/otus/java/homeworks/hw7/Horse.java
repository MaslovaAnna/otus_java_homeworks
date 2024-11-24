package ru.otus.java.homeworks.hw7;

import static ru.otus.java.homeworks.hw7.TerrainType.SWAMP;
import static ru.otus.java.homeworks.hw7.TransportType.HORSE;

public class Horse implements Transport {

    protected int resurs;
    protected int rashod;

    public Horse(int resurs, int rashod) {
        this.resurs = resurs;
        this.rashod = rashod;
    }

    @Override
    public boolean travel(int distance, TerrainType terrainType) {
        if (terrainType == SWAMP){
            System.out.println("Проехать на лошади невозможно");
            return false;
        }
        resurs -= distance * rashod;
        if (resurs < 0) {
            System.out.println("Лошади не хватило сил доехать до конца");
            return false;
        }
        System.out.println("Проехали " + distance);
        return true;
    }

    public TransportType getType() {
        return HORSE;
    }

    public int getResurs() {
        return resurs;
    }
}
