
package Modelo;

/**
 *
 * @author Victor
 */
public class MallaC {
    private Integer IdMallaCurricular;
    private String NombreMallaCurricular;
    private Integer DuracionMallaCurricular;
    private String EstadoMallaCurricular;
    
    private Carrera carreraMallaCurricula;
    private Integer CarreraID;
    
    public MallaC() {
        
    }

    public Integer getIdMallaCurricular() {
        return IdMallaCurricular;
    }

    public void setIdMallaCurricular(Integer IdMallaCurricular) {
        this.IdMallaCurricular = IdMallaCurricular;
    }

    public String getNombreMallaCurricular() {
        return NombreMallaCurricular;
    }

    public void setNombreMallaCurricular(String NombreMallaCurricular) {
        this.NombreMallaCurricular = NombreMallaCurricular;
    }

    public Integer getDuracionMallaCurricular() {
        return DuracionMallaCurricular;
    }

    public void setDuracionMallaCurricular(Integer DuracionMallaCurricular) {
        this.DuracionMallaCurricular = DuracionMallaCurricular;
    }

    public String getEstadoMallaCurricular() {
        return EstadoMallaCurricular;
    }

    public void setEstadoMallaCurricular(String EstadoMallaCurricular) {
        this.EstadoMallaCurricular = EstadoMallaCurricular;
    }

    public Carrera getCarreraMallaCurricula() {
        return carreraMallaCurricula;
    }

    public void setCarreraMallaCurricula(Carrera carreraMallaCurricula) {
        this.carreraMallaCurricula = carreraMallaCurricula;
    }

    public Integer getCarreraID() {
        return CarreraID;
    }

    public void setCarreraID(Integer CarreraID) {
        this.CarreraID = CarreraID;
    }
    
    @Override
    public String toString() {
        return NombreMallaCurricular +  "\n";
    }
    
}
