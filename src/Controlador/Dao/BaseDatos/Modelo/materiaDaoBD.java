
package Controlador.Dao.BaseDatos.Modelo;

import Controlador.Dao.BaseDatos.AdaptadorDaoBD;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Materia;
import java.io.IOException;

/**
 *
 * @author Victor
 */
public class materiaDaoBD extends AdaptadorDaoBD<Materia> {

    private ListaDinamica<Materia> ListaAsistencias;
    private Materia asistencias = new Materia();
    private Integer Indice = -1;

    public materiaDaoBD() {
        super(Materia.class);
        this.ListaAsistencias = new ListaDinamica<>();
    }

    public ListaDinamica<Materia> getListaAlumno() {
        if (ListaAsistencias.EstaVacio()) {
            ListaAsistencias = this.listar();
        }
        return ListaAsistencias;
    }

    public void setListaAlumno(ListaDinamica<Materia> ListaAlumno) {
        this.ListaAsistencias = ListaAlumno;
    }

    public Materia getAlumno() {
        return asistencias;
    }

    public void setAlumno(Materia alumno) {
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
