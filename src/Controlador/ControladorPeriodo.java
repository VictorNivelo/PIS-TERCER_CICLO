
package Controlador;

import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.PeriodoAcademico;

/**
 *
 * @author romer
 */
public class ControladorPeriodo {
    private ListaDinamica<PeriodoAcademico> ListaPeriodos;
    private PeriodoAcademico periodoAcademico;
    
    public ControladorPeriodo(){
        ListaPeriodos = new ListaDinamica<>();
    }
    
    public Boolean guardarPeriodo(){
        getPeriodoAcademico().setIdPeriodoAcademino(getListaPeriodos().getLongitud());
        getListaPeriodos().Agregar(getPeriodoAcademico());
        return true;
    }

    public ListaDinamica<PeriodoAcademico> getListaPeriodos() {
        return ListaPeriodos;
    }

    public void setListaPeriodos(ListaDinamica<PeriodoAcademico> periodos) {
        this.ListaPeriodos = periodos;
    }

    public PeriodoAcademico getPeriodoAcademico() {
        if (periodoAcademico == null) {
            periodoAcademico = new PeriodoAcademico();
        }
        return periodoAcademico;
    }

    public void setPeriodoAcademico(PeriodoAcademico periodoAcademico) {
        this.periodoAcademico = periodoAcademico;
    }
        
}
