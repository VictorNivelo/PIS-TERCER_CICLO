
package Controlador.Dao.Modelo;

import Controlador.Dao.DaoImplement;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Cursa;

/**
 *
 * @author Victor
 */
public class cursoDao extends DaoImplement<Cursa>{
    private ListaDinamica<Cursa> listaCursa = new ListaDinamica<>();
    private Cursa cursos;

    public cursoDao() {
        super(Cursa.class);
    }

    public ListaDinamica<Cursa> getListaCursa() {
        listaCursa = all();
        return listaCursa;
    }

    public void setListaCursa(ListaDinamica<Cursa> listaCursa) {
        this.listaCursa = listaCursa;
    }

    public Cursa getCursos() {
        if(cursos == null){
            cursos = new Cursa();
        }
        return cursos;
    }

    public void setCursos(Cursa cursos) {
        this.cursos = cursos;
    }
        
    public Boolean Persist(){
        cursos.setIdCurso(all().getLongitud()+1);
        return Persist(cursos);
    }

}
