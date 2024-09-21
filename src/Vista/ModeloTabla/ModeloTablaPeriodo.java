
package Vista.ModeloTabla;

import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.PeriodoAcademico;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Victor
 */
public class ModeloTablaPeriodo extends AbstractTableModel {
    ListaDinamica<PeriodoAcademico> periodosTabla;

    public ListaDinamica<PeriodoAcademico> getPeriodosTabla() {
        return periodosTabla;
    }

    public void setPeriodosTabla(ListaDinamica<PeriodoAcademico> periodosTabla) {
        this.periodosTabla = periodosTabla;
    }
    
    @Override
    public int getRowCount() {
        return periodosTabla.getLongitud();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            PeriodoAcademico periodo = periodosTabla.getInfo(rowIndex);
            switch (columnIndex) {
                case 0:
                    return (periodo != null) ? periodo.getIdPeriodoAcademino() : "";
                case 1:
                    return (periodo != null) ? periodo.getFechaInicio() : "";
                case 2:
                    return (periodo != null) ? periodo.getFechaFin() : "";
                case 3:
                    return (periodo != null) ? periodo.getEstadoPeriodoAcedemico(): "";
                default:
                    return null;
            }
        } 
        catch (Exception e) {
            return null;
        }
        
    }
    
    @Override
    public String getColumnName(int column){
        switch (column) {
            case 0:
                return "#";
            case 1:
                return "Fecha inicio";
            case 2:
                return "Fecha fin";
            case 3:
                return "Estado";
            default:
                return null;
        }
    }
    
}
