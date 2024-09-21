
package Modelo;

/**
 *
 * @author Victor
 */
public enum EstadoAsistencia {
    
    Presente("Presente"),
    Ausente("Ausente"),
    Justificado("Justificado");

    private final String descripcion;

    EstadoAsistencia(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
    
}
