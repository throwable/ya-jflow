package com.github.throwable.yajflow.poc1;

import com.github.throwable.yajflow.poc1.Flow;
import com.github.throwable.yajflow.poc1.For;
import com.github.throwable.yajflow.poc1.Task;

public class ScratchBook1 {
    class MyFlow extends Flow {
        String var1;
        Integer var2;

        {
            Begin(
                    Do(() -> {
                        System.out.println("SetUp");
                    }),

                    If(() -> var1 == null).Then(
                            Do()
                    ).ElseIf(() -> var1.isEmpty()).Then(
                            Do()
                    ).Else(
                            Do()
                    ),

                    IncludeSomeLogic(),

                    // subflow scope with variables
                    new Flow() {
                        String var3;

                        {
                            Begin(
                                    Do(() -> var3 = "123456")
                            );
                        }
                    },

                    // Transformation chain???
                    /*With(() -> var2)
                            .Do(v -> String.valueOf(v))
                            .Do(s -> s.isEmpty())
                            .Done(b -> System.out.println(b)),*/

                    new For() {

                    }
            );
        }

        Task IncludeSomeLogic() {
            return Do(

            );
        }
    }
}
