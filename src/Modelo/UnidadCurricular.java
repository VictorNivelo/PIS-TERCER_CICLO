
package Modelo;

/**
 *
 * @author Victor
 */
public class UnidadCurricular {
    private Integer IdUnidadCurricular;
    private String CodigoUnidadCurricular;
    private String NombreUnidadCurricular;
    private String DescripcionUnidadCurricular;
    
    private MallaC mallaCurricularUnidadCurricular;
    private Integer MallaCurricularID;
    

    public Integer getIdUnidadCurricular() {
        return IdUnidadCurricular;
    }

    public void setIdUnidadCurricular(Integer IdUnidadCurricular) {
        this.IdUnidadCurricular = IdUnidadCurricular;
    }

    public String getCodigoUnidadCurricular() {
        return CodigoUnidadCurricular;
    }

    public void setCodigoUnidadCurricular(String CodigoUnidadCurricular) {
        this.CodigoUnidadCurricular = CodigoUnidadCurricular;
    }

    public String getNombreUnidadCurricular() {
        return NombreUnidadCurricular;
    }

    public void setNombreUnidadCurricular(String NombreUnidadCurricular) {
        this.NombreUnidadCurricular = NombreUnidadCurricular;
    }

    public String getDescripcionUnidadCurricular() {
        return DescripcionUnidadCurricular;
    }

    public void setDescripcionUnidadCurricular(String DescripcionUnidadCurricular) {
        this.DescripcionUnidadCurricular = DescripcionUnidadCurricular;
    }

    public MallaC getMallaCurricularUnidadCurricular() {
        return mallaCurricularUnidadCurricular;
    }

    public void setMallaCurricularUnidadCurricular(MallaC mallaCurricularUnidadCurricular) {
        this.mallaCurricularUnidadCurricular = mallaCurricularUnidadCurricular;
    }

    public Integer getMallaCurricularID() {
        return MallaCurricularID;
    }

    public void setMallaCurricularID(Integer MallaCurricularID) {
        this.MallaCurricularID = MallaCurricularID;
    }

    @Override
    public String toString() {
        return CodigoUnidadCurricular + " " +NombreUnidadCurricular + "\n";
    }
    
}
