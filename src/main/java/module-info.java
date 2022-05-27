module com.example.cuphead {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;

    
    opens cuphead.controllers to javafx.fxml, com.google.gson;
    opens cuphead.models to javafx.fxml, com.google.gson;
    opens cuphead.views to javafx.fxml;
    opens cuphead.views.controllers to javafx.fxml;
    opens cuphead.views.transitions to javafx.fxml;
    opens cuphead.views.utilities to javafx.fxml;
    exports cuphead.views;
}