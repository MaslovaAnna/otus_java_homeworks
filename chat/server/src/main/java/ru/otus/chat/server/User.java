package ru.otus.chat.server;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {

    private int id;
    private String username;
    private String login;
    private String password;
    private LocalDateTime banTime;
    private boolean permBan = false;
    private String role;

    public User(int id, String username, String login, String password) {
        this.id = id;
        this.username = username;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public LocalDateTime getBanTime() {
        return banTime;
    }

    public void setBanTime(LocalDateTime banTime) {
        this.banTime = banTime;
    }

    public boolean isPermBan() {
        return permBan;
    }

    public void setPermBan(boolean permBan) {
        this.permBan = permBan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ru.otus.chat.server.User user = (ru.otus.chat.server.User) o;
        return id == user.id && Objects.equals(username, user.username) && Objects.equals(login, user.login) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, login, password);
    }

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", username='" + username + '\'' +
               ", login='" + login + '\'' +
               ", password='" + password + '\'' +
               ", role='" + role +
               '}';
    }
}

