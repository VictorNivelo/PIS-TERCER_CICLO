
package Vista.ModeloTabla;

import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Paralelo;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Victor
 */
public class ModeloTablaParalelo extends AbstractTableModel {
    ListaDinamica<Paralelo> paraleloTabla;

    public ListaDinamica<Paralelo> getParaleloTabla() {
        return paraleloTabla;
    }

    public void setParaleloTabla(ListaDinamica<Paralelo> paraleloTabla) {
        this.paraleloTabla = paraleloTabla;
    }
    
    @Override
    public int getRowCount() {
        return paraleloTabla.getLongitud();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Paralelo periodo = paraleloTabla.getInfo(rowIndex);
            switch (columnIndex) {
                case 0:
                    return (periodo != null) ? periodo.getIdParalelo(): "";
                case 1:
                    return (periodo != null) ? periodo.getCodigoParalelo(): "";
                case 2:
                    return (periodo != null) ? periodo.getNombre(): "";
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
                return "Codigo";
            case 2:
                return "Nombre";

            default:
                return null;
        }
    }
}
