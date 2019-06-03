package com.github.throwable.yajflow.exec;

import com.github.throwable.yajflow.*;
import com.github.throwable.yajflow.Process;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessRunner<In, Out> {
    private final Process<In, Out> process;

    private final Map<String, Var<?>> variables = new HashMap<>();

    public ProcessRunner(Process<In, Out> process) {
        this.process = process;
        bindProcess();
    }

    private void bindProcess() {
        final List<String> names = Util.parseLambdaParameterNames(process.definition);
        assert names.size() == 2;
        final Var<Object> request = createVariable(names.get(0));
        final Var<Object> response = createVariable(names.get(1));
        final Flow flow = ((Process.Definition<Object, Object>) process.definition).define(request, response);
    }

    private Var<Object> createVariable(String name) {
        if (variables.containsKey(name))
            throw new ProcessDefinitionException("Duplicate variable name: " + name);
        final ProcessVariable localVar = new ProcessVariable(name, stateStorageSupplier);
        variables.put(name, localVar);
        return localVar;
    }
}
