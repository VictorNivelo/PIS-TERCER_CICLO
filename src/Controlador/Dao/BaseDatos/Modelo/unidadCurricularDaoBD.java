
package Controlador.Dao.BaseDatos.Modelo;

import Controlador.Dao.BaseDatos.AdaptadorDaoBD;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.UnidadCurricular;
import java.io.IOException;

/**
 *
 * @author Victor
 */
public class unidadCurricularDaoBD extends AdaptadorDaoBD<UnidadCurricular> {

    private ListaDinamica<UnidadCurricular> ListaAsistencias;
    private UnidadCurricular asistencias = new UnidadCurricular();
    private Integer Indice = -1;

    public unidadCurricularDaoBD() {
        super(UnidadCurricular.class);
        this.ListaAsistencias = new ListaDinamica<>();
    }

    public ListaDinamica<UnidadCurricular> getListaAlumno() {
        if (ListaAsistencias.EstaVacio()) {
            ListaAsistencias = this.listar();
        }
        return ListaAsistencias;
    }

    public void setListaAlumno(ListaDinamica<UnidadCurricular> ListaAlumno) {
        this.ListaAsistencias = ListaAlumno;
    }

    public UnidadCurricular getAlumno() {
        return asistencias;
    }

    public void setAlumno(UnidadCurricular alumno) {
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
