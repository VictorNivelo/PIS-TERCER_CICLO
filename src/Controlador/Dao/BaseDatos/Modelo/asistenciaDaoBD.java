
package Controlador.Dao.BaseDatos.Modelo;

import Controlador.Dao.BaseDatos.AdaptadorDaoBD;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Asistencia;
import java.io.IOException;

/**
 *
 * @author Victor
 */
public class asistenciaDaoBD extends AdaptadorDaoBD<Asistencia> {

    private ListaDinamica<Asistencia> ListaAsistencias;
    private Asistencia asistencias = new Asistencia();
    private Integer Indice = -1;

    public asistenciaDaoBD() {
        super(Asistencia.class);
        this.ListaAsistencias = new ListaDinamica<>();
    }

    public ListaDinamica<Asistencia> getListaAlumno() {
        if (ListaAsistencias.EstaVacio()) {
            ListaAsistencias = this.listar();
        }
        return ListaAsistencias;
    }

    public void setListaAlumno(ListaDinamica<Asistencia> ListaAlumno) {
        this.ListaAsistencias = ListaAlumno;
    }

    public Asistencia getAlumno() {
        return asistencias;
    }

    public void setAlumno(Asistencia alumno) {
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
