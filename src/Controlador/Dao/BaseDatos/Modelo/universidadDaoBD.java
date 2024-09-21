
package Controlador.Dao.BaseDatos.Modelo;

import Controlador.Dao.BaseDatos.AdaptadorDaoBD;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Universidad;
import java.io.IOException;

/**
 *
 * @author Victor
 */
public class universidadDaoBD extends AdaptadorDaoBD<Universidad> {

    private ListaDinamica<Universidad> ListaUniversidad;
    private Universidad universidad = new Universidad();
    private Integer Indice = -1;

    public universidadDaoBD() {
        super(Universidad.class);
        this.ListaUniversidad = new ListaDinamica<>();
    }

    public ListaDinamica<Universidad> getListaUniversidad() {
        if (ListaUniversidad.EstaVacio()) {
            ListaUniversidad = this.listar();
        }
        return ListaUniversidad;
    }

    public void setListaUniversidad(ListaDinamica<Universidad> ListaUniversidad) {
        this.ListaUniversidad = ListaUniversidad;
    }

    public Universidad getUniversidad() {
        if(universidad == null){
            universidad = new Universidad();
        }
        return universidad;
    }

    public void setUniversidad(Universidad universidad) {
        this.universidad = universidad;
    }

    public Integer getIndice() {
        return Indice;
    }

    public void setIndice(Integer Indice) {
        this.Indice = Indice;
    }

    public Boolean GuardarBD() throws Exception {
        return super.guardarb(this.universidad);
    }
    
//    public Integer save() throws Exception {
//        return super.guardar(this.universidad, "SQC_UNIVERSIDAD");
//    }

    public Boolean Modificar() throws IOException {
        try {
            modificar(this.universidad);
            return true;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
