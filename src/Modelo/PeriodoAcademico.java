
package Modelo;

/**
 *
 * @author Victor
 */
public class PeriodoAcademico {
    private Integer IdPeriodoAcademino;
    private String FechaInicio;
    private String FechaFin;
    private String EstadoPeriodoAcedemico;

    public PeriodoAcademico() {
        
    }

    public Integer getIdPeriodoAcademino() {
        return IdPeriodoAcademino;
    }

    public void setIdPeriodoAcademino(Integer IdPeriodoAcademino) {
        this.IdPeriodoAcademino = IdPeriodoAcademino;
    }

    public String getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(String FechaInicio) {
        this.FechaInicio = FechaInicio;
    }

    public String getFechaFin() {
        return FechaFin;
    }

    public void setFechaFin(String FechaFin) {
        this.FechaFin = FechaFin;
    }

    public String getEstadoPeriodoAcedemico() {
        return EstadoPeriodoAcedemico;
    }

    public void setEstadoPeriodoAcedemico(String EstadoPeriodoAcedemico) {
        this.EstadoPeriodoAcedemico = EstadoPeriodoAcedemico;
    }
    
    @Override
    public String toString() {
        return  FechaInicio + " - " + FechaFin + "\n";
    }
    
}
