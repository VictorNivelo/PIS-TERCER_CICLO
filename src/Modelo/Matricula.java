
package Modelo;

/**
 *
 * @author Victor
 */
public class Matricula {
    private Integer IdMatricula;
    private String CodigoMatricula;
    private String FechaMatricula;
    private String EstadoMatricula;
    
    private Alumno alumnoMatricula;
    private Integer AlumnoID;
    
    private PeriodoAcademico periodoAcademicoMatricula;
    private Integer PeriodoID;
    
    public Matricula() {
        
    }

    public Integer getIdMatricula() {
        return IdMatricula;
    }

    public void setIdMatricula(Integer IdMatricula) {
        this.IdMatricula = IdMatricula;
    }

    public String getCodigoMatricula() {
        return CodigoMatricula;
    }

    public void setCodigoMatricula(String CodigoMatricula) {
        this.CodigoMatricula = CodigoMatricula;
    }

    public String getFechaMatricula() {
        return FechaMatricula;
    }

    public void setFechaMatricula(String FechaMatricula) {
        this.FechaMatricula = FechaMatricula;
    }

    public String getEstadoMatricula() {
        return EstadoMatricula;
    }

    public void setEstadoMatricula(String EstadoMatricula) {
        this.EstadoMatricula = EstadoMatricula;
    }

    public PeriodoAcademico getPeriodoAcademicoMatricula() {
        return periodoAcademicoMatricula;
    }

    public void setPeriodoAcademicoMatricula(PeriodoAcademico periodoAcademicoMatricula) {
        this.periodoAcademicoMatricula = periodoAcademicoMatricula;
    }

    public Alumno getAlumnoMatricula() {
        return alumnoMatricula;
    }

    public void setAlumnoMatricula(Alumno alumnoMatricula) {
        this.alumnoMatricula = alumnoMatricula;
    }

    public Integer getAlumnoID() {
        return AlumnoID;
    }

    public void setAlumnoID(Integer AlumnoID) {
        this.AlumnoID = AlumnoID;
    }

    public Integer getPeriodoID() {
        return PeriodoID;
    }

    public void setPeriodoID(Integer PeriodoID) {
        this.PeriodoID = PeriodoID;
    }

    @Override
    public String toString() {
        return CodigoMatricula +  " " + alumnoMatricula.getDatosAlumno().getNumeroCedula() +  " "+ alumnoMatricula.getDatosAlumno().getNombre() +" "+ alumnoMatricula.getDatosAlumno().getApellido()+"\n";
    }
    
}
