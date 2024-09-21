
package Modelo;


/**
 *
 * @author Victor
 */
public class Ciclo {
    private Integer IdCiclo;
    private Integer NumeroCiclo;
    
    private NombreCiclo NombreCiclo;
    private Integer NombreCicloID;

    private UnidadCurricular unidadCurricularCiclo;
    private Integer UnidadCurricularID;

    public Ciclo() {
        
    }
    
    public Integer getIdCiclo() {
        return IdCiclo;
    }

    public void setIdCiclo(Integer IdCiclo) {
        this.IdCiclo = IdCiclo;
    }

    public NombreCiclo getNombreCiclo() {
        return NombreCiclo;
    }

    public void setNombreCiclo(NombreCiclo NombreCiclo) {
        this.NombreCiclo = NombreCiclo;
    }

    public Integer getNumeroCiclo() {
        return NumeroCiclo;
    }

    public void setNumeroCiclo(Integer NumeroCiclo) {
        this.NumeroCiclo = NumeroCiclo;
    }

    public UnidadCurricular getUnidadCurricularCiclo() {
        return unidadCurricularCiclo;
    }

    public void setUnidadCurricularCiclo(UnidadCurricular unidadCurricularCiclo) {
        this.unidadCurricularCiclo = unidadCurricularCiclo;
    }

    public Integer getUnidadCurricularID() {
        return UnidadCurricularID;
    }

    public void setUnidadCurricularID(Integer UnidadCurricularID) {
        this.UnidadCurricularID = UnidadCurricularID;
    }

    public Integer getNombreCicloID() {
        return NombreCicloID;
    }

    public void setNombreCicloID(Integer NombreCicloID) {
        this.NombreCicloID = NombreCicloID;
    }

    @Override
    public String toString() {
        return NombreCiclo+"\n";
    }
    
}
