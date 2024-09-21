
package Controlador.Dao.BaseDatos.Modelo;

import Controlador.Dao.BaseDatos.AdaptadorDaoBD;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Rol;
import java.io.IOException;

/**
 *
 * @author Victor
 */
public class rolDaoBD extends AdaptadorDaoBD<Rol> {

    private ListaDinamica<Rol> ListaAsistencias;
    private Rol asistencias = new Rol();
    private Integer Indice = -1;

    public rolDaoBD() {
        super(Rol.class);
        this.ListaAsistencias = new ListaDinamica<>();
    }

    public ListaDinamica<Rol> getListaAlumno() {
        if (ListaAsistencias.EstaVacio()) {
            ListaAsistencias = this.listar();
        }
        return ListaAsistencias;
    }

    public void setListaAlumno(ListaDinamica<Rol> ListaAlumno) {
        this.ListaAsistencias = ListaAlumno;
    }

    public Rol getAlumno() {
        return asistencias;
    }

    public void setAlumno(Rol alumno) {
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
