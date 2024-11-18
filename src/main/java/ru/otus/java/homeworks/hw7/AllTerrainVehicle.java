package ru.otus.java.homeworks.hw7;

import static ru.otus.java.homeworks.hw7.TransportType.AllTERRAINVEHICLE;

public class AllTerrainVehicle implements Transport {
    protected int resurs;
    protected int rashod;

    public AllTerrainVehicle(int resurs, int rashod) {
        this.resurs = resurs;
        this.rashod = rashod;
    }

    public boolean travel(int distance, TerrainType terrainType) {
        resurs -= distance * rashod;
        if (resurs < 0) {
            System.out.println("Не хватило бензина доехать до конца");
            return false;
        }
        System.out.println("Проехали " + distance);
        return true;
    }

    public TransportType getType() {
        return AllTERRAINVEHICLE;
    }

    public int getResurs() {
        return resurs;
    }
}
