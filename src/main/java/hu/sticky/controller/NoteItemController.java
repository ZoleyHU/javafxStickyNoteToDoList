package hu.sticky.controller;

import hu.sticky.App;
import hu.sticky.dao.NoteDaoImpl;
import hu.sticky.event.NoteEvent;
import hu.sticky.model.StickyNote;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class NoteItemController {
    public VBox containerVbox;
    public Label noteText;
    public Label deadlineText;
    public Label postponesText;
    public Button deleteButton;
    public Button postponeButton;
    private int noteId;

    public void setData(StickyNote stickyNote) {
        noteId = stickyNote.getId();
        noteText.setText(stickyNote.getNoteDescription());
        deadlineText.setText(stickyNote.getDeadline().toString());
        postponesText.setText(Integer.toString(stickyNote.getPostpones()));
        containerVbox.setStyle("-fx-background-color: "+stickyNote.getBackground()+";");

        disablePostponeButton(stickyNote.getPostpones());

        //todo changing font color for better visibility
        //todo adding a black border for better looking notes
    }

    private StickyNote getData() {
        return new StickyNote(
                noteId,
                noteText.getText(),
                Integer.parseInt(postponesText.getText()),
                LocalDate.parse(deadlineText.getText()),
                "#"+containerVbox.getBackground().getFills().get(0).getFill().toString().substring(2)
        );
    }

    public void postpone(ActionEvent actionEvent) {
        if (Integer.parseInt(postponesText.getText()) > 0) {
            StickyNote stickyNote = getData();
            stickyNote.setPostpones(stickyNote.getPostpones()-1);
            stickyNote.setDeadline(LocalDate.parse(deadlineText.getText()).plusWeeks(1));

            if (new NoteDaoImpl().postpone(stickyNote)) {
                postponesText.setText(String.valueOf(stickyNote.getPostpones()));
                deadlineText.setText(stickyNote.getDeadline().toString());
                disablePostponeButton(stickyNote.getPostpones());
            }
        }
    }

    private void disablePostponeButton(int postpones) {
        if (postpones == 0) postponeButton.setDisable(true);
    }

    public void openModifyWindow(ActionEvent actionEvent) {
        try {
            StickyNote stickyNote = getData();

            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/modifyNote.fxml"));
            Parent root = fxmlLoader.load();

            ModifyNoteController modifyController = fxmlLoader.getController();

            modifyController.setData(stickyNote);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modify Note");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure, you want to delete this note?");

        alert.showAndWait().ifPresent(response -> {
            if (response.getButtonData().isDefaultButton()) {
                if (new NoteDaoImpl().delete(noteId)) {
                    deleteButton.fireEvent(new NoteEvent(NoteEvent.NOTE_DELETE, containerVbox));

                    Alert succesAlert = new Alert(Alert.AlertType.INFORMATION);
                    succesAlert.setHeaderText("Note was deleted successfully!");

                    succesAlert.show();
                }
                else {
                    Alert failedAlert = new Alert(Alert.AlertType.ERROR);
                    failedAlert.setHeaderText("Note could not be deleted!");

                    failedAlert.show();
                }
            }
        });

    }
}
