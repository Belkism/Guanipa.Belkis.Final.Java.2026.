/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafinal;

/**
 *
 * @author belki
 */
public class Auto extends Vehiculo implements Mantenimiento{
    
    private int cantidadPuertas;
    private boolean tieneAireAcondicionado;
    
    
    public Auto(String patente, String marca, TipoCombustible combustible, int cantidadPuertas, boolean tieneAireAcondicionado) {
        super(patente, marca, combustible); // Llama al constructor de Vehiculo
        this.cantidadPuertas = cantidadPuertas;
        this.tieneAireAcondicionado = tieneAireAcondicionado;
    }
    // Sobrecarga 2: Un parámetro menos que el anterior
    public Auto(String patente, String marca, TipoCombustible combustible, int cantidadPuertas) {
        this(patente, marca, combustible, cantidadPuertas, true); // Asigna aire acondicionado por defecto
    }
    
    //Sobrecarga 3: A elección (Constructor vacío)
    public Auto() {
        super();
        this.cantidadPuertas = 4;
        this.tieneAireAcondicionado = false;
    
    
    
}

    public int getCantidadPuertas() {
        return cantidadPuertas;
    }

    public void setCantidadPuertas(int cantidadPuertas) {
        this.cantidadPuertas = cantidadPuertas;
    }

    public boolean isTieneAireAcondicionado() {
        return tieneAireAcondicionado;
    }

    public void setTieneAireAcondicionado(boolean tieneAireAcondicionado) {
        this.tieneAireAcondicionado = tieneAireAcondicionado;
        
    }
    @Override
    public String mostrarDetalles() {
        return "AUTO -> " + toString() + " | Puertas: " + cantidadPuertas + " | AC: " + (tieneAireAcondicionado ? "Sí" : "No");
    }
    @Override
    public double calcularImpuesto() {
        // Lógica de ejemplo para el examen
        return tieneAireAcondicionado ? 15000.0 : 10000.0;
    }
    @Override
    public String realizarService() {
        return "SERVICE AUTO: Cambio de aceite, filtros y revisión de pastillas de freno.";
    
    }
}