package hu.sticky.event;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.layout.VBox;

public class NoteEvent extends Event {

    public static final EventType<NoteEvent> NOTE_DELETE = new EventType<>(NoteEvent.ANY, "NOTE_DELETE");
    public static final EventType<NoteEvent> NOTE_MODIFY = new EventType<>(NoteEvent.ANY, "NOTE_MODIFY");

    private final VBox deletableVbox;
    public NoteEvent(EventType<? extends Event> eventType, VBox deletableVbox) {
        super(eventType);
        this.deletableVbox = deletableVbox;
    }

    public VBox getDeletableVbox() {
        return deletableVbox;
    }
}
