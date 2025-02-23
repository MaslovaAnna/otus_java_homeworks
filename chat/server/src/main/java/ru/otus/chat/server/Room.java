package ru.otus.chat.server;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Room {
    private int id;
    private String name;
    private String password;
    private boolean isDeleted = false;

    private User owner;

    public Room(String name, int id, String password, User owner) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
