package javafinal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFinal extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        // Carga la interfaz visual desde el archivo FXML
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Sistema de Gestión de Vehículos - UTN FRA");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // Lanza la aplicación de JavaFX
        launch(args);
    }
}