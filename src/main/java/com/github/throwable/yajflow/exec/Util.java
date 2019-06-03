package com.github.throwable.yajflow.exec;

import com.github.throwable.yajflow.Named;
import com.github.throwable.yajflow.Var;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util
{
    public static List<String> parseLambdaParameterNames(Object samObject)
    {
        final Method method = samObject.getClass().getMethods()[0];
        final List<String> names = new ArrayList<>();
        for (Parameter parameter : method.getParameters()) {
            final Named named = parameter.getAnnotation(Named.class);
            if (named != null)
                names.add(named.value());
            else {
                names.add(parameter.getName());
            }
        }
        return names;
    }
}
