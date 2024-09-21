
package Controlador.Dao.Modelo;

import Controlador.Dao.DaoImplement;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Facultad;

/**
 *
 * @author Victor
 */
public class facultadDao extends DaoImplement<Facultad>{
    private ListaDinamica<Facultad> listaFacultad = new ListaDinamica<>();
    private Facultad facultades;

    public facultadDao() {
        super(Facultad.class);
    }

    public ListaDinamica<Facultad> getListaFacultad() {
        listaFacultad = all();
        return listaFacultad;
    }

    public void setListaFacultad(ListaDinamica<Facultad> listaFacultad) {
        this.listaFacultad = listaFacultad;
    }

    public Facultad getFacultades() {
        if(facultades == null){
            facultades = new Facultad();
        }
        return facultades;
    }

    public void setFacultades(Facultad facultades) {
        this.facultades = facultades;
    }
    
    public Boolean Persist(){
        facultades.setIdFacultad(all().getLongitud()+1);
        return Persist(facultades);
    }
    
}
