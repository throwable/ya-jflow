package com.github.throwable.yajflow.poc3;

import java.util.function.Function;

public class Flow<In, Out>
{
    protected Var<In> request;
    protected Var<Out> response;

    /*public static <Input, Output> Flow<Input, Output> Flow() {
        return new Flow<>();
    }

    public static <Input, Output> Flow<Input, Output> Flow(Exchange<Input, Output> exchange) {
        return new Flow<>();
    }*/

    public Flow<In, Out> Do(Runnable runnable) {
        return this;
    }

    public Flow<In, Out> Eval(Function transform) {
        return this;
    }


    public <P1> Flow<In, Out> Scope(Scope1<In, Out, P1> scope) {
        return this;
    }

    public <P1, P2> Flow<In, Out> Scope(Scope2<In, Out, P1, P2> scope) {
        return this;
    }

    interface Scope0<In, Out> {
        Flow<In, Out> def();
    }

    interface Scope1<In, Out, P1> {
        Flow<In, Out> def(Var<P1> p1);
    }

    interface Scope2<In, Out, P1, P2> {
        Flow<In, Out> def(Var<P1> p1, Var<P2> p2);
    }

    interface Exchange<In, Out> {
        Flow<In, Out> def(final Var<In> request, final Var<Out> response, Flow<In, Out> flow);
    }
}
