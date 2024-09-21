
package Controlador.Dao.BaseDatos.Modelo;

import Controlador.Dao.BaseDatos.AdaptadorDaoBD;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Ciclo;
import java.io.IOException;

/**
 *
 * @author Victor
 */
public class cicloDaoBD extends AdaptadorDaoBD<Ciclo> {

    private ListaDinamica<Ciclo> ListaAsistencias;
    private Ciclo asistencias = new Ciclo();
    private Integer Indice = -1;

    public cicloDaoBD() {
        super(Ciclo.class);
        this.ListaAsistencias = new ListaDinamica<>();
    }

    public ListaDinamica<Ciclo> getListaAlumno() {
        if (ListaAsistencias.EstaVacio()) {
            ListaAsistencias = this.listar();
        }
        return ListaAsistencias;
    }

    public void setListaAlumno(ListaDinamica<Ciclo> ListaAlumno) {
        this.ListaAsistencias = ListaAlumno;
    }

    public Ciclo getAlumno() {
        return asistencias;
    }

    public void setAlumno(Ciclo alumno) {
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