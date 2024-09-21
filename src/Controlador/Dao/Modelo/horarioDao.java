
package Controlador.Dao.Modelo;

import Controlador.Dao.DaoImplement;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Horario;

/**
 *
 * @author Victor
 */
public class horarioDao extends DaoImplement<Horario> {

    private ListaDinamica<Horario> ListaHorario= new ListaDinamica<>();
    private Horario horarios;

    public horarioDao() {
        super(Horario.class);
    }

    public ListaDinamica<Horario> getListaHorario() {
        ListaHorario = all();
        return ListaHorario;
    }

    public void setListaHorario(ListaDinamica<Horario> ListaHorario) {
        this.ListaHorario = ListaHorario;
    }

    public Horario getHorarios() {
        if(horarios == null){
            horarios = new Horario();
        }
        return horarios;
    }

    public void setHorarios(Horario horarios) {
        this.horarios = horarios;
    }
    
    public Boolean Persist() {
        horarios.setIdHorario(all().getLongitud() + 1);
        return Persist(horarios);
    }
    
}
