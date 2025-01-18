package main.java.ru.otus.homeworks.hw7;

import static main.java.ru.otus.homeworks.hw7.TerrainType.*;
import static main.java.ru.otus.homeworks.hw7.TransportType.CAR;

public class Car implements Transport {

    protected int resurs;
    protected int rashod;

    public Car(int resurs, int rashod) {
        this.resurs = resurs;
        this.rashod = rashod;
    }

    @Override
    public boolean travel(int distance, TerrainType terrainType) {
        if (terrainType == SWAMP || terrainType == FORREST){
            System.out.println("Проехать на машине невозможно");
            return false;
        }
        resurs -= distance * rashod;
        if (resurs < 0) {
            System.out.println("Не хватило бензина доехать до конца");
            return false;
        }
        System.out.println("Проехали " + distance);
        return true;
    }

    public TransportType getType() {
        return CAR;
    }

    public int getResurs() {
        return resurs;
    }
}
