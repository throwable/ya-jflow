package com.github.throwable.yajflow.exec;

import com.github.throwable.yajflow.Var;

import java.util.function.Supplier;

public class ProcessVariable implements Var<Object> {
    private final String name;
    private final Supplier<StateStorage> stateStorageSupplier;

    public ProcessVariable(String name, Supplier<StateStorage> stateStorageSupplier) {
        this.name = name;
        this.stateStorageSupplier = stateStorageSupplier;
    }

    @Override
    public Object get() {
        return stateStorageSupplier.get().get(name);
    }

    @Override
    public void set(Object value) {
        stateStorageSupplier.get().set(name, value);
    }
}
