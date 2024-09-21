
package Controlador.Dao.Modelo;

import Controlador.Dao.DaoImplement;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Docente;

/**
 *
 * @author Victor
 */
public class docenteDao extends DaoImplement<Docente>{
    private ListaDinamica<Docente> listaDocentes = new ListaDinamica<>();
    private Docente docentes;

    public docenteDao() {
        super(Docente.class);
    }

    public ListaDinamica<Docente> getListaDocentes() {
        listaDocentes = all();
        return listaDocentes;
    }

    public void setListaDocentes(ListaDinamica<Docente> listaDocentes) {
        this.listaDocentes = listaDocentes;
    }

    public Docente getDocentes() {
        if(docentes == null){
            docentes = new Docente();
        }
        return docentes;
    }

    public void setDocentes(Docente docentes) {
        this.docentes = docentes;
    }
    
    public Boolean Persist(){
        docentes.setIdDocente(all().getLongitud()+1);
        return Persist(docentes);
    }
    
}
