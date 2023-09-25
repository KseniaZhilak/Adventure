module com.example.adventure {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.adventure to javafx.fxml;
    exports com.example.adventure;
}