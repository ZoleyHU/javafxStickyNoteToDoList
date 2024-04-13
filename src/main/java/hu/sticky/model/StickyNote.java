package hu.sticky.model;

import java.time.LocalDate;

public class StickyNote {

    private int id;
    private String noteDescription;
    private boolean postponable;
    private int postpones;
    private LocalDate deadline;
    private String background;

    public StickyNote(String noteDescription, boolean postponable, LocalDate deadline, String background) {
        this.noteDescription = noteDescription;
        this.postponable = postponable;
        this.deadline = deadline;
        this.background = background;

        this.postpones = this.postponable ? 2 : 0;
    }

    public StickyNote(int id, String noteDescription, boolean postponable, int postpones, LocalDate deadline, String background) {
        this.id = id;
        this.noteDescription = noteDescription;
        this.postponable = postponable;
        this.postpones = postpones;
        this.deadline = deadline;
        this.background = background;
    }

    public int getId() {
        return id;
    }


    public String getNoteDescription() {
        return noteDescription;
    }

    public boolean isPostponable() {
        return postponable;
    }

    public int getPostpones() {
        return postpones;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public String getBackground() {
        return background;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public void setPostponable(boolean postponable) {
        this.postponable = postponable;
    }

    public void setPostpones(int postpones) {
        this.postpones = postpones;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
