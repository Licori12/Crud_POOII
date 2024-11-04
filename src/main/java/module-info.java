module org.example.projcrudpoo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;

    opens org.example.projcrudpoo.main to javafx.fxml;
    opens org.example.projcrudpoo.controller to javafx.fxml;
    opens org.example.projcrudpoo.model to javafx.base; // Adicione esta linha

    exports org.example.projcrudpoo.main;
    exports org.example.projcrudpoo.controller;
}
