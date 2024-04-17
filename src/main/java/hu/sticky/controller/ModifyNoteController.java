package hu.sticky.controller;

import hu.sticky.dao.NoteDaoImpl;
import hu.sticky.model.StickyNote;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.LocalDate;

public class ModifyNoteController {
    public TextArea descriptionTextArea;
    public ColorPicker noteColor;
    public Label hiddenLabel;

    public void modifyNote(ActionEvent actionEvent) {
        StickyNote stickyNote = getData();
        if (isDescriptionNotEmpty(stickyNote.getNoteDescription())) {
            if (new NoteDaoImpl().modify(stickyNote)) {
                //todo check if binded properties work, so events become unnecessary
                //todo emit event, to update the notes on screen

                closeWindow(actionEvent);
                return;
            }
        }
        showError();
    }

    public void closeModifyNoteForm(ActionEvent actionEvent) {
        closeWindow(actionEvent);
    }

    private void closeWindow(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
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
