
package Controlador.Dao.Modelo;

import Controlador.Dao.DaoImplement;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.UnidadCurricular;

/**
 *
 * @author Victor
 */
public class unidadCurricularDao extends DaoImplement<UnidadCurricular>{
    private ListaDinamica<UnidadCurricular> ListaUnidadCurricular = new ListaDinamica<>();
    private UnidadCurricular unidadCurriculares;
    
    public unidadCurricularDao(){
        super (UnidadCurricular.class);
    }

    public ListaDinamica<UnidadCurricular> getListaUnidadCurricular() {
        ListaUnidadCurricular = all();
        return ListaUnidadCurricular;
    }

    public void setListaUnidadCurricular(ListaDinamica<UnidadCurricular> ListaUnidadCurricular) {
        this.ListaUnidadCurricular = ListaUnidadCurricular;
    }

    public UnidadCurricular getUnidadCurriculares() {
        if(unidadCurriculares == null){
            unidadCurriculares = new UnidadCurricular();
        }
        return unidadCurriculares;
    }

    public void setUnidadCurriculares(UnidadCurricular unidadCurriculares) {
        this.unidadCurriculares = unidadCurriculares;
    }

    public Boolean Persist(){
        unidadCurriculares.setIdUnidadCurricular(all().getLongitud()+1);
        return Persist(unidadCurriculares);
    }
}
