module org.example.crud {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.crud to javafx.fxml;
    exports org.example.crud.main;
}