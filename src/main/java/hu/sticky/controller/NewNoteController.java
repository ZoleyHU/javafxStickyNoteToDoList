package hu.sticky.controller;

import java.io.IOException;
import java.time.LocalDate;

import hu.sticky.App;
import hu.sticky.dao.NoteDaoImpl;
import hu.sticky.model.StickyNote;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

public class NewNoteController {

    public TextArea descriptionTextArea;
    public DatePicker deadlineDate;
    public ToggleGroup postponable;
    public ColorPicker noteColor;

    public void addNewNote(ActionEvent actionEvent) {
        String description = this.descriptionTextArea.getText().trim();
        var deadline = this.deadlineDate.getValue();
        int postpones = ((RadioButton)this.postponable.getSelectedToggle()).getText().equals("Yes") ? 2 : 0;
        String color = "#"+this.noteColor.getValue().toString().substring(2);

        if(checkValidValues(description, deadline)) {
            StickyNote stickyNote = new NoteDaoImpl().add(new StickyNote(description, postpones, deadline, color));

            if (stickyNote == null) {
                showErrorMessage("Please ensure you are connected to the database and check if your input is correct!");
            } else {
                showSuccessMessage();
                resetFields();
            }
        } else {
            showErrorMessage( "Please check if your input is correct and try again!");
        }
    }

    private boolean checkValidValues(String description, LocalDate deadline) {
        if (description.isEmpty()) return false;
        if (deadline == null) return false;
        if (!deadline.isAfter(LocalDate.now())) return false;
        return true;
    }

    private void showErrorMessage(String content) {
        Alert failedAlert = new Alert(Alert.AlertType.ERROR);
        failedAlert.setHeaderText("Unable to add new note!");
        failedAlert.setContentText(content);

        failedAlert.show();
    }

    private void resetFields() {
        descriptionTextArea.setText("");
        deadlineDate.setValue(null);
        noteColor.setValue(Color.WHITE);
    }

    private void showSuccessMessage() {
        Alert succesAlert = new Alert(Alert.AlertType.INFORMATION);
        succesAlert.setHeaderText("Note was added successfully!");

        succesAlert.show();
    }

    public void closeNewNoteForm(ActionEvent actionEvent) throws IOException{
        App.setRoot("view/mainWindow");
    }
}