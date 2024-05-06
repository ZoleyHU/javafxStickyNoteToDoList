module hu.sticky {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens hu.sticky to javafx.fxml;
    exports hu.sticky;
    exports hu.sticky.controller;
    opens hu.sticky.controller to javafx.fxml;
    exports hu.sticky.model;
}
