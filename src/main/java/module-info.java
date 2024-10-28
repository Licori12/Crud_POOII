module org.example.projcrudpoo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.projcrudpoo.main to javafx.fxml;
    opens org.example.projcrudpoo.controller to javafx.fxml;

    exports org.example.projcrudpoo.main;
    exports org.example.projcrudpoo.controller;
}
