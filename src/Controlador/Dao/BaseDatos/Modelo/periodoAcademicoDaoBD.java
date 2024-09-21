
package Controlador.Dao.BaseDatos.Modelo;

import Controlador.Dao.BaseDatos.AdaptadorDaoBD;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.PeriodoAcademico;
import java.io.IOException;

/**
 *
 * @author Victor
 */
public class periodoAcademicoDaoBD extends AdaptadorDaoBD<PeriodoAcademico> {

    private ListaDinamica<PeriodoAcademico> ListaAsistencias;
    private PeriodoAcademico asistencias = new PeriodoAcademico();
    private Integer Indice = -1;

    public periodoAcademicoDaoBD() {
        super(PeriodoAcademico.class);
        this.ListaAsistencias = new ListaDinamica<>();
    }

    public ListaDinamica<PeriodoAcademico> getListaAlumno() {
        if (ListaAsistencias.EstaVacio()) {
            ListaAsistencias = this.listar();
        }
        return ListaAsistencias;
    }

    public void setListaAlumno(ListaDinamica<PeriodoAcademico> ListaAlumno) {
        this.ListaAsistencias = ListaAlumno;
    }

    public PeriodoAcademico getAlumno() {
        return asistencias;
    }

    public void setAlumno(PeriodoAcademico alumno) {
        this.asistencias = alumno;
    }

    public Integer getIndice() {
        return Indice;
    }

    public void setIndice(Integer Indice) {
        this.Indice = Indice;
    }

    public Boolean GuardarBD() throws Exception {
        return super.guardarb(this.asistencias);
    }

    public Boolean Modificar() throws IOException {
        try {
            modificar(this.asistencias);
            return true;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
