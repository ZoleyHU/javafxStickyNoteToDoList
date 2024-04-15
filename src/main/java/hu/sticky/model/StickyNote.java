package hu.sticky.model;

import java.time.LocalDate;

public class StickyNote {

    private int id;
    private String noteDescription;
    private int postpones;
    private LocalDate deadline;
    private String background;

    public StickyNote(String noteDescription, int postpones, LocalDate deadline, String background) {
        this.noteDescription = noteDescription;
        this.postpones = postpones;
        this.deadline = deadline;
        this.background = background;
    }

    public StickyNote(int id, String noteDescription, int postpones, LocalDate deadline, String background) {
        this.id = id;
        this.noteDescription = noteDescription;
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
