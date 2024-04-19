module com.example.midterm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    exports com.example.midterm.Free;
    opens com.example.midterm.Free to javafx.fxml;
}