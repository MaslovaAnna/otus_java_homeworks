package main.java.ru.otus.homeworks.hw7;

public interface Transport {
    /*
    Каждый из классов должен предоставлять возможность
    перемещаться на определенное расстояние с указанием типа местности
     */
     boolean travel(int distance, TerrainType terrainType);
     TransportType getType();
     int getResurs();

}
