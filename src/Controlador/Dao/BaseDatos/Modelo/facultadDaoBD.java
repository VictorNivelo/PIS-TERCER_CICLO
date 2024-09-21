
package Controlador.Dao.BaseDatos.Modelo;

import Controlador.Dao.BaseDatos.AdaptadorDaoBD;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Facultad;
import java.io.IOException;

/**
 *
 * @author Victor
 */
public class facultadDaoBD extends AdaptadorDaoBD<Facultad> {

    private ListaDinamica<Facultad> ListaAsistencias;
    private Facultad asistencias = new Facultad();
    private Integer Indice = -1;

    public facultadDaoBD() {
        super(Facultad.class);
        this.ListaAsistencias = new ListaDinamica<>();
    }

    public ListaDinamica<Facultad> getListaAlumno() {
        if (ListaAsistencias.EstaVacio()) {
            ListaAsistencias = this.listar();
        }
        return ListaAsistencias;
    }

    public void setListaAlumno(ListaDinamica<Facultad> ListaAlumno) {
        this.ListaAsistencias = ListaAlumno;
    }

    public Facultad getAlumno() {
        return asistencias;
    }

    public void setAlumno(Facultad alumno) {
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
