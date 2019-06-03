package com.github.throwable.yajflow.exec;

public interface StateStorage {
    void set(String name, Object value);
    Object get(String name);
    void remove(String name);
}
