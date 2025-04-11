package examen;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * 
 * @version 2.4.0
 * @author CádizTech
 * @see https://institucional.cadiz.es/area/Plan-de-Movilidad-Urbana-Sostenible/2021
 * 
 * Esta clase nos permite gestionar los diferentes carriles bicis de la bahía de Cádiz.
 * Permitiendonos añadir tramos, mirar el estado del carril y actualizarlo, obtener un informe detallado, 
 * mirar la longitud que tiene cada carril y obtener tramos de los diferentes carriles que nos interesen.
 */

public class CarrilBiciManager {

    private final Map<String, Double> tramos; // nombre del tramo -> longitud en km
    private final Map<String, String> estadoTramos; // nombre del tramo -> estado

    /**
     * Constructor público que nos inicializa los mapas
     * tramos y estadoTramos en HashMap<>()     
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
     * Este método es recomandeble que se deje de usar
     * @param nombre
     * @param estado
     * @deprecated
     */
    public void cambiarEstado(String nombre, String estado) {
        actualizarEstado(nombre, estado);
    }

    public String consultarEstado(String nombre) {
        if (!estadoTramos.containsKey(nombre)) {
            throw new NoSuchElementException("El tramo indicado no existe");
        }
        return estadoTramos.get(nombre);
    }

    public double longitudTotal() {
        return tramos.values().stream().mapToDouble(Double::doubleValue).sum();
    }

    public Map<String, Double> obtenerTramos() {
        return Collections.unmodifiableMap(tramos);
    }

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
