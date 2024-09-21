
package Modelo;

/**
 *
 * @author Victor
 */
public class NombreCiclo {
    private Integer IdNombreCiclo;
    private String NombreCiclo;

    public NombreCiclo() {
        
    }

    public Integer getIdNombreCiclo() {
        return IdNombreCiclo;
    }

    public void setIdNombreCiclo(Integer IdNombreCiclo) {
        this.IdNombreCiclo = IdNombreCiclo;
    }

    public String getNombreCiclo() {
        return NombreCiclo;
    }

    public void setNombreCiclo(String NombreCiclo) {
        this.NombreCiclo = NombreCiclo;
    }
    
    @Override
    public String toString() {
        return NombreCiclo;
    }
    
}
