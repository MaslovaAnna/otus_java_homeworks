package ru.otus.java.homeworks.hw7;

import static ru.otus.java.homeworks.hw7.TransportType.*;

public class Person {
    /*
    Создайте класс Человек с полями name (имя) и currentTransport (текущий транспорт)
    Реализуйте в вашем приложении классы Машина, Лошадь, Велосипед, Вездеход

    Человек должен иметь возможность сесть на любой из этих видов транспорта,
    встать с него, или переместиться на некоторое расстояние (при условии что он находится на каком-либо транспорте)
    При попытке выполнить перемещение у человека, не использующего транспорт,
    считаем что он просто идет указанное расстояние пешком

    При перемещении Машина и Вездеход тратят бензин, который у них ограничен.
    Лошадь тратит силы. Велосипед может использоваться без ограничений (можете для усложнения велосипедом тратить силы “водителя”).
    При выполнении действия результат должен быть отпечатан в консоль
    У каждого вида транспорта есть местности по которым он не может перемещаться:
    машина - густой лес и болото, лошадь и велосипед - болото, вездеход - нет ограничений
    При попытке переместиться должен быть возвращен результат true/false - удалось ли выполнить действие
     */
    private String name;
    public TransportType currentTransport;
    private int resurs = 20;
    private final int rashod = 2;

    public Person(String name) {
        this.name = name;
        currentTransport = LEGS;
    }
    Transport[] transports = {
            new Bicycle(resurs, rashod),
            new Car(500, 5),
            new AllTerrainVehicle(200, 10),
            new Horse(50, 2),
            new Legs(resurs, rashod)
    };

    public void sit(TransportType currentTransport) {
        if (this.currentTransport != LEGS) {
            System.out.println("Сначала встаньте с " + currentTransport);
            return;
        }
        this.currentTransport = currentTransport;
        System.out.println("Человек сел на транспорт: " + this.currentTransport);
    }

    public void standUp() {
        if (currentTransport == LEGS) {
            System.out.println("Человек уже стоит");
            return;
        }
        this.currentTransport = LEGS;
        System.out.println("Человек стоит");
    }


    public void travel(int distance, TerrainType terrainType) {
        for (Transport t : transports) {
            //for (TransportType type : TransportType.values()) --- для перечисления енам констант
            if (currentTransport == t.getType()) {
                t.travel(distance, terrainType);
                if (currentTransport == LEGS || currentTransport == BICYCLE) {
                    this.resurs = t.getResurs();
                }
            }
        }

    }

    public int getResurs() {
        return this.resurs;
    }

    public TransportType getType() {
        return currentTransport;
    }
}
