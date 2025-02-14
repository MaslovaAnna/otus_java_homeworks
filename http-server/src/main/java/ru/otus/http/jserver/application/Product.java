package ru.otus.http.jserver.application;

public class Product {
    private Long id;
    private String title;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Product(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
