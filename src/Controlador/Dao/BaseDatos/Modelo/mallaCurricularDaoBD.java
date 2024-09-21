
package Controlador.Dao.BaseDatos.Modelo;

import Controlador.Dao.BaseDatos.AdaptadorDaoBD;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.MallaC;
import java.io.IOException;

/**
 *
 * @author Victor
 */
public class mallaCurricularDaoBD extends AdaptadorDaoBD<MallaC> {

    private ListaDinamica<MallaC> ListaAsistencias;
    private MallaC asistencias = new MallaC();
    private Integer Indice = -1;

    public mallaCurricularDaoBD() {
        super(MallaC.class);
        this.ListaAsistencias = new ListaDinamica<>();
    }

    public ListaDinamica<MallaC> getListaAlumno() {
        if (ListaAsistencias.EstaVacio()) {
            ListaAsistencias = this.listar();
        }
        return ListaAsistencias;
    }

    public void setListaAlumno(ListaDinamica<MallaC> ListaAlumno) {
        this.ListaAsistencias = ListaAlumno;
    }

    public MallaC getAlumno() {
        return asistencias;
    }

    public void setAlumno(MallaC alumno) {
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
