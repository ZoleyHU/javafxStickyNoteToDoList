package hu.sticky.controller;

import hu.sticky.App;
import hu.sticky.dao.NoteDaoImpl;
import hu.sticky.event.NoteEvent;
import hu.sticky.model.StickyNote;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class NoteItemController {
    public VBox containerVbox;
    public Label noteLabel;
    public Label deadlineLabel;
    public Label postponesLabel;
    public Button deleteButton;
    public Button postponeButton;
    public Label deadlineText;
    public Label postponesText;
    public Button modifyButton;
    private int noteId;

    public void setData(StickyNote stickyNote) {
        noteId = stickyNote.getId();
        noteLabel.setText(stickyNote.getNoteDescription());
        deadlineLabel.setText(stickyNote.getDeadline().toString());
        postponesLabel.setText(Integer.toString(stickyNote.getPostpones()));
        containerVbox.setStyle("-fx-background-color: "+stickyNote.getBackground()+";");

        containerVbox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        setFontColors(stickyNote);
        disablePostponeButton(stickyNote.getPostpones());
        disableModifyButton(stickyNote);
    }

    private void disableModifyButton(StickyNote stickyNote) {
        if (stickyNote.getDeadline().isBefore(LocalDate.now())) {
            modifyButton.setDisable(true);
            deadlineLabel.setStyle("-fx-text-fill: " + getOptimalFontColor(stickyNote.getBackground())+";"+
                    "-fx-font-weight: bold;" +
                    "-fx-underline: true;");
        }
        else {
            modifyButton.setDisable(false);
        }
    }

    private StickyNote getData() {
        return new StickyNote(
                noteId,
                noteLabel.getText(),
                Integer.parseInt(postponesLabel.getText()),
                LocalDate.parse(deadlineLabel.getText()),
                "#"+containerVbox.getBackground().getFills().get(0).getFill().toString().substring(2)
        );
    }

    public void postpone(ActionEvent actionEvent) {
        if (Integer.parseInt(postponesLabel.getText()) > 0) {
            StickyNote stickyNote = getData();
            stickyNote.setPostpones(stickyNote.getPostpones()-1);
            stickyNote.setDeadline(LocalDate.parse(deadlineLabel.getText()).plusWeeks(1));

            if (new NoteDaoImpl().modify(stickyNote)) {
                postponesLabel.setText(String.valueOf(stickyNote.getPostpones()));
                deadlineLabel.setText(stickyNote.getDeadline().toString());
                disablePostponeButton(stickyNote.getPostpones());
                disableModifyButton(stickyNote);
            }
        }
    }

    private String getOptimalFontColor(String backgroundColor) {
        return "#" + Color.web(backgroundColor).invert().toString().substring(2);
    }

    private void setFontColors(StickyNote stickyNote) {
        noteLabel.setStyle("-fx-text-fill: " + getOptimalFontColor(stickyNote.getBackground())+";");
        deadlineText.setStyle("-fx-text-fill: " + getOptimalFontColor(stickyNote.getBackground())+";");
        deadlineLabel.setStyle("-fx-text-fill: " + getOptimalFontColor(stickyNote.getBackground())+";");
        postponesText.setStyle("-fx-text-fill: " + getOptimalFontColor(stickyNote.getBackground())+";");
        postponesLabel.setStyle("-fx-text-fill: " + getOptimalFontColor(stickyNote.getBackground())+";");
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

            Stage stage = createStage(actionEvent, root, modifyController);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Stage createStage(ActionEvent actionEvent, Parent root, ModifyNoteController modifyController) {
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Modify Note");

        Stage parentStage = getParentStage(actionEvent);

        stage.setOnCloseRequest(event -> {
            parentStage.show();
        });

        stage.setOnShowing(event -> {
            parentStage.hide();
        });

        stage.addEventFilter(NoteEvent.NOTE_MODIFY, event -> {
            this.setData(modifyController.getData());
        });

        return stage;
    }

    private Stage getParentStage(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        return (Stage) source.getScene().getWindow();
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
