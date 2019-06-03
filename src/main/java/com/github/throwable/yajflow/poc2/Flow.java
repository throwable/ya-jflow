package com.github.throwable.yajflow.poc2;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Flow<In, Out>
{
    public static <A> Flow<A, A> Flow() {
        return new Flow<>();
    }

    public <O> Flow<In, O> Eval(Function<Out, O> transform) {
        return new Flow<>();
    }

    public Flow<In, Void> Apply(Consumer<Out> consumer) {
        return new Flow<>();
    }

    public Flow<In, Void> Do(Runnable code) {
        return new Flow<>();
    }

    public <O> Flow<In, O> Take(Supplier<O> supplier) {
        return new Flow<>();
    }

    public <O> Flow<In, O> Call(Flow<Out, O> flow) {
        return new Flow<>();
    }
}
