
package Modelo;

/**
 *
 * @author Victor
 */
public class Facultad {
    private Integer IdFacultad;
    private String NombreFacultad;
    private String FechaCreacion;
    
    private Universidad universidadFacultad;
    private Integer UniversidadID;

    public Facultad() {

    }

    public Integer getIdFacultad() {
        return IdFacultad;
    }

    public void setIdFacultad(Integer IdFacultad) {
        this.IdFacultad = IdFacultad;
    }

    public String getNombreFacultad() {
        return NombreFacultad;
    }

    public void setNombreFacultad(String NombreFacultad) {
        this.NombreFacultad = NombreFacultad;
    }

    public String getFechaCreacion() {
        return FechaCreacion;
    }

    public void setFechaCreacion(String FechaCreacion) {
        this.FechaCreacion = FechaCreacion;
    }

    public Universidad getUniversidadFacultad() {
        return universidadFacultad;
    }

    public void setUniversidadFacultad(Universidad universidadFacultad) {
        this.universidadFacultad = universidadFacultad;
    }

    public Integer getUniversidadID() {
        return UniversidadID;
    }

    public void setUniversidadID(Integer UniversidadID) {
        this.UniversidadID = UniversidadID;
    }
        
    @Override
    public String toString() {
        return NombreFacultad +  "\n";
    }
    
}
