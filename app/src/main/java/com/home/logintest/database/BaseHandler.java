package com.home.logintest.database;

import java.util.List;

public interface BaseHandler<T> {
    void create(T item);
    List<T> getAll();
}
