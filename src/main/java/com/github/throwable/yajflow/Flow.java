package com.github.throwable.yajflow;

import com.github.throwable.yajflow.exec.ProcessVariable;
import com.github.throwable.yajflow.exec.StateStorage;
import com.github.throwable.yajflow.exec.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class Flow
{
    public static Flow Do() {
        return new Flow();
    }

    /*public static <P1> Flow Def(Scope1<P1> scope) {
        return new Flow();
    }
    public static <P1, P2> Flow Def(Scope2<P1, P2> scope) {
        return new Flow();
    }
    public static <P1, P2, P3> Flow Def(Scope3<P1, P2, P3> scope) {
        return new Flow();
    }*/

    public Flow Run(Runnable runnable) {
        return this;
    }

    public Flow Eval(Function transform) {
        return this;
    }


    public <P1> Flow Scope(String name, Scope1<P1> scope) {
        defineScope(name, scope);
        return this;
    }
    public <P1, P2> Flow Scope(Scope2<P1, P2> scope) {
        return this;
    }
    public <P1, P2, P3> Flow Scope(Scope3<P1, P2, P3> scope) {
        return this;
    }

    public <In, Out> Flow Call(Process<In, Out> process, Var<In> in, Var<Out> out) {
        return this;
    }

    public <In, Out> Flow Invoke(Service<In, Out> service, Var<In> in, Var<Out> out) {
        return this;
    }

    public <In> Flow Receive(OneWayService<In> service, Var<In> out) {return this;}

    public <Out> Flow Send(OneWayService<Out> service, Var<Out> in) {return this;}



    private final Map<String, Var<?>> variables = new HashMap<>();

    private Supplier<StateStorage> stateStorageSupplier;


    private void defineScope(String scopeName, Object scope)
    {
        final List<String> names = Util.parseLambdaParameterNames(scope);
        final ArrayList<Var<Object>> variables = new ArrayList<>();
        for (String name : names) {
            Var<Object> variable = new ProcessVariable(scopeName + "." + name, this::getStateStorage);
            variables.add(variable);
        }
        final Object[] args = variables.toArray();
        Flow flow;
        try {
            flow = (Flow) scope.getClass().getMethods()[0].invoke(scope, args);
        } catch (Exception e) {
            throw new ProcessDefinitionException(e);
        }
        flow.bindToParentFlow(this);
    }

    private StateStorage getStateStorage() {
        return stateStorageSupplier.get();
    }

    private void bindToParentFlow(Flow parentFlow) {
        stateStorageSupplier = parentFlow::getStateStorage;
    }


    interface Scope0 {
        Flow define();
    }

    interface Scope1<P1> {
        Flow define(Var<P1> p1);
    }

    interface Scope2<P1, P2> {
        Flow define(Var<P1> p1, Var<P2> p2);
    }

    interface Scope3<P1, P2, P3> {
        Flow define(Var<P1> p1, Var<P2> p2, Var<P3> p3);
    }
}
