
package Controlador.Dao.BaseDatos.Modelo;

import Controlador.Dao.BaseDatos.AdaptadorDaoBD;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Alumno;
import java.io.IOException;

/**
 *
 * @author Victor
 */
public class alumnoDaoBD extends AdaptadorDaoBD<Alumno> {

    private ListaDinamica<Alumno> ListaAlumno;
    private Alumno alumno = new Alumno();
    private Integer Indice = -1;

    public alumnoDaoBD() {
        super(Alumno.class);
    }

    public ListaDinamica<Alumno> getListaAlumno() {
        if (ListaAlumno.EstaVacio()) {
            ListaAlumno = this.listar();
        }
        return ListaAlumno;
    }

    public void setListaAlumno(ListaDinamica<Alumno> ListaAlumno) {
        this.ListaAlumno = ListaAlumno;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Integer getIndice() {
        return Indice;
    }

    public void setIndice(Integer Indice) {
        this.Indice = Indice;
    }

    public Boolean GuardarBD() throws Exception {
        return super.guardarb(this.alumno);
    }

    public Boolean Modificar() throws IOException {
        try {
            modificar(this.alumno);
            return true;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
