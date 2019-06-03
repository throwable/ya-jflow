package com.github.throwable.yajflow.poc1;

import com.github.throwable.yajflow.poc1.SampleFlow;

import static com.github.throwable.yajflow.poc1.SampleFlow.*;

public class ScratchBook {
    {
        SampleFlow.ServiceInvocation<String, Integer> parseIntService = new SampleFlow.ServiceInvocation<>();

        new SampleFlow.FlowBuilder<String>()
                .Eval(String::isEmpty)
                .Set(aBoolean -> {
                    System.out.println(aBoolean);
                })
                .Do(() -> {
                    System.out.println("Done");
                })
                .Get(() -> "1234567")
                .Invoke(parseIntService)
                .Set((result) -> {
                    assert result == 1234567;
                })
                .Get(() -> {
                    return new Object() {
                        String x = "qwe";
                    };
                })
                .With(new Scope() {
                    String variable;
                    int value;
                    {
                        flow.Do(() -> System.out.println());
                    }
                });


        final SampleFlow.FlowBuilder<Void> with = new SampleFlow.FlowBuilder<String>()
                .With(flow -> flow
                        .Eval(Integer::parseInt)
                        .With(flow1 -> flow1
                                .Eval(i -> i + 1)
                        )
                        .Set(System.out::println)
                );

        new SampleFlow.FlowBuilder<String>()
                .With1(new SampleFlow.FlowGen<String, Integer>() {
                    @Override
                    public SampleFlow.FlowBuilder<Integer> gen() {
                        return flow().Get(() -> 1);
                    }
                })
                .With1(() -> Flow()
                        .Get(() -> "")
                );

        final FlowBuilder<String> scope = new FlowBuilder<Integer>()
                //.Scope((Scope1<String, Integer, String>) p1 -> new FlowBuilder<>())
                .Scope((String p1) -> {

                    return Flow()
                            .Eval((a) -> a.toString() + " " + p1);
                    }
                );
    }
}
