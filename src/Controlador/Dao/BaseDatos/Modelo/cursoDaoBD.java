
package Controlador.Dao.BaseDatos.Modelo;

import Controlador.Dao.BaseDatos.AdaptadorDaoBD;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Cursa;
import java.io.IOException;

/**
 *
 * @author Victor
 */
public class cursoDaoBD extends AdaptadorDaoBD<Cursa> {

    private ListaDinamica<Cursa> ListaAsistencias;
    private Cursa asistencias = new Cursa();
    private Integer Indice = -1;

    public cursoDaoBD() {
        super(Cursa.class);
        this.ListaAsistencias = new ListaDinamica<>();
    }

    public ListaDinamica<Cursa> getListaAlumno() {
        if (ListaAsistencias.EstaVacio()) {
            ListaAsistencias = this.listar();
        }
        return ListaAsistencias;
    }

    public void setListaAlumno(ListaDinamica<Cursa> ListaAlumno) {
        this.ListaAsistencias = ListaAlumno;
    }

    public Cursa getAlumno() {
        return asistencias;
    }

    public void setAlumno(Cursa alumno) {
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
