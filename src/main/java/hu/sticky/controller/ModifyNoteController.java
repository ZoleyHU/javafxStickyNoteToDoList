package hu.sticky.controller;

import hu.sticky.dao.NoteDaoImpl;
import hu.sticky.model.StickyNote;
import javafx.event.ActionEvent;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.time.LocalDate;

public class ModifyNoteController {
    public TextArea descriptionTextArea;
    public ColorPicker noteColor;
    public Label hiddenLabel;

    public void modifyNote(ActionEvent actionEvent) {
        StickyNote stickyNote = getData();
        if (new NoteDaoImpl().modify(stickyNote)) {
            //todo feedback to the user about successful modification
            //todo close window
            //todo emit event, to update the notes on screen
        }
    }

    public void closeModifyNoteForm(ActionEvent actionEvent) throws IOException {
        //todo close window
    }

    public void setData(StickyNote stickyNote) {
        descriptionTextArea.setText(stickyNote.getNoteDescription());
        noteColor.setValue(Color.web(stickyNote.getBackground()));
        hiddenLabel.setText(
                stickyNote.getId()+";"+
                stickyNote.getPostpones()+";"+
                stickyNote.getDeadline()
        );
    }

    private StickyNote getData() {
        String[] hiddenProperties = hiddenLabel.getText().split(";");
        return new StickyNote(
                Integer.parseInt(hiddenProperties[0]),
                descriptionTextArea.getText(),
                Integer.parseInt(hiddenProperties[1]),
                LocalDate.parse(hiddenProperties[2]),
                "#"+noteColor.getValue().toString().substring(2)
        );
    }
}
