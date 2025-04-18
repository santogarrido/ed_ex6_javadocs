package examen;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Esta clase nos permite gestionar los diferentes carriles bicis de la bahía de Cádiz.
 * Permitiendonos añadir tramos, mirar el estado del carril y actualizarlo, obtener un informe detallado, 
 * mirar la longitud que tiene cada carril y obtener tramos de los diferentes carriles que nos interesen.
 * 
 * @author CádizTech
 * @version 2.4.0
 * 
 */

public class CarrilBiciManager {

    private final Map<String, Double> tramos; // nombre del tramo -> longitud en km
    private final Map<String, String> estadoTramos; // nombre del tramo -> estado

    /**
     * Constructor público que nos inicializa los mapas
     * tramos y estadoTramos en HashMap     
     */
    public CarrilBiciManager() {
        this.tramos = new HashMap<>();
        this.estadoTramos = new HashMap<>();
    }

    
    /**
     * Este método recibe dos parámetros
     * 
     * @param nombre -> Nombre del tramo del carril bici
     * @param longitud -> longitud del tramo nuevo
     * 
     * Comprueba si los parámetros recibidos concuerdan con las restricciones necesarias
     * de ser así nos añade ese nuevo tramo. Si no concuerda nos devolverá un mensaje informando del error
     */
    public void añadirTramo(String nombre, double longitud) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del tramo no puede estar vacío");
        }
        if (longitud <= 0) {
            throw new IllegalArgumentException("La longitud debe ser mayor que cero");
        }
        tramos.put(nombre, longitud);
        estadoTramos.put(nombre, "En servicio");
    }

    /**
     * Este método nos permite actualizar el estado de un tramo
     * 
     * @param nombre -> nombre del tramo a cambiar el estado
     * @param nuevoEstado -> nuevo estado del tramo
     * 
     * Primero nos comprueba si ese tramo existe, de ser así nos cambia el estado.
     * Si no existe el tramo, nos mostrará un mensaje indicandolo
     */
    public void actualizarEstado(String nombre, String nuevoEstado) {
        if (!tramos.containsKey(nombre)) {
            throw new NoSuchElementException("El tramo indicado no existe: " + nombre);
        }
        estadoTramos.put(nombre, nuevoEstado);
    }
    
    
    /**
     * Este método nos permite actualizar el estado de un tramo
     * 
     * @param nombre -> Recibe el nombre del tramo
     * @param estado -> Recibe el nuevo estado
     * @deprecated
     * 
     * Este método llama al método actualizarEstado()
     */
    public void cambiarEstado(String nombre, String estado) {
        actualizarEstado(nombre, estado);
    }

    /**
     * Este método nos permite consultar el estado de un tramo
     * 
     * @param nombre -> nombre del tramo
     * @return estado del tramo
     * 
     * Este método recibe el nombre de un tramo y si existe ese tramo nos devuelve el estado del mismo
     * Si no existe el tramo nos muestra un mensaje informando de ello
     */
    public String consultarEstado(String nombre) {
        if (!estadoTramos.containsKey(nombre)) {
            throw new NoSuchElementException("El tramo indicado no existe");
        }
        return estadoTramos.get(nombre);
    }

    /**
     * Este método nos devuelve la longitud total del carril bici
     * 
     * @return la suma de todos los tramos de un carril bici
     */
    public double longitudTotal() {
        return tramos.values().stream().mapToDouble(Double::doubleValue).sum();
    }

    /**
     * Este método nos devuelve todos los tramos.
     * 
     * @return todos los tramos
     */
    public Map<String, Double> obtenerTramos() {
        return Collections.unmodifiableMap(tramos);
    }

    /**
     * Este método genera un informe de los carriles bicis
     * 
     * @return informe del estado de los carriles bicis
     */
    public String generarInforme() {
        StringBuilder sb = new StringBuilder("INFORME DE CARRILES BICI - Bahía de Cádiz\n");
        sb.append("===========================================\n");
        for (String nombre : tramos.keySet()) {
            sb.append("- ").append(nombre).append(" (")
              .append(tramos.get(nombre)).append(" km): ")
              .append(estadoTramos.get(nombre)).append("\n");
        }
        sb.append("Longitud total: ").append(longitudTotal()).append(" km\n");
        return sb.toString();
    }
}
