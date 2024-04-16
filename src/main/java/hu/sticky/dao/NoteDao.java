package hu.sticky.dao;

import hu.sticky.model.StickyNote;

import java.util.List;

public interface NoteDao {
    List<StickyNote> getNotes();
    StickyNote add(StickyNote stickyNote);
    boolean delete(int id);
    boolean modify(StickyNote stickyNote);
    boolean postpone(StickyNote stickyNote);
}
