package nl.jixxed.eliteodysseymaterials.service.event;

import javafx.application.Platform;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Getter
@Slf4j
public class EventListener<T extends Event> {
    protected final boolean fxThread;
    protected final Integer priority;
    protected final Class<T> eventClass;
    protected final Consumer<T> consumer;

    EventListener(final boolean fxThread, final Integer priority, final Class<T> eventClass, final Consumer<T> consumer) {
        this.fxThread = fxThread;
        this.priority = priority;
        this.eventClass = eventClass;
        this.consumer = consumer;
    }

    void handleEvent(final T event) {
        if (fxThread) {
            Platform.runLater(() -> this.consumer.accept(event));
        } else {
            this.consumer.accept(event);
        }

    }

}
