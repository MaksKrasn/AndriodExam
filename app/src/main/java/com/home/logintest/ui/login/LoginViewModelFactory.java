package com.home.logintest.ui.login;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.home.logintest.data.LoginDataSource;
import com.home.logintest.data.LoginRepository;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Фабрика поставщика ViewModel для создания экземпляра LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 * Обязательный, учитывая, что LoginViewModel имеет непустой конструктор
 */
public class LoginViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(LoginRepository.getInstance(new LoginDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}