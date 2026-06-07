package javafinal;

public class Avion extends Vehiculo implements Mantenimiento{
    
    private int capacidadPasajeros;
    private int altitudMaxima;

    // Sobrecarga 1: Completo
    public Avion(String patente, String marca, TipoCombustible combustible, int capacidadPasajeros, int altitudMaxima) {
        super(patente, marca, combustible);
        this.capacidadPasajeros = capacidadPasajeros;
        this.altitudMaxima = altitudMaxima;
    }

    // Sobrecarga 2: Un parámetro menos
    public Avion(String patente, String marca, TipoCombustible combustible, int capacidadPasajeros) {
        this(patente, marca, combustible, capacidadPasajeros, 10000); // 10000 metros por defecto
    }

    // Sobrecarga 3: Vacío
    public Avion() {
        super();
        this.capacidadPasajeros = 2;
        this.altitudMaxima = 3000;
    }

    // Getters y Setters
    public int getCapacidadPasajeros() { return capacidadPasajeros; }
    public void setCapacidadPasajeros(int capacidadPasajeros) { this.capacidadPasajeros = capacidadPasajeros; }

    public int getAltitudMaxima() { return altitudMaxima; }
    public void setAltitudMaxima(int altitudMaxima) { this.altitudMaxima = altitudMaxima; }

    @Override
    public String mostrarDetalles() {
        return "AVIÓN -> " + toString() + " | Pasajeros: " + capacidadPasajeros + " | Altitud Máx: " + altitudMaxima + "m";
    }

    @Override
    public double calcularImpuesto() {
        return capacidadPasajeros * 5000.0 + (altitudMaxima / 10);
    }
    
    @Override
    public String realizarService() {
        return "SERVICE AVIÓN: Inspección de turbinas, test de tren de aterrizaje.";
    }
    
}