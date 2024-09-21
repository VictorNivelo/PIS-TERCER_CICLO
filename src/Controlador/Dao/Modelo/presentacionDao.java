
package Controlador.Dao.Modelo;

import Controlador.Dao.DaoImplement;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Presentacion;

/**
 *
 * @author Victor
 */
public class presentacionDao extends DaoImplement<Presentacion>{
    private ListaDinamica<Presentacion> ListaPresentacion = new ListaDinamica<>();
    private Presentacion presentaciones;
    
    public presentacionDao(){
        super (Presentacion.class);
    }

    public ListaDinamica<Presentacion> getListaPresentacion() {
        ListaPresentacion = all();
        return ListaPresentacion;
    }

    public void setListaPresentacion(ListaDinamica<Presentacion> ListaPresentacion) {
        this.ListaPresentacion = ListaPresentacion;
    }

    public Presentacion getPresentaciones() {
        if(presentaciones == null){
            presentaciones = new Presentacion();
        }
        return presentaciones;
    }

    public void setPresentaciones(Presentacion presentaciones) {
        this.presentaciones = presentaciones;
    }

    public Boolean Persist(){
        presentaciones.setIdPresentacion(all().getLongitud()+1);
        return Persist(presentaciones);
    }
    
}
