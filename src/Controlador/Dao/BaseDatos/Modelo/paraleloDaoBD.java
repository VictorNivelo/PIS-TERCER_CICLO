
package Controlador.Dao.BaseDatos.Modelo;

import Controlador.Dao.BaseDatos.AdaptadorDaoBD;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Paralelo;
import java.io.IOException;

/**
 *
 * @author Victor
 */
public class paraleloDaoBD extends AdaptadorDaoBD<Paralelo> {

    private ListaDinamica<Paralelo> ListaAsistencias;
    private Paralelo asistencias = new Paralelo();
    private Integer Indice = -1;

    public paraleloDaoBD() {
        super(Paralelo.class);
        this.ListaAsistencias = new ListaDinamica<>();
    }

    public ListaDinamica<Paralelo> getListaAlumno() {
        if (ListaAsistencias.EstaVacio()) {
            ListaAsistencias = this.listar();
        }
        return ListaAsistencias;
    }

    public void setListaAlumno(ListaDinamica<Paralelo> ListaAlumno) {
        this.ListaAsistencias = ListaAlumno;
    }

    public Paralelo getAlumno() {
        return asistencias;
    }

    public void setAlumno(Paralelo alumno) {
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
