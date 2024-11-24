package ru.otus.java.homeworks.hw7;

import static ru.otus.java.homeworks.hw7.TerrainType.*;

public interface Transport {
    /*
    Каждый из классов должен предоставлять возможность
    перемещаться на определенное расстояние с указанием типа местности
     */
     boolean travel(int distance, TerrainType terrainType);
     TransportType getType();
     int getResurs();

}
