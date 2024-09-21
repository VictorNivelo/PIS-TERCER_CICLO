
package Controlador.Dao.Modelo;

import Controlador.Dao.DaoImplement;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Materia;

/**
 *
 * @author Victor
 */
public class materiaDao extends DaoImplement<Materia>{
    private ListaDinamica<Materia> listaMateria = new ListaDinamica<>();
    private Materia materia;

    public materiaDao() {
        super(Materia.class);
    }
    
    public ListaDinamica<Materia> getListaMateria() {
        listaMateria = all();
        return listaMateria;
    }

    public void setListaMateria(ListaDinamica<Materia> listaMateria) {
        this.listaMateria = listaMateria;
    }

    public Materia getMateria() {
        if(materia ==null){
            materia = new Materia();
        }
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }
        
    public Boolean Persist(){
        materia.setIdMateria(all().getLongitud()+1);
        return Persist(materia);
    }
    
}
