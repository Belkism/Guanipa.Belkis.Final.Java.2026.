/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package javafinal;

/**
 *
 * @author belki
 */

import java.util.List;
public interface DoCRUD<T> {
    void crear (T entidad)throws Exception;
    List<T>leerTodo();
    void actualizar (String identificador, T TentidadActualizada) throws Exception;
    void eliminar (String indentificador)throws Exception;
    
}
