package hu.sticky.event;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.layout.VBox;

public class NoteEvent extends Event {

    public static final EventType<NoteEvent> NOTE_DELETE = new EventType<>(NoteEvent.ANY, "NOTE_DELETE");

    private final VBox deletableVbox;
    public NoteEvent(EventType<? extends Event> eventType, VBox deletableVbox) {
        super(eventType);
        this.deletableVbox = deletableVbox;
    }

    public VBox getDeletableVbox() {
        return deletableVbox;
    }
}
