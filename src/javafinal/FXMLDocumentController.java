package javafinal;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox; // Asegúrate de tener este import
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLDocumentController implements Initializable {
    
    private GestionVehiculos gestion;
    
    @FXML private TextField txtPatente;
    @FXML private TextField txtMarca;
    @FXML private ComboBox<String> cmbTipo; // Vinculado a tu nuevo FXML
    @FXML private TextArea txtAreaLista;
    @FXML private Label lblMensajes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gestion = new GestionVehiculos();
        
        // Cargar las opciones en el ComboBox
        cmbTipo.getItems().addAll("AUTO", "BARCO", "AVION");
        cmbTipo.setValue("AUTO"); // Opción inicial por defecto

        try {
            List<Vehiculo> archivados = ManejadorArchivos.cargarVehiculos();
            if (archivados != null) {
                gestion.leerTodo().addAll(archivados);
            }
            lblMensajes.setText("Estado: Datos recuperados del disco.");
            mostrarListaEnPantalla(gestion.leerTodo());
        } catch (Exception e) {
            lblMensajes.setText("Estado: Iniciando lista vacía.");
        }
    }    

    @FXML
    private void handleAgregarAction(ActionEvent event) {
        try {
            String patente = txtPatente.getText().trim();
            String marca = txtMarca.getText().trim();
            String tipoSeleccionado = cmbTipo.getValue(); // Leemos qué tipo seleccionó el usuario

            if (patente.isEmpty() || marca.isEmpty()) {
                lblMensajes.setText("Estado: Error: Completa los campos.");
                return;
            }

            Vehiculo nuevoVehiculo;

            // Instanciamos el objeto polimórfico correcto usando el ComboBox
            switch (tipoSeleccionado) {
                case "AVION":
                    nuevoVehiculo = new Avion(patente, marca, TipoCombustible.GASOLINAAVION, 180, 4);
                    break;
                case "BARCO":
                    nuevoVehiculo = new Barco(patente, marca, TipoCombustible.DIESEL, 15.5, true);
                    break;
                default: // AUTO
                    nuevoVehiculo = new Auto(patente, marca, TipoCombustible.GASOLINA, 4, true);
                    break;
            }

            gestion.crear(nuevoVehiculo); // Lanza PatenteDuplicadaException si ya existe
            
            // Persistencia de datos requerida por el PDF
            ManejadorArchivos.guardarVehiculos(gestion.leerTodo());
            ManejadorArchivos.guardarEnFormatosExtras(gestion.leerTodo()); // Guarda JSON
            ManejadorArchivos.guardarVehiculosBinario(gestion.leerTodo());
            
            lblMensajes.setText("Estado: " + nuevoVehiculo.getClass().getSimpleName() + " " + patente + " creado.");
            mostrarListaEnPantalla(gestion.leerTodo());
            limpiarCampos();
            
        } catch (PatenteDuplicadaException e) {
            lblMensajes.setText("Estado: " + e.getMessage()); // Captura tu excepción propia
        } catch (Exception e) {
            lblMensajes.setText("Estado: Error inesperado: " + e.getMessage());
        }
    }

    @FXML
    private void handleActualizarAction(ActionEvent event) {
        try {
            String patente = txtPatente.getText().trim();
            String nuevaMarca = txtMarca.getText().trim();
            String tipoSeleccionado = cmbTipo.getValue();

            if (patente.isEmpty() || nuevaMarca.isEmpty()) {
                lblMensajes.setText("Estado: Error: Ingresa Patente y nueva Marca.");
                return;
            }

            Vehiculo vehiculoActualizado;
            
            switch (tipoSeleccionado) {
                case "AVION":
                    vehiculoActualizado = new Avion(patente, nuevaMarca, TipoCombustible.GASOLINAAVION, 180, 4);
                    break;
                case "BARCO":
                    vehiculoActualizado = new Barco(patente, nuevaMarca, TipoCombustible.DIESEL, 15.5, true);
                    break;
                default:
                    vehiculoActualizado = new Auto(patente, nuevaMarca, TipoCombustible.GASOLINA, 4, true);
                    break;
            }
            
            gestion.actualizar(patente, vehiculoActualizado);
            ManejadorArchivos.guardarVehiculos(gestion.leerTodo());
            
            lblMensajes.setText("Estado: " + vehiculoActualizado.getClass().getSimpleName() + " " + patente + " actualizado.");
            mostrarListaEnPantalla(gestion.leerTodo());
            limpiarCampos();
        } catch (Exception e) {
            lblMensajes.setText("Estado: " + e.getMessage());
        }
    }
