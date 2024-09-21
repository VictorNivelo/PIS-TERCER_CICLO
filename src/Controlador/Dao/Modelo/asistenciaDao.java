
package Controlador.Dao.Modelo;

import Controlador.Dao.DaoImplement;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Asistencia;

/**
 *
 * @author Victor
 */
public class asistenciaDao extends DaoImplement<Asistencia>{
    private ListaDinamica<Asistencia> listaAsistencia = new ListaDinamica<>();
    private Asistencia asistencia;

    public asistenciaDao(){
        super(Asistencia.class);
    }

    public ListaDinamica<Asistencia> getListaAsistencia() {
        listaAsistencia = all();
        return listaAsistencia;
    }

    public void setListaAsistencia(ListaDinamica<Asistencia> listaAsistencia) {
        this.listaAsistencia = listaAsistencia;
    }

    public Asistencia getAsistencia() {
        if(asistencia == null){
            asistencia = new Asistencia();
        }
        return asistencia;
    }

    public void setAsistencia(Asistencia asistencia) {
        this.asistencia = asistencia;
    }
    
    public Boolean Persist(){
        asistencia.setIdAsistencia(all().getLongitud()+1);
        return  Persist(asistencia);
    }
    
}
