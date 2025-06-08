module org.example.pw_projekt_gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires com.fasterxml.jackson.databind;
    opens org.example.pw_projekt_gui to javafx.fxml;
    exports org.example.pw_projekt_gui;
}