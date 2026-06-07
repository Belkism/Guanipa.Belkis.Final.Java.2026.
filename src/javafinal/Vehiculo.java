
package javafinal;

/**
 *
 * @author belki
 */

enum TipoCombustible { 
GASOLINA, DIESEL, GASOLINAAVION}

public abstract class Vehiculo implements Comparable<Vehiculo>{
    
    private String patente;
    private String marca;
    public TipoCombustible combustible;

    
    // Sobrecarga 1: Recibe tantos parámetros como atributos (Completo)
    public Vehiculo(String patente, String marca, TipoCombustible combustible) {
        this.patente = patente;
        this.marca = marca;
        this.combustible = combustible;
    }
    // Sobrecarga 2: Recibe un parámetro menos que el anterior
    public Vehiculo (String patente, String marca){
        this.patente = patente;
        this.marca = marca;
        this.combustible =TipoCombustible.GASOLINA;
        
        
    }
    // Sobrecarga 3: A elección (Elegimos el constructor vacío)
    public Vehiculo (){
        this.patente = "N/N";
        this.marca = "Generico";
        this.combustible =TipoCombustible.GASOLINA;
    
}

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public TipoCombustible getCombustible() {
        return combustible;
    }

    public void setCombustible(TipoCombustible combustible) {
        this.combustible = combustible;
    }
    //Metodos compartidos
    public abstract String mostrarDetalles();
    public abstract double calcularImpuesto();
    
    //comparamos alfabeticamente un carro con otro con su patente
    @Override
    public int compareTo(Vehiculo otro) {
        return this.patente.compareToIgnoreCase(otro.patente);
    }
    //sobreescribe lo que queremos visualizar en consola 
    @Override
    public String toString() {
        return "Patente: " + patente + " | Marca: " + marca + " | Combustible: " + combustible;
    }
}
    
    
    
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
 
