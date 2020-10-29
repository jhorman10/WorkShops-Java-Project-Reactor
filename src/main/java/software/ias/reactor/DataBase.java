package software.ias.reactor;

import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DataBase {
    public static final List<Integer> db = IntStream.range(0, 1_000_000)
            .boxed()
            .collect(Collectors.toList());
    public static Mono<List<Integer>> selectPage(int page, int size) {
        int fromIndex = size * page;
        int toIndex = fromIndex + size;
        return Mono.defer(()->{
            return  Mono.just(db.subList(fromIndex, toIndex));
        })
                .doOnError(Throwable::printStackTrace)
                .onErrorReturn(List.of())
                .delaySubscription(Duration.ofMillis(25));
    }
}
