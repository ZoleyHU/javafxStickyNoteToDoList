package hu.sticky.controller;

import hu.sticky.model.StickyNote;
import javafx.event.ActionEvent;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

public class ModifyNoteController {
    public TextArea descriptionTextArea;
    public DatePicker deadlineDate;
    public ColorPicker noteColor;

    public void modifyNote(ActionEvent actionEvent) {
    }

    public void closeModifyNoteForm(ActionEvent actionEvent) {
    }

    public void setData(StickyNote stickyNote) {
        descriptionTextArea.setText(stickyNote.getNoteDescription());
        deadlineDate.setValue(stickyNote.getDeadline());
        noteColor.setValue(Color.web(stickyNote.getBackground()));
    }
}
