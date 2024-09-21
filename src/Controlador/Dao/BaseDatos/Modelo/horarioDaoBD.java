
package Controlador.Dao.BaseDatos.Modelo;

import Controlador.Dao.BaseDatos.AdaptadorDaoBD;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Horario;
import java.io.IOException;

/**
 *
 * @author Victor
 */
public class horarioDaoBD extends AdaptadorDaoBD<Horario> {

    private ListaDinamica<Horario> ListaAsistencias;
    private Horario asistencias = new Horario();
    private Integer Indice = -1;

    public horarioDaoBD() {
        super(Horario.class);
        this.ListaAsistencias = new ListaDinamica<>();
    }

    public ListaDinamica<Horario> getListaAlumno() {
        if (ListaAsistencias.EstaVacio()) {
            ListaAsistencias = this.listar();
        }
        return ListaAsistencias;
    }

    public void setListaAlumno(ListaDinamica<Horario> ListaAlumno) {
        this.ListaAsistencias = ListaAlumno;
    }

    public Horario getAlumno() {
        return asistencias;
    }

    public void setAlumno(Horario alumno) {
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
