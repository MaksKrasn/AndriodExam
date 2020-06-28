package com.home.logintest.data;

import android.content.Context;

import com.home.logintest.data.model.LoggedInUser;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 * Класс, который запрашивает аутентификацию и информацию о пользователе из удаленного источника данных и
 * поддерживает в кеше в памяти информацию о состоянии входа и информацию о пользователях.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;
    private Context ctx;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // Если учетные данные пользователя будут кэшироваться в локальном хранилище, рекомендуется зашифровать
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    // private конструктор: одиночный доступ
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // Если учетные данные пользователя будут кэшироваться в локальном хранилище, рекомендуется зашифровать
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<LoggedInUser> login(String username, String password, Context ctx) {
        // handle login
        // обрабатывать логин
        this.ctx = ctx;
        Result<LoggedInUser> result = dataSource.login(username, password, ctx);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }
}