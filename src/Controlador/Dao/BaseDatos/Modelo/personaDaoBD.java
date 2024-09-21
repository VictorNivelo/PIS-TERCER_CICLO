
package Controlador.Dao.BaseDatos.Modelo;

import Controlador.Dao.BaseDatos.AdaptadorDaoBD;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Persona;
import java.io.IOException;

/**
 *
 * @author Victor
 */
public class personaDaoBD extends AdaptadorDaoBD<Persona> {

    private ListaDinamica<Persona> ListaAsistencias;
    private Persona asistencias = new Persona();
    private Integer Indice = -1;

    public personaDaoBD() {
        super(Persona.class);
        this.ListaAsistencias = new ListaDinamica<>();
    }

    public ListaDinamica<Persona> getListaAlumno() {
        if (ListaAsistencias.EstaVacio()) {
            ListaAsistencias = this.listar();
        }
        return ListaAsistencias;
    }

    public void setListaAlumno(ListaDinamica<Persona> ListaAlumno) {
        this.ListaAsistencias = ListaAlumno;
    }

    public Persona getAlumno() {
        return asistencias;
    }

    public void setAlumno(Persona alumno) {
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
