package com.github.throwable.yajflow;

public class Process<In, Out>
{
    public Process(Definition<In, Out> definition) {
        this.definition = definition;
    }

    public static <In, Out> Process<In, Out> Process(Definition<In, Out> definition) {
        return new Process<>(definition);
    }

    public interface Definition<In, Out> {
        Flow define(final Var<In> request, final Var<Out> response);
    }


    public final Definition<In, Out> definition;
}
