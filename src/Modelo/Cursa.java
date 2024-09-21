
package Modelo;

/**
 *
 * @author Victor
 */
public class Cursa {
    private Integer IdCurso;
    private String CodigoCursa;
    
    private Paralelo ParaleloCursa;
    private Integer ParaleloID;
    
    private Matricula matriculaCursa;
    private Integer MatriculaID;
    
    private Materia MateriaCursa;
    private Integer MateriaID;
    
    private Docente docenteCursa;
    private Integer DocenteID;
    
    public Cursa() {
        
    }

    public Integer getIdCurso() {
        return IdCurso;
    }

    public void setIdCurso(Integer IdCurso) {
        this.IdCurso = IdCurso;
    }

    public Paralelo getParaleloCursa() {
        return ParaleloCursa;
    }

    public void setParaleloCursa(Paralelo ParaleloCursa) {
        this.ParaleloCursa = ParaleloCursa;
    }

    public Matricula getMatriculaCursa() {
        return matriculaCursa;
    }

    public void setMatriculaCursa(Matricula matriculaCursa) {
        this.matriculaCursa = matriculaCursa;
    }

    public Docente getDocenteCursa() {
        return docenteCursa;
    }

    public void setDocenteCursa(Docente docenteCursa) {
        this.docenteCursa = docenteCursa;
    }

    public String getCodigoCursa() {
        return CodigoCursa;
    }

    public void setCodigoCursa(String CodigoCursa) {
        this.CodigoCursa = CodigoCursa;
    }

    public Integer getMatriculaID() {
        return MatriculaID;
    }

    public void setMatriculaID(Integer MatriculaID) {
        this.MatriculaID = MatriculaID;
    }

    public Integer getDocenteID() {
        return DocenteID;
    }

    public void setDocenteID(Integer DocenteID) {
        this.DocenteID = DocenteID;
    }

    public Integer getParaleloID() {
        return ParaleloID;
    }

    public void setParaleloID(Integer ParaleloID) {
        this.ParaleloID = ParaleloID;
    }

    public Materia getMateriaCursa() {
        return MateriaCursa;
    }

    public void setMateriaCursa(Materia MateriaCursa) {
        this.MateriaCursa = MateriaCursa;
    }

    public Integer getMateriaID() {
        return MateriaID;
    }

    public void setMateriaID(Integer MateriaID) {
        this.MateriaID = MateriaID;
    }
    
    @Override
    public String toString() {
        return ParaleloCursa + "\n";
    }
    
}
