package hu.sticky.controller;

import hu.sticky.dao.NoteDaoImpl;
import hu.sticky.event.NoteEvent;
import hu.sticky.model.StickyNote;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.time.LocalDate;

public class ModifyNoteController {
    public TextArea descriptionTextArea;
    public ColorPicker noteColor;
    public Label hiddenLabel;
    public Button confirmButton;

    public void modifyNote(ActionEvent actionEvent) {
        StickyNote stickyNote = getData();
        if (isDescriptionNotEmpty(stickyNote.getNoteDescription())) {
            if (new NoteDaoImpl().modify(stickyNote)) {
                closeWindow(actionEvent, true);
                return;
            }
        }
        showError();
    }

    public void closeModifyNoteForm(ActionEvent actionEvent) {
        closeWindow(actionEvent, false);
    }

    private void closeWindow(ActionEvent actionEvent, boolean modified) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

        if (modified) confirmButton.fireEvent(new NoteEvent(NoteEvent.NOTE_MODIFY, new VBox()));

        confirmButton.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));

        stage.close();
    }

    private void showError() {
        Alert failedAlert = new Alert(Alert.AlertType.ERROR);
        failedAlert.setHeaderText("Unable to modify note!");
        failedAlert.setContentText("Please try again later and ensure you are connected to the database");

        failedAlert.show();
    }

    private boolean isDescriptionNotEmpty(String description) {
        return !description.trim().isEmpty();
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

    protected StickyNote getData() {
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
