package hu.sticky.dao;

import hu.sticky.config.ConfigHelper;
import hu.sticky.model.StickyNote;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NoteDaoImpl implements NoteDao{
    @Override
    public List<StickyNote> getNotes() {
        List<StickyNote> result = new ArrayList<>();

        try(Connection c = DriverManager.getConnection(ConfigHelper.getProperty("db.url"));
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM StickyNotes")
        ){

            while(rs.next()){
                result.add(new StickyNote(
                        rs.getInt("id"),
                        rs.getString("description"),
                        rs.getInt("postpones"),
                        LocalDate.parse(rs.getString("deadline")), //converting string to local date
                        rs.getString("background")
                ));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return result;
    }

    @Override
    public StickyNote add(StickyNote stickyNote) {
        String path = ConfigHelper.getProperty("db.url");
        String addSqlCommand = "INSERT INTO StickyNotes (DESCRIPTION, POSTPONES, DEADLINE, BACKGROUND) VALUES (?,?,?,?)";

        try(Connection c = DriverManager.getConnection(path);
            PreparedStatement stmt = c.prepareStatement(addSqlCommand, Statement.RETURN_GENERATED_KEYS);
        ){
            stmt.setString(1, stickyNote.getNoteDescription());
            stmt.setInt(2, stickyNote.getPostpones());
            stmt.setString(3, stickyNote.getDeadline().toString());
            stmt.setString(4, stickyNote.getBackground());

            int affectedRows = stmt.executeUpdate();
            if(affectedRows == 0){
                return null;
            }

            ResultSet genKeys = stmt.getGeneratedKeys();
            if(genKeys.next()) stickyNote.setId(genKeys.getInt(1));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

        return stickyNote;
    }

    @Override
    public boolean delete(int id) {
        String path = ConfigHelper.getProperty("db.url");
        String deleteSqlCommand = "DELETE FROM StickyNotes WHERE ID = ?";

        try(Connection c = DriverManager.getConnection(path);
            PreparedStatement stmt = c.prepareStatement(deleteSqlCommand);
        ){
            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();
            if(affectedRows == 0){
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean modify(StickyNote stickyNote) {
        return false;
    }
}