//Manejadores del proyecto
    @FXML
    private void handleEliminarAction(ActionEvent event) {
        try {
            String patente = txtPatente.getText().trim();
            if (patente.isEmpty()) {
                lblMensajes.setText("Estado: Error: Ingresa una patente.");
                return;
            }
            gestion.eliminar(patente); // Lanza VehiculoNoEncontradoException si no existe
            ManejadorArchivos.guardarVehiculos(gestion.leerTodo());
            lblMensajes.setText("Estado: Vehículo " + patente + " eliminado.");
            mostrarListaEnPantalla(gestion.leerTodo());
            limpiarCampos();
        } catch (VehiculoNoEncontradoException e) {
            lblMensajes.setText("Estado: " + e.getMessage()); // Captura tu otra excepción propia
        } catch (Exception e) {
            lblMensajes.setText("Estado: " + e.getMessage());
        }
    }

    @FXML 
    private void handleListarAction(ActionEvent event) {
        mostrarListaEnPantalla(gestion.leerTodo());
        lblMensajes.setText("Estado: Mostrando lista completa.");
    }

    @FXML 
    private void handleOrdenarAction(ActionEvent event) {
        gestion.ordenarPorMarca(); // Usa tu Comparator por Lambda
        mostrarListaEnPantalla(gestion.leerTodo());
        lblMensajes.setText("Estado: Ordenado alfabeticamente por marca.");
    }

    @FXML 
    private void handleFiltrarAction(ActionEvent event) {
        try {
            String tipoSeleccionado = cmbTipo.getValue();
            TipoCombustible combustibleDestino;

            // Asignamos dinámicamente el combustible según la selección del ComboBox
            if (tipoSeleccionado.equals("AVION")) {
                combustibleDestino = TipoCombustible.GASOLINAAVION;
            } else if (tipoSeleccionado.equals("BARCO")) {
                combustibleDestino = TipoCombustible.DIESEL;
            } else {
                combustibleDestino = TipoCombustible.GASOLINA;
            }

            // Invoca tu método con Upper Bounded Wildcard (? extends) de GestionVehiculos
            List<Vehiculo> filtrados = gestion.filtrarPorCombustible(gestion.leerTodo(), combustibleDestino);
            
            mostrarListaEnPantalla(filtrados);
            ManejadorArchivos.exportarListadoFiltrado(filtrados); // Exporta el reporte legible .txt requerido
            
            lblMensajes.setText("Estado: Filtrado por " + combustibleDestino + " y exportado reporte_filtrado.txt");
        } catch (Exception e) {
            lblMensajes.setText("Estado: Error al exportar filtro: " + e.getMessage());
        }
    }

    private void mostrarListaEnPantalla(List<Vehiculo> lista) {
        txtAreaLista.clear();
        if (lista.isEmpty()) {
            txtAreaLista.setText("No hay vehículos.");
            return;
        }
        for (Vehiculo v : lista) {
            txtAreaLista.appendText("• [" + v.getClass().getSimpleName() + "] Patente: " + v.getPatente() + " | Marca: " + v.getMarca() + " | " + v.getCombustible() + "\n");
        }
    }
    
    private void limpiarCampos() {
        txtPatente.clear();
        txtMarca.clear();
    }
}