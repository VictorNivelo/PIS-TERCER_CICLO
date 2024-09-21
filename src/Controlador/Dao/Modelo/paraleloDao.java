
package Controlador.Dao.Modelo;

import Controlador.Dao.DaoImplement;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Paralelo;

/**
 *
 * @author Victor
 */
public class paraleloDao extends DaoImplement<Paralelo>{
    
    private ListaDinamica<Paralelo> ListaParalelo;
    private Paralelo paralelo;
    
    public paraleloDao() {
        super(Paralelo.class);
    }

    public ListaDinamica<Paralelo> getListaParalelo() {
        ListaParalelo = all();
        return ListaParalelo;
    }

    public void setListaParalelo(ListaDinamica<Paralelo> ListaParalelo) {
        this.ListaParalelo = ListaParalelo;
    }

    public Paralelo getParalelo() {
        if(paralelo == null){
            paralelo = new Paralelo();
        }
        return paralelo;
    }

    public void setParalelo(Paralelo paralelo) {
        this.paralelo = paralelo;
    }
    
    public Boolean persist(){
        paralelo.setIdParalelo(all().getLongitud()+1);
        return Persist(paralelo);
    }
    
}
