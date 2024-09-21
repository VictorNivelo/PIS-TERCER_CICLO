
package Controlador.Dao.BaseDatos.Modelo;

import Controlador.Dao.BaseDatos.AdaptadorDaoBD;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Docente;
import java.io.IOException;

/**
 *
 * @author Victor
 */
public class docenteDaoBD extends AdaptadorDaoBD<Docente> {

    private ListaDinamica<Docente> ListaAsistencias;
    private Docente asistencias = new Docente();
    private Integer Indice = -1;

    public docenteDaoBD() {
        super(Docente.class);
        this.ListaAsistencias = new ListaDinamica<>();
    }

    public ListaDinamica<Docente> getListaAlumno() {
        if (ListaAsistencias.EstaVacio()) {
            ListaAsistencias = this.listar();
        }
        return ListaAsistencias;
    }

    public void setListaAlumno(ListaDinamica<Docente> ListaAlumno) {
        this.ListaAsistencias = ListaAlumno;
    }

    public Docente getAlumno() {
        return asistencias;
    }

    public void setAlumno(Docente alumno) {
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
