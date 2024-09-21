
package Vista.ModeloTabla;

import Controlador.TDA.ListaDinamica.Excepcion.ListaVacia;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.UnidadCurricular;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Victor
 */
public class ModeloTablaUnidadCurricular extends AbstractTableModel {

    private ListaDinamica<UnidadCurricular> unidadCurricularTabla;

    public ListaDinamica<UnidadCurricular> getUnidadCurricularTabla() {
        return unidadCurricularTabla;
    }

    public void setUnidadCurricularTabla(ListaDinamica<UnidadCurricular> unidadCurricularTabla) {
        this.unidadCurricularTabla = unidadCurricularTabla;
    }
    
    @Override
    public int getRowCount() {
        return unidadCurricularTabla.getLongitud();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }
    
    @Override
    public Object getValueAt(int Fila, int Columna) {

        try {
            UnidadCurricular u = unidadCurricularTabla.getInfo(Fila);

            switch (Columna) {
                case 0:
                    return (u != null) ? u.getIdUnidadCurricular(): "";
                case 1:
                    return (u != null) ? u.getCodigoUnidadCurricular(): "";
                case 2:
                    return (u != null) ? u.getNombreUnidadCurricular(): "";
                case 3:
                    return (u != null) ? u.getDescripcionUnidadCurricular(): "";
                case 4:
                    return (u != null) ? u.getMallaCurricularUnidadCurricular().getNombreMallaCurricular(): "";
                
                default:
                    return null;
            }
        } 
        catch (ListaVacia | IndexOutOfBoundsException ex) {
        }
        return unidadCurricularTabla;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "#";
            case 1:
                return "Codigo";
            case 2:
                return "Nombre";
            case 3:
                return "Descripcion";
            case 4:
                return "Malla";
            
            default:
                return null;
        }
    }
}
