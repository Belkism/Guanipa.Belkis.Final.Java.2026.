package javafinal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

// Implementa tu interfaz DoCRUD e Iterable
public class GestionVehiculos implements DoCRUD<Vehiculo>, Iterable<Vehiculo> {

    private List<Vehiculo> listaVehiculos;

    public GestionVehiculos() {
        this.listaVehiculos = new ArrayList<>();
    }

    // --- IMPLEMENTACIÓN DEL CRUD ---
    @Override
    public void crear(Vehiculo vehiculo) throws Exception {
        for (Vehiculo v : listaVehiculos) {
            if (v.getPatente().equalsIgnoreCase(vehiculo.getPatente())) {
                throw new PatenteDuplicadaException("Error: Ya existe un vehículo con la patente " + vehiculo.getPatente());
            }
        }
        listaVehiculos.add(vehiculo);
        System.out.println("Vehículo agregado con éxito.");
    }

    @Override
    public List<Vehiculo> leerTodo() {
        return this.listaVehiculos;
    }

    @Override
    public void actualizar(String identificador, Vehiculo entidadActualizada) throws Exception {
        for (int i = 0; i < listaVehiculos.size(); i++) {
            if (listaVehiculos.get(i).getPatente().equalsIgnoreCase(identificador)) {
                listaVehiculos.set(i, entidadActualizada);
                System.out.println("Vehículo actualizado con éxito.");
                return;
            }
        }
        throw new Exception("Error: No se encontró ningún vehículo con la patente " + identificador);
    }

    @Override
    public void eliminar(String identificador) throws Exception {
        boolean removido = listaVehiculos.removeIf(v -> v.getPatente().equalsIgnoreCase(identificador));
        if (!removido) {
            throw new VehiculoNoEncontradoException("Error: No se encontró ningún vehículo con la patente " + identificador);
        }
        System.out.println("Vehículo eliminado con éxito.");
    }

    // --- ITERATOR PERSONALIZADO ---
    @Override
    public Iterator<Vehiculo> iterator() {
        return new MiIteratorPersonalizado();
    }

    private class MiIteratorPersonalizado implements Iterator<Vehiculo> {

        private int indiceActual = 0;

        @Override
        public boolean hasNext() {
            return indiceActual < listaVehiculos.size();
        }

        @Override
        public Vehiculo next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No hay más elementos en la lista.");
            }
            return listaVehiculos.get(indiceActual++);
        }
    }

    // Criterio 1 con Comparator: Ordenar por Marca alfabéticamente (Requisito punto 4)
    public void ordenarPorMarca() {
        this.listaVehiculos.sort((v1, v2) -> v1.getMarca().compareToIgnoreCase(v2.getMarca()));
        System.out.println("Lista ordenada por marca.");
    }

// Criterio 2 con Comparator: Ordenar por Impuesto de menor a mayor (Requisito punto 4)
    public void ordenarPorImpuesto() {
        this.listaVehiculos.sort((v1, v2) -> Double.compare(v1.calcularImpuesto(), v2.calcularImpuesto()));
        System.out.println("Lista ordenada por valor de impuesto.");
    }
    // Uso de Upper Bounded Wildcard (? extends) para filtrar y listar (Requisito punto 4 y 8)

    public List<Vehiculo> filtrarPorCombustible(List<? extends Vehiculo> listaAFiltrar, TipoCombustible tipo) {
        List<Vehiculo> resultado = new ArrayList<>();
        for (Vehiculo v : listaAFiltrar) {
            if (v.getCombustible() == tipo) {
                resultado.add(v);
            }
        }
        return resultado;
    }

// Uso de Lower Bounded Wildcard (? super Auto) para copiar datos de forma segura (Requisito punto 8)
    public void copiarAutosAListaDestino(List<? super Auto> listaDestino) {
        for (Vehiculo v : this.listaVehiculos) {
            if (v instanceof Auto) {
                listaDestino.add((Auto) v); // Agregamos con casteo seguro
            }
        }

    }

    // Modificación masiva usando la interfaz funcional Consumer (Requisito punto 5)
    public void modificarVehiculos(Consumer<Vehiculo> modificacion) {
        for (Vehiculo v : this.listaVehiculos) {
            modificacion.accept(v); // Aplica la acción lambda que le pasemos
        }
        System.out.println("Modificación funcional aplicada con éxito.");
    }
}
