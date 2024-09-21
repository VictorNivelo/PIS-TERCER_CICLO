
package Controlador.Dao.Modelo;

import Controlador.Dao.DaoImplement;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.PeriodoAcademico;

/**
 *
 * @author Victor
 */
public class periodoAcademicoDao extends DaoImplement<PeriodoAcademico> {
    private ListaDinamica<PeriodoAcademico> ListaPeriodos;
    private PeriodoAcademico periodo;
    
    public periodoAcademicoDao(){
        super(PeriodoAcademico.class);
    }

    public ListaDinamica<PeriodoAcademico> getListaPeriodos() {
        ListaPeriodos  = all();
        return ListaPeriodos;
    }

    public void setListaPeriodos(ListaDinamica<PeriodoAcademico> periodos) {
        this.ListaPeriodos = periodos;
    }

    public PeriodoAcademico getPeriodo() {
        if (periodo == null) {
            periodo = new PeriodoAcademico();
        }
        return periodo;
    }

    public void setPeriodo(PeriodoAcademico periodo) {
        this.periodo = periodo;
    }

    
    public Boolean persist(){
        periodo.setIdPeriodoAcademino(all().getLongitud() + 1);
        return Persist(periodo);
    }
    
}
