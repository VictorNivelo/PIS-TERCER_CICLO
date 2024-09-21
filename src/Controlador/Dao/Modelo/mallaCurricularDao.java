
package Controlador.Dao.Modelo;

import Controlador.Dao.DaoImplement;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.MallaC;

/**
 *
 * @author Victor
 */
public class mallaCurricularDao extends DaoImplement<MallaC> {

    private ListaDinamica<MallaC> ListaMalla = new ListaDinamica<>();
    private MallaC mallaCurricular;
    
    public mallaCurricularDao() {
        super(MallaC.class);
    }

    public ListaDinamica<MallaC> getListaMalla() {
        ListaMalla = all();
        return ListaMalla;
    }

    public void setListaMalla(ListaDinamica<MallaC> ListaCarreras) {
        this.ListaMalla = ListaCarreras;
    }

    public MallaC getMallaCurricular() {
        if(mallaCurricular == null){
            mallaCurricular = new MallaC();
        }
        return mallaCurricular;
    }

    public void setMallaCurricular(MallaC mallaCurricular) {
        this.mallaCurricular = mallaCurricular;
    }
    
    public Boolean Persist() {
        mallaCurricular.setIdMallaCurricular(all().getLongitud() + 1);
        return Persist(mallaCurricular);
    }
    
}
