
package Controlador.Dao.Modelo;

import Controlador.Dao.DaoImplement;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.NombreCiclo;

/**
 *
 * @author Victor
 */
public class nombreCicloDao extends DaoImplement<NombreCiclo>{
    private ListaDinamica<NombreCiclo> ListaNombreCiclo = new ListaDinamica<>();
    private NombreCiclo nombreCiclo;
    
    public nombreCicloDao(){
        super (NombreCiclo.class);
    }

    public ListaDinamica<NombreCiclo> getListaNombreCiclo() {
        ListaNombreCiclo = all();
        return ListaNombreCiclo;
    }

    public void setListaNombreCiclo(ListaDinamica<NombreCiclo> ListaNombreCiclo) {
        this.ListaNombreCiclo = ListaNombreCiclo;
    }

    public NombreCiclo getNombreCiclo() {
        if(nombreCiclo == null){
            nombreCiclo = new  NombreCiclo();
        }
        return nombreCiclo;
    }

    public void setNombreCiclo(NombreCiclo nombreCiclo) {
        this.nombreCiclo = nombreCiclo;
    }
    
    public Boolean Persist(){
        nombreCiclo.setIdNombreCiclo(all().getLongitud()+1);
        return Persist(nombreCiclo);
    }
    
}
