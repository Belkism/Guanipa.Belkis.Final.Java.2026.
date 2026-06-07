module JavaFinal {
    requires javafx.controls;
    requires javafx.fxml;
    
    opens javafinal to javafx.fxml;

    exports javafinal;
}