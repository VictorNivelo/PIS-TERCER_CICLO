
package Modelo;

/**
 *
 * @author Victor
 */
public class Carrera {
    private Integer IdCarrera;
    private String NombreCarrera;
    private Integer Duracion;
    private Integer NumeroCiclos;
    
    private Facultad facutadCarrera;
    private Integer FacultadID;
    
    public Carrera() {
        
    }

    public Integer getIdCarrera() {
        return IdCarrera;
    }

    public void setIdCarrera(Integer IdCarrera) {
        this.IdCarrera = IdCarrera;
    }

    public String getNombreCarrera() {
        return NombreCarrera;
    }

    public void setNombreCarrera(String NombreCarrera) {
        this.NombreCarrera = NombreCarrera;
    }

    public Integer getNumeroCiclos() {
        return NumeroCiclos;
    }

    public void setNumeroCiclos(Integer NumeroCiclos) {
        this.NumeroCiclos = NumeroCiclos;
    }

    public Integer getDuracion() {
        return Duracion;
    }

    public void setDuracion(Integer Duracion) {
        this.Duracion = Duracion;
    }

    public Facultad getFacutadCarrera() {
        return facutadCarrera;
    }

    public void setFacutadCarrera(Facultad facutadCarrera) {
        this.facutadCarrera = facutadCarrera;
    }

    public Integer getFacultadID() {
        return FacultadID;
    }

    public void setFacultadID(Integer FacultadID) {
        this.FacultadID = FacultadID;
    }

    @Override
    public String toString() {
        return NombreCarrera + "\n";
    }
    
}
