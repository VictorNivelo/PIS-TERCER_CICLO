
package Modelo;

/**
 *
 * @author Victor
 */
public class Tematica {
    private Integer IdTematica;
    private String NombreTematica;
    private String FechaTematica;
    
    public Tematica() {
        
    }

    public Integer getIdTematica() {
        return IdTematica;
    }

    public void setIdTematica(Integer IdTematica) {
        this.IdTematica = IdTematica;
    }

    public String getNombreTematica() {
        return NombreTematica;
    }

    public void setNombreTematica(String NombreTematica) {
        this.NombreTematica = NombreTematica;
    }

    public String getFechaTematica() {
        return FechaTematica;
    }

    public void setFechaTematica(String FechaTematica) {
        this.FechaTematica = FechaTematica;
    }
    
    @Override
    public String toString() {
        return "idTematica=" + IdTematica + ", DiaTematica=" + FechaTematica + ", NombreTematica=" + NombreTematica + "\n";
    }
     
}
