package com.github.throwable.yajflow.poc1;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class SampleFlow {
    /*interface Consumer<In> {
        void accept(In in);
    }*/

    static class ServiceInvocation<In, Out> {

    }

    static abstract class Scope<In> {
        public final FlowBuilder<In> flow = new FlowBuilder<>();
    }

    public static <In> FlowBuilder<In> Flow() {
        return new FlowBuilder<>();
    }

    static class FlowBuilder<In>
    {
        public <Out> FlowBuilder<Out> Eval(Function<In, Out> transform) {
            return new FlowBuilder<>();
        }

        public FlowBuilder<Void> Set(Consumer<In> consumer) {
            return new FlowBuilder<>();
        }

        public FlowBuilder<Void> Do(Runnable code) {
            return new FlowBuilder<>();
        }

        public <Out> FlowBuilder<Out> Get(Supplier<Out> supplier) {
            return new FlowBuilder<>();
        }

        public <Out> FlowBuilder<Out> Invoke(ServiceInvocation<In, Out> serviceInvocation) {
            return new FlowBuilder<>();
        }

        public <Void> FlowBuilder<Void> With(Scope<In> scope) {
            return new FlowBuilder<>();
        }


        public <Out> FlowBuilder<Out> With(Function<FlowBuilder<In>, FlowBuilder<Out>> fun) {
            return new FlowBuilder<>();
        }

        public <Out> FlowBuilder<Out> With1(FlowGen<In, Out> flowGen) {
            return new FlowBuilder<>();
        }

        public <Out, P1> FlowBuilder<Out> Scope(Scope1<In, Out, P1> scope1) {
            return scope1.define(null);
        }
    }


    interface Scope1<In, Out, P1> {
        FlowBuilder<Out> define(P1 p1);

        default void test() {

        }
    }

    interface FlowGen<In, Out> {
        FlowBuilder<Out> gen();

        default FlowBuilder<In> flow() {
            return new FlowBuilder<>();
        }
    }
}
