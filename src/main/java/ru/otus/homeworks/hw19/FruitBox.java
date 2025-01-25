package main.java.ru.otus.homeworks.hw19;

import java.util.ArrayList;
import java.util.List;

public class FruitBox<T extends Fruit> {
    private List<T> fruits;

    public FruitBox(T... fruit) {
        this.fruits = new ArrayList<>(List.of(fruit));
    }

    public void addFruit(T fruit) {
        fruits.add(fruit);
    }

    public int weight() {
        int sum = 0;
        for (T f : fruits) {
            sum += f.getWeight();
        }
        return sum;
    }
    public boolean compareTo(FruitBox<? extends Fruit> box) {
        return this.weight() - box.weight() == 0;
    }

    public void moveTo(FruitBox<Fruit> box) {
        for (int i = fruits.size()-1; i >= 0; i--) {
            box.addFruit(fruits.get(i));
            fruits.remove(i);
        }
    }

    @Override
    public String toString() {
        return "FruitBox{" +
                "fruits=" + fruits +
                '}';
    }
}
