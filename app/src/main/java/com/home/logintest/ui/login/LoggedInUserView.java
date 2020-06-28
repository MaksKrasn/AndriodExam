package com.home.logintest.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 * Класс, предоставляющий аутентифицированные пользовательские данные пользовательскому интерфейсу.
 */
class LoggedInUserView {
    private String displayName;
    //... other data fields that may be accessible to the UI
    //... другие поля данных, которые могут быть доступны для пользовательского интерфейса

    LoggedInUserView(String displayName) {
        this.displayName = displayName;
    }

    String getDisplayName() {
        return displayName;
    }
}