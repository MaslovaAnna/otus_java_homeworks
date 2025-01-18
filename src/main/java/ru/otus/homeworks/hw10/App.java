package main.java.ru.otus.homeworks.hw10;



public class App {

    public static void main(String[] args) {
        PhoneBook phoneBook= new PhoneBook();


        phoneBook.add("Anna", 89032);
        phoneBook.add("Vasya", 87654);
        phoneBook.add("Vasya", 87354);
        phoneBook.add("Anna", 12345);
        phoneBook.add("Anna", 76587);
        phoneBook.add("Katya", 87657);
        phoneBook.add("Katya", 12345);
        phoneBook.add("Stepa", 89907);
        phoneBook.add("Danya", 76854);
        System.out.println(phoneBook);

        phoneBook.find("Katya");

        phoneBook.containsPhoneNumber(87654);

    }

}

