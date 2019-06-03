package com.github.throwable.yajflow;

import static com.github.throwable.yajflow.Flow.*;
import static com.github.throwable.yajflow.Process.*;

public class ScratchBook4 {
    {
        final Process<String, Integer> stringToInt =
                Process((final Var<String> request,
                         final Var<Integer> response) -> Do()

                        .Run(() -> response.set(Integer.parseInt(request.get())))
                );

        Process((final Var<String> request,
                 final Var<String> response) -> Do()

                .Scope((final Var<Integer> errCode,
                        final Var<String> errDesc,
                        final Var<Integer> integerParsed) -> Do()

                        .Run(System.out::println)
                        .Call(stringToInt, request, integerParsed)
                )
        );

        // TODO: design input builder that can filter input message
        final OneWayService<String> eventService = null;
        final OneWayService<Integer> notificationService = null;
        final Service<String, Integer> externalService = null;

        Process((
                final Var<String> request,
                final Var<String> response) -> Do().Scope((
                final Var<String> localVar1,
                final Var<Integer> localVar2) -> Do()

                .Run(System.out::println)
                .Scope((final Var<Integer> errCode,
                        final Var<String> errDesc,
                        final Var<Integer> integerParsed) -> Do()

                        .Run(System.out::println)
                        .Call(stringToInt, request, integerParsed)

                        .Receive(eventService, response)
                        .Send(notificationService, localVar2)
                        .Invoke(externalService, localVar1, localVar2)
                ))

                .Run(System.out::println)
        );
    }
}
