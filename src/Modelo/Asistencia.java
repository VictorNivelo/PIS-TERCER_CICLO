
package Modelo;

/**
 *
 * @author Victor
 */
public class Asistencia {
    private Integer IdAsistencia;
    private String FechaAsistencia;
    private String EstadoAsistencia;
    private String Observacion;

    private Horario horarioAsistencia;
    private Alumno alumnoAsistencia;
    private Tematica TematicaAsistencia;
    
    public Asistencia() {
        
    }

    public String getFechaAsistencia() {
        return FechaAsistencia;
    }

    public void setFechaAsistencia(String FechaAsistencia) {
        this.FechaAsistencia = FechaAsistencia;
    }

    public String getEstadoAsistencia() {
        return EstadoAsistencia;
    }

    public void setEstadoAsistencia(String EstadoAsistencia) {
        this.EstadoAsistencia = EstadoAsistencia;
    }

    public String getObservacion() {
        return Observacion;
    }

    public void setObservacion(String Observacion) {
        this.Observacion = Observacion;
    }

    public Horario getHorarioAsistencia() {
        return horarioAsistencia;
    }

    public void setHorarioAsistencia(Horario horarioAsistencia) {
        this.horarioAsistencia = horarioAsistencia;
    }

    public Alumno getAlumnoAsistencia() {
        return alumnoAsistencia;
    }

    public void setAlumnoAsistencia(Alumno alumnoAsistencia) {
        this.alumnoAsistencia = alumnoAsistencia;
    }

    public Tematica getTematicaAsistencia() {
        return TematicaAsistencia;
    }

    public void setTematicaAsistencia(Tematica TematicaAsistencia) {
        this.TematicaAsistencia = TematicaAsistencia;
    }

    public Integer getIdAsistencia() {
        return IdAsistencia;
    }

    public void setIdAsistencia(Integer IdAsistencia) {
        this.IdAsistencia = IdAsistencia;
    }
    
    @Override
    public String toString() {
        return "Estado: "+EstadoAsistencia+"Observacion:" + Observacion + "\n";
    }
                
}
