package com.github.throwable.yajflow.poc3;

import com.github.throwable.yajflow.poc3.Flow;
import com.github.throwable.yajflow.poc3.Var;

public class ScratchBook3 {
    /*{
        Flow()
                .Scope((Var<String> s) -> Flow()
                        .Do(() -> {
                            s.set(s.get() + " - Test");
                        })
                );


        Flow((final Var<String> input,
              final Var<Integer> output) -> {
                }
        );
        Flow((Var<String> request,
              Var<Integer> response,
              Flow<String, Integer> flow) -> flow
                .Do(System::currentTimeMillis) );
    }*/


    class MyFlow extends Flow<String, Integer> {{
        this
                .Do(System::currentTimeMillis)
                .Scope((
                        final Var<String> message,
                        final Var<Integer> errorCode
                        ) -> this
                        .Do(() -> System.out.println(""))
                );
    }}
}
