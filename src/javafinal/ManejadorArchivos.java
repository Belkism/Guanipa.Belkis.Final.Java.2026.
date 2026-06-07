package javafinal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManejadorArchivos {

    // El nombre del archivo de texto donde guardaremos los datos en el disco
    private static final String NOMBRE_ARCHIVO = "vehiculos.txt";

    // --- MÉTODO PARA GUARDAR LOS DATOS (Escritura) ---
    public static void guardarVehiculos(List<Vehiculo> lista) {
        // El bloque try-with-resources abre el archivo y lo cierra solo al terminar
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO))) {
            
            for (Vehiculo v : lista) {
                // Guardamos los datos separados por un punto y coma (;) para poder leerlos fácil después
                // Ejemplo: AUTO;AAA111;Ford;NAFTA
                String tipo = (v instanceof Auto) ? "AUTO" : (v instanceof Barco) ? "BARCO" : "AVION";
                
                bw.write(tipo + ";" + v.getPatente() + ";" + v.getMarca() + ";" + v.getCombustible());
                bw.newLine(); // Salto de línea para el próximo vehículo
            }
            System.out.println("Datos persistidos en " + NOMBRE_ARCHIVO + " con éxito.");
            
        } catch (IOException e) {
            // Requisito del PDF: Manejo explícito de excepciones de Entrada/Salida
            System.err.println("Error crítico al intentar guardar el archivo: " + e.getMessage());
        }
    }

    // --- MÉTODO PARA CARGAR LOS DATOS (Lectura) ---
    public static List<Vehiculo> cargarVehiculos() {
        List<Vehiculo> listaCargada = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String linea;
            
            // Leemos el archivo renglón por renglón hasta que no quede nada (null)
            while ((linea = br.readLine()) != null) {
                // Rompemos la línea usando el punto y coma como separador
                String[] partes = linea.split(";");
                
                if (partes.length >= 4) {
                    String tipo = partes[0];
                    String patente = partes[1];
                    String marca = partes[2];
                    TipoCombustible combustible = TipoCombustible.valueOf(partes[3]);
                    
                    // Dependiendo del tipo guardado, recreamos el objeto hijo correspondiente
                    if (tipo.equals("AUTO")) {
                        // Creamos un Auto usando el constructor (le pasamos datos por defecto para sus atributos propios por ahora)
                        listaCargada.add(new Auto(patente, marca, combustible, 4, true));
                    } else if (tipo.equals("BARCO")) {
                        listaCargada.add(new Barco(patente, marca, combustible, 12.5, true));
                    } else if (tipo.equals("AVION")) {
                        listaCargada.add(new Avion(patente, marca, combustible, 150, 2));
                    }
                }
            }
            System.out.println("Datos cargados desde el archivo correctamente.");
            
        } catch (IOException e) {
            // Si el archivo no existe todavía (primera vez que se abre la app), no bloqueamos el programa
            System.out.println("Aviso: No se encontró un archivo previo. Se iniciará con la lista vacía.");
        }
        
        return listaCargada;
    }
    
    // REQUISITO PUNTO 6: Guardar la lista completa en formato JSON
    public static void guardarEnFormatosExtras(List<Vehiculo> lista) throws Exception {
        // 1. Guardar en formato JSON (.json) de forma nativa
        try (BufferedWriter bwJson = new BufferedWriter(new FileWriter("vehiculos.json"))) {
            bwJson.write("[\n");
            for (int i = 0; i < lista.size(); i++) {
                Vehiculo v = lista.get(i);
                bwJson.write("  {\n");
                bwJson.write("    \"patente\": \"" + v.getPatente() + "\",\n");
                bwJson.write("    \"marca\": \"" + v.getMarca() + "\",\n");
                bwJson.write("    \"combustible\": \"" + v.getCombustible() + "\"\n");
                
                if (i < lista.size() - 1) {
                    bwJson.write("  },\n"); // Lleva coma si no es el último
                } else {
                    bwJson.write("  }\n");  // El último no lleva coma
                }
            }
            bwJson.write("]");
            System.out.println("Archivo JSON exportado con éxito.");
        }
    }

    // REQUISITO PUNTO 6: Exportar un listado FILTRADO a un archivo de texto (.txt)
    public static void exportarListadoFiltrado(List<Vehiculo> listaFiltrada) throws Exception {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("reporte_filtrado.txt"))) {
            bw.write("=== REPORTE DE VEHICULOS FILTRADOS ===\n");
            bw.write("Fecha de generacion: 2026\n");
            bw.write("--------------------------------------\n");
            if (listaFiltrada.isEmpty()) {
                bw.write("No se encontraron vehiculos que coincidan con el filtro.\n");
            } else {
                for (Vehiculo v : listaFiltrada) {
                    bw.write("- Tipo: " + v.getClass().getSimpleName() + " | Patente: " + v.getPatente() + " | Marca: " + v.getMarca() + " | Combustible: " + v.getCombustible() + "\n");
                }
            }
            System.out.println("Reporte filtrado exportado a reporte_filtrado.txt");
        }
    }
}
