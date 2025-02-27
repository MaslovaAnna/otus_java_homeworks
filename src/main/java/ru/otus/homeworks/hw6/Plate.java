package main.java.ru.otus.homeworks.hw6;

public class Plate {
    /*
    Реализуйте классы Тарелка (максимальное количество еды, текущее количество еды) и Кот (имя, аппетит).
    Количество еды измеряем в условных единицах.
     */
    private int maxFood;
    private int currentFood;

    //    При создании тарелки указывается ее объем и она полностью заполняется едой
    public Plate(int maxFood) {
        this.maxFood = maxFood;
        this.currentFood = maxFood;

    }
    public void addFood(int food) {
        /*
        В тарелке должен быть метод, позволяющий добавить еду в тарелку.
        После добавления в тарелке не может оказаться еды больше максимума
         */
        currentFood += food;
        if (currentFood > maxFood) {
            System.out.println("В тарелке не может быть еды больше чем " + currentFood);
            currentFood = maxFood;
        } else {
            System.out.println("В тарелке: " + currentFood + " еды");
        }
    }

    /*
    В тарелке должен быть boolean метод уменьшения количества еды,
    при этом после такого уменьшения, в тарелке не может оказаться отрицательное количество еды
    (если удалось уменьшить еду так,
    чтобы в тарелке осталось >= 0 кусков еды, то возвращаем true, в противном случае - false).
     */
    public boolean eatingFood(int food) {
        if (currentFood < food) {
            System.out.println("В тарелке не хватает еды");
            return false;
        }
        currentFood -= food;
        System.out.println("В тарелке: " + currentFood + " еды");
        return true;
    }
}
