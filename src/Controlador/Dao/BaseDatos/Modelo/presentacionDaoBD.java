
package Controlador.Dao.BaseDatos.Modelo;

import Controlador.Dao.BaseDatos.AdaptadorDaoBD;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Presentacion;
import java.io.IOException;

/**
 *
 * @author Victor
 */
public class presentacionDaoBD extends AdaptadorDaoBD<Presentacion> {

    private ListaDinamica<Presentacion> ListaAsistencias;
    private Presentacion asistencias = new Presentacion();
    private Integer Indice = -1;

    public presentacionDaoBD() {
        super(Presentacion.class);
        this.ListaAsistencias = new ListaDinamica<>();
    }

    public ListaDinamica<Presentacion> getListaAlumno() {
        if (ListaAsistencias.EstaVacio()) {
            ListaAsistencias = this.listar();
        }
        return ListaAsistencias;
    }

    public void setListaAlumno(ListaDinamica<Presentacion> ListaAlumno) {
        this.ListaAsistencias = ListaAlumno;
    }

    public Presentacion getAlumno() {
        return asistencias;
    }

    public void setAlumno(Presentacion alumno) {
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
