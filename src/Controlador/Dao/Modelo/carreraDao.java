
package Controlador.Dao.Modelo;

import Controlador.Dao.DaoImplement;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Carrera;

/**
 *
 * @author Victor
 */
public class carreraDao extends DaoImplement<Carrera> {

    private ListaDinamica<Carrera> ListaCarreras = new ListaDinamica<>();
    private Carrera carreras;

    public carreraDao() {
        super(Carrera.class);
    }

    public ListaDinamica<Carrera> getListaCarreras() {
        ListaCarreras = all();
        return ListaCarreras;
    }

    public void setListaCarreras(ListaDinamica<Carrera> ListaCarreras) {
        this.ListaCarreras = ListaCarreras;
    }

    public Carrera getCarreras() {
        if(carreras == null){
            carreras = new Carrera();
        }
        return carreras;
    }

    public void setCarreras(Carrera carreras) {
        this.carreras = carreras;
    }
    
    public Boolean Persist() {
        carreras.setIdCarrera(all().getLongitud() + 1);
        return Persist(carreras);
    }
    
}
