module com.example.midterm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    exports com.example.midterm.Free;
    opens com.example.midterm.Free to javafx.fxml;
}