package javafinal;

public class Barco extends Vehiculo {
    
    private double eslora;
    private boolean tieneCamarote;

    // Sobrecarga 1: Completo
    public Barco(String patente, String marca, TipoCombustible combustible, double eslora, boolean tieneCamarote) {
        super(patente, marca, combustible);
        this.eslora = eslora;
        this.tieneCamarote = tieneCamarote;
    }

    // Sobrecarga 2: Un parámetro menos
    public Barco(String patente, String marca, TipoCombustible combustible, double eslora) {
        this(patente, marca, combustible, eslora, false);
    }

    // Sobrecarga 3: Vacío
    public Barco() {
        super();
        this.eslora = 5.5;
        this.tieneCamarote = false;
    }

    // Getters y Setters
    public double getEslora() { return eslora; }
    public void setEslora(double eslora) { this.eslora = eslora; }

    public boolean isTieneCamarote() { return tieneCamarote; }
    public void setTieneCamarote(boolean tieneCamarote) { this.tieneCamarote = tieneCamarote; }

    @Override
    public String mostrarDetalles() {
        return "BARCO -> " + toString() + " | Eslora: " + eslora + "m | Camarote: " + (tieneCamarote ? "Sí" : "No");
    }

    @Override
    public double calcularImpuesto() {
        return eslora * 2000.0 + (tieneCamarote ? 5000.0 : 0.0);
    }
}