package hu.sticky.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import hu.sticky.App;
import hu.sticky.dao.NoteDaoImpl;
import hu.sticky.event.NoteEvent;
import hu.sticky.model.StickyNote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;

public class MainWindowController implements Initializable{

    public FlowPane flow;

    @FXML
    public void openNewNoteForm(ActionEvent actionEvent) throws IOException{
        App.setRoot("view/NewNote");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        flow.addEventFilter(NoteEvent.NOTE_DELETE, this::handleNoteEvent);

        try {
            List<StickyNote> notes = new NoteDaoImpl().getNotes();

            for (StickyNote note : notes) {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("common/noteItem.fxml"));
                VBox vbox = fxmlLoader.load();

                NoteItemController noteItemController = fxmlLoader.getController();
                noteItemController.setData(note);

                flow.getChildren().add(vbox);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleNoteEvent(NoteEvent event) {
        flow.getChildren().remove(event.getDeletableVbox());
    }
}
