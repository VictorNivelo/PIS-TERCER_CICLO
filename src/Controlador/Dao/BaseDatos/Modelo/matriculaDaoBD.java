
package Controlador.Dao.BaseDatos.Modelo;

import Controlador.Dao.BaseDatos.AdaptadorDaoBD;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Matricula;
import java.io.IOException;

/**
 *
 * @author romer
 */
public class matriculaDaoBD extends AdaptadorDaoBD<Matricula> {

    private ListaDinamica<Matricula> ListaAsistencias;
    private Matricula asistencias = new Matricula();
    private Integer Indice = -1;

    public matriculaDaoBD() {
        super(Matricula.class);
        this.ListaAsistencias = new ListaDinamica<>();
    }

    public ListaDinamica<Matricula> getListaAlumno() {
        if (ListaAsistencias.EstaVacio()) {
            ListaAsistencias = this.listar();
        }
        return ListaAsistencias;
    }

    public void setListaAlumno(ListaDinamica<Matricula> ListaAlumno) {
        this.ListaAsistencias = ListaAlumno;
    }

    public Matricula getAlumno() {
        return asistencias;
    }

    public void setAlumno(Matricula alumno) {
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
