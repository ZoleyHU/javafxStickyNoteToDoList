package hu.sticky.controller;

import hu.sticky.dao.NoteDaoImpl;
import hu.sticky.event.NoteEvent;
import hu.sticky.model.StickyNote;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class NoteItemController {
    public VBox containerVbox;
    public Label noteText;
    public Label deadlineText;
    public Label postponesText;
    public Button deleteButton;
    private int noteId;

    public void setData(StickyNote stickyNote) {
        noteId = stickyNote.getId();
        noteText.setText(stickyNote.getNoteDescription());
        deadlineText.setText("Deadline: " + stickyNote.getDeadline());
        postponesText.setText("Remaining postopnes: " + stickyNote.getPostpones());
        containerVbox.setStyle("-fx-background-color: "+stickyNote.getBackground()+";");

        //todo changing font color for better visibility
        //todo adding a black border for better looking notes
    }

    private StickyNote getData() {
        int postpones = Integer.parseInt(postponesText.getText().substring(postponesText.getText().length()-1));
        return new StickyNote(
                noteId,
                noteText.getText(),
                 postpones > 0,
                postpones,
                LocalDate.parse(deadlineText.getText().substring(10) ),
                "#"+containerVbox.getBackground().getFills().get(0).getFill().toString().substring(2)
        );
    }

    public void postpone(ActionEvent actionEvent) {
        //todo postopnes should be an embedded property?
        //todo these methods should be implemented in daoimpl because they communicate with the db?
    }

    public void modify(ActionEvent actionEvent) {
        StickyNote stickyNote = getData();
        //todo implement
        System.out.println(stickyNote.getId());
        System.out.println(stickyNote.getDeadline());
        System.out.println(stickyNote.isPostponable());
        System.out.println(stickyNote.getPostpones());
        System.out.println(stickyNote.getBackground());
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