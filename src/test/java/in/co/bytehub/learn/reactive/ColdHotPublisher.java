package in.co.bytehub.learn.reactive;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

public class ColdHotPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(ColdHotPublisher.class);
    private List<String> itemList = Arrays.asList("item1", "item2", "item3", "item4", "item5", "item6");

    @Test
    public void coldPublisherTest() throws InterruptedException {
        Flux<String> stringFlux = Flux.fromIterable(itemList)
                .delayElements(Duration.ofSeconds(1));

        /*
         * We can have multiple subscriber fro same Flux.
         * All of them will get the data.
         * In Cold Publishing, All the subscriber will get data
         * from beginning even if any one of subscriber has subscribed later
         * */

        stringFlux.subscribe(item -> LOGGER.info(String.format("Subscriber1: %s", item)));
        Thread.sleep(Duration.ofSeconds(2).toMillis()); // Give chance to subscriber2

        // Here, 2nd subscriber subscribed 2 second later, Still will get data from item1 i.e. beginning
        // Although Flux is already emitted e items as it is configured to emit the data with interval of 1 second

        stringFlux.subscribe(item -> LOGGER.info(String.format("Subscriber2: %s", item)));
        Thread.sleep(Duration.ofSeconds(10).toMillis()); // Just wait unless both subscriber finish operation
    }

    @Test
    public void hotPublisherTest() throws InterruptedException {
        Flux<String> stringFlux = Flux.fromIterable(itemList)
                .delayElements(Duration.ofSeconds(1));

        /*
         * We can have multiple subscriber fro same Flux.
         * All of them will get the data.
         * In Hot Publishing, Subscriber will start getting data after it's subscription
         * It will miss the data which is already emitted  by flux to the other subscribers
         * */

        ConnectableFlux<String> connectableFlux = stringFlux.publish();
        connectableFlux.connect();

        // Al the subscribers connected with (subscribed) to connectableFlux will in HOT publishing mode
        // Means if any subscriber subscribes later it will not get data which is already emitted by connectableFlux
        // to other subscribers

        connectableFlux.subscribe(item -> LOGGER.info(String.format("Hot-Subscriber1: %s", item)));
        Thread.sleep(Duration.ofSeconds(2).toMillis()); // Give chance to subscriber2

        // Here, 2nd subscriber subscribed 2 second later, Still will get data from item1 i.e. beginning
        // Although Flux is already emitted e items as it is configured to emit the data with interval of 1 second

        connectableFlux.subscribe(item -> LOGGER.info(String.format("Hot-Subscriber2: %s", item)));
        Thread.sleep(Duration.ofSeconds(2).toMillis()); // Give chance to subscriber2

        stringFlux.subscribe(item -> LOGGER.info(String.format("Cold Subscriber: %s", item)));

        Thread.sleep(Duration.ofSeconds(10).toMillis()); // Just wait unless both subscriber finish operation

/*
        -----------------------------* Sample Response *----------------------------------------------------

        10:12:34.195 [parallel-1] INFO in.co.bytehub.learn.reactive.ColdHotPublisher - Hot-Subscriber1: item1
        10:12:35.201 [parallel-2] INFO in.co.bytehub.learn.reactive.ColdHotPublisher - Hot-Subscriber1: item2
        10:12:35.201 [parallel-2] INFO in.co.bytehub.learn.reactive.ColdHotPublisher - Hot-Subscriber2: item2
        10:12:36.204 [parallel-3] INFO in.co.bytehub.learn.reactive.ColdHotPublisher - Hot-Subscriber1: item3
        10:12:36.204 [parallel-3] INFO in.co.bytehub.learn.reactive.ColdHotPublisher - Hot-Subscriber2: item3
        10:12:37.206 [parallel-4] INFO in.co.bytehub.learn.reactive.ColdHotPublisher - Hot-Subscriber1: item4
        10:12:37.206 [parallel-4] INFO in.co.bytehub.learn.reactive.ColdHotPublisher - Hot-Subscriber2: item4
        10:12:38.198 [parallel-1] INFO in.co.bytehub.learn.reactive.ColdHotPublisher - Cold Subscriber: item1
        10:12:38.209 [parallel-2] INFO in.co.bytehub.learn.reactive.ColdHotPublisher - Hot-Subscriber1: item5
        10:12:38.209 [parallel-2] INFO in.co.bytehub.learn.reactive.ColdHotPublisher - Hot-Subscriber2: item5
        10:12:39.200 [parallel-3] INFO in.co.bytehub.learn.reactive.ColdHotPublisher - Cold Subscriber: item2
        10:12:39.211 [parallel-4] INFO in.co.bytehub.learn.reactive.ColdHotPublisher - Hot-Subscriber1: item6
        10:12:39.211 [parallel-4] INFO in.co.bytehub.learn.reactive.ColdHotPublisher - Hot-Subscriber2: item6
        10:12:40.210 [parallel-1] INFO in.co.bytehub.learn.reactive.ColdHotPublisher - Cold Subscriber: item3
        10:12:41.212 [parallel-2] INFO in.co.bytehub.learn.reactive.ColdHotPublisher - Cold Subscriber: item4
        10:12:42.213 [parallel-3] INFO in.co.bytehub.learn.reactive.ColdHotPublisher - Cold Subscriber: item5
        10:12:43.214 [parallel-4] INFO in.co.bytehub.learn.reactive.ColdHotPublisher - Cold Subscriber: item6*/
    }
}
