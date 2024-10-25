module org.example.crud {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.crud.main to javafx.fxml; // Certifique-se de que isso está correto
    opens org.example.crud.controller to javafx.fxml; // Adicione isso se necessário
    opens org.example.crud.view to javafx.fxml; // Adicione isso se necessário
    exports org.example.crud.main;
}
