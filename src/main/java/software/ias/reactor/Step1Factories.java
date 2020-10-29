package software.ias.reactor;

import org.w3c.dom.ls.LSOutput;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Step1Factories {

    private static Integer superProcessingFunction(Integer i){
        // Logica
        System.out.println("i = " + i);
        return i + 1;
    }

    public static void main(String[] args) throws InterruptedException {
        Mono.just(0);
        Flux<Integer> fluxInteger = Flux.just(1,2,3,4);

        Integer[] intArray = new Integer[]{1,2,3,4};

        Flux.fromArray(intArray)
                .subscribe(System.out::println);

        Flux.range(1, Integer.MAX_VALUE)
                .filter(integer -> integer % 2 == 0)
                .map(integer -> integer * 3)
                .flatMap(integer -> {
                    return Flux.range(0, integer);
                })
                .buffer(13)
                .delayElements(Duration.ofSeconds(1))
                .take(10);
                //.subscribe(System.out::println);

        final int SIZE = 250;
        Flux.range(0,Integer.MAX_VALUE)
                .flatMap(page -> {
                    return DataBase.selectPage(page, SIZE);
                },8)
                .takeUntil(integers -> integers.isEmpty())
                .flatMap(Flux::fromIterable)
                .subscribe(
                System.out::println
                );
        Thread.sleep(30000);
    }
}
