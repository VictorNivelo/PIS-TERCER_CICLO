
package Controlador.Dao.BaseDatos.Modelo;

import Controlador.Dao.BaseDatos.AdaptadorDaoBD;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.NombreCiclo;
import java.io.IOException;

/**
 *
 * @author Victor
 */
public class nombreCicloDaoBD extends AdaptadorDaoBD<NombreCiclo> {

    private ListaDinamica<NombreCiclo> ListaAsistencias;
    private NombreCiclo asistencias = new NombreCiclo();
    private Integer Indice = -1;

    public nombreCicloDaoBD() {
        super(NombreCiclo.class);
        this.ListaAsistencias = new ListaDinamica<>();
    }

    public ListaDinamica<NombreCiclo> getListaAlumno() {
        if (ListaAsistencias.EstaVacio()) {
            ListaAsistencias = this.listar();
        }
        return ListaAsistencias;
    }

    public void setListaAlumno(ListaDinamica<NombreCiclo> ListaAlumno) {
        this.ListaAsistencias = ListaAlumno;
    }

    public NombreCiclo getAlumno() {
        return asistencias;
    }

    public void setAlumno(NombreCiclo alumno) {
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
