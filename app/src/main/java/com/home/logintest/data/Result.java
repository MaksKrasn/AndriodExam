package com.home.logintest.data;

/**
 * A generic class that holds a result success w/ data or an error exception.
 * Универсальный класс, который содержит результат успеха с данными или исключение ошибки.
 */
public class Result<T> {
    // hide the private constructor to limit subclass types (Success, Error)
    // скрыть приватный конструктор для ограничения типов подклассов (успех, ошибка)
    private Result() {
    }

    @Override
    public String toString() {
        if (this instanceof Result.Success) {
            Result.Success success = (Result.Success) this;
            return "Success[data=" + success.getData().toString() + "]";
        } else if (this instanceof Result.Error) {
            Result.Error error = (Result.Error) this;
            return "Error[exception=" + error.getError().toString() + "]";
        }
        return "";
    }

    // Success sub-class
    // Подкласс успеха
    public final static class Success<T> extends Result {
        private T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return this.data;
        }
    }

    // Error sub-class
    // Подкласс ошибки
    public final static class Error extends Result {
        private Exception error;

        public Error(Exception error) {
            this.error = error;
        }

        public Exception getError() {
            return this.error;
        }
    }
}