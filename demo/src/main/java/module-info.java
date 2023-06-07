module JavaFXApplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires java.sql;

    opens batalha_naval to javafx.fxml;
    opens batalha_naval.controller to javafx.fxml;
    exports batalha_naval;

}