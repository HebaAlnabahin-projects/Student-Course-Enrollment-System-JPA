module com.example.demo4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens App to javafx.graphics, javafx.fxml;

    opens Controllers to javafx.fxml;
    opens Models to javafx.base;

    exports com.example.demo4;
    exports App;
}