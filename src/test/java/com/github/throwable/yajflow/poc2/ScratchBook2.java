package com.github.throwable.yajflow.poc2;

import com.github.throwable.yajflow.poc2.Flow;

public class ScratchBook2 {
    {
        new Flow<String, String>()
                .Eval((String input) -> Integer.parseInt(input))
                .Eval(i -> i + 1)
                .Call(Flow.<Integer>Flow()
                        .Eval(i -> i + 1)
                )
                .Eval(i -> i + 2)
                .Take(() -> String.valueOf(123));
    }
}
