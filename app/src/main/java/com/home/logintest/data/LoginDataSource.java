package com.home.logintest.data;

import android.content.Context;

import com.home.logintest.data.model.LoggedInUser;
import com.home.logintest.database.PeopleHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 * Класс, который обрабатывает аутентификацию с учетными данными / login и получает информацию о пользователе.
 */
public class LoginDataSource {
    LoggedInUser currentUser;
    public Result<LoggedInUser> login(String username, String password, Context ctx) {

        try {
            // TODO: handle loggedInUser authentication
            // TODO: обрабатывать аутентификацию loggedInUser
//            LoggedInUser fakeUser =
//                    new LoggedInUser(
//                            java.util.UUID.randomUUID().toString(),
//                            "Jane Doe");
            List<LoggedInUser> people;
            PeopleHandler dbHandler = new PeopleHandler( ctx );
            people = dbHandler.getAll();
            for (LoggedInUser us: people) {
                if(username.equals( us.getDisplayName() )){
                    currentUser = us;
                    if(us.getPassword().equals( password )){
                        return new Result.Success<>(currentUser);
                    }
                    else{
                        return new Result.Error(new IOException("Error logging in"));
                    }
                }
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
        return null;
    }

    public void logout() {
        // TODO: revoke authentication
        // TODO: отменить аутентификацию
    }
}