
package Controlador.Dao.BaseDatos.Modelo;

import Controlador.Dao.BaseDatos.AdaptadorDaoBD;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Carrera;
import java.io.IOException;

/**
 *
 * @author Victor
 */
public class carreraDaoBD extends AdaptadorDaoBD<Carrera> {

    private ListaDinamica<Carrera> ListaAsistencias;
    private Carrera asistencias = new Carrera();
    private Integer Indice = -1;

    public carreraDaoBD() {
        super(Carrera.class);
        this.ListaAsistencias = new ListaDinamica<>();
    }

    public ListaDinamica<Carrera> getListaAlumno() {
        if (ListaAsistencias.EstaVacio()) {
            ListaAsistencias = this.listar();
        }
        return ListaAsistencias;
    }

    public void setListaAlumno(ListaDinamica<Carrera> ListaAlumno) {
        this.ListaAsistencias = ListaAlumno;
    }

    public Carrera getAlumno() {
        return asistencias;
    }

    public void setAlumno(Carrera alumno) {
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
