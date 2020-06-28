package com.home.logintest.data.model;

/*
 * Data class that captures user information for logged in users retrieved from LoginRepository
 * Класс данных, который собирает информацию о пользователях, вошедших в систему, полученную из LoginRepository
 */
public class LoggedInUser {

    private int id;
    private String displayName;
    private String password;

    public LoggedInUser(int id, String displayName, String password) {
        this.id = id;
        this.displayName = displayName;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPassword() { return password; }
}