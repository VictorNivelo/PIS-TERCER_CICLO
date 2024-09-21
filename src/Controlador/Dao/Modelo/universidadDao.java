
package Controlador.Dao.Modelo;

import Controlador.Dao.DaoImplement;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Universidad;

/**
 *
 * @author Victor
 */
public class universidadDao extends DaoImplement<Universidad>{
    private ListaDinamica<Universidad> ListaUniversidad = new ListaDinamica<>();
    private Universidad universidad;
    
    public universidadDao(){
        super (Universidad.class);
    }

    public ListaDinamica<Universidad> getListaUniversid() {
        ListaUniversidad = all();
        return ListaUniversidad;
    }

    public void setListaUniversid(ListaDinamica<Universidad> ListaUniversidades) {
        this.ListaUniversidad = ListaUniversidades;
    }

    public Universidad getUniversidad() {
        if(universidad == null){
            universidad = new Universidad();
        }
        return universidad;
    }

    public void setUniversidad(Universidad universidades) {
        this.universidad = universidades;
    }
    
    public Boolean Persist(){
        universidad.setIdU(all().getLongitud()+1);
        return Persist(universidad);
    }
    
}
