package com.github.throwable.yajflow.poc1;

import java.util.function.Supplier;

public class Flow extends Task {


    protected void Begin(Task... tasks) {

    }

    /** Wrap lambda */
    protected Task Do(Runnable runnable) {
        return null;
    }

    /** Composed task */
    protected Task Do(Task... tasks) {
        return null;
    }

    protected IfElseTask.IfTask.IfDef If(Supplier<Boolean> p)
    {
        return new IfElseTask.IfTask.IfDef( p );
    }


    /*protected <In, Out> Func<In, Out> With(Supplier<In> supplier) {
        return null;
    }*/
}
