package software.ias.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

public class intro {

    private static void printOnNextMethod(Integer i) {
        System.out.println("Integer = " + i);
    }

    public static void main(String[] args) throws InterruptedException {
        Mono<Object> objectMono = Mono.create(monoSink -> {
            System.out.println("About to send value");
            monoSink.success(1);
        });

        Flux<Integer> integerFlux = Flux.create(fluxSink -> {
            fluxSink.next(1);
            fluxSink.next(2);
            fluxSink.next(3);
            fluxSink.next(4);
            fluxSink.next(5);
            fluxSink.next(6);
            fluxSink.complete();
        });

        integerFlux.subscribe(
                intro::printOnNextMethod,
                throwable -> throwable.printStackTrace(),
                () -> {
                    System.out.println("Complete");
                }
        );

        Consumer<Integer> printOnNext = Integer -> {
            System.out.println("Integer = " + Integer);
        };

        objectMono.subscribe(
                Integer -> System.out.println("Integer = " + Integer)
        );

        Thread.sleep(10000);
    }
}
