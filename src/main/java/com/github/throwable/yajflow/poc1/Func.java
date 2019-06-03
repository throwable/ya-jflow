package com.github.throwable.yajflow.poc1;

import java.util.function.Consumer;
import java.util.function.Function;

public class Func<In> {
    public <Res> Func<Res> Do(Function<In, Res> arg) {
        return new Func<>();
    }

    public Task Done(Consumer<In> consumer) {
        return new Task();
    }
}
