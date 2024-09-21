
package Vista.ModeloTabla;

import Controlador.TDA.ListaDinamica.Excepcion.ListaVacia;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Ciclo;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Victor
 */
public class ModeloTablaCiclos extends AbstractTableModel {

    private ListaDinamica<Ciclo> cicloTabla;

    public ListaDinamica<Ciclo> getCicloTabla() {
        return cicloTabla;
    }

    public void setCicloTabla(ListaDinamica<Ciclo> cicloTabla) {
        this.cicloTabla = cicloTabla;
    }
    
    @Override
    public int getRowCount() {
        return cicloTabla.getLongitud();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }
    
    @Override
    public Object getValueAt(int Fila, int Columna) {

        try {
            Ciclo p = cicloTabla.getInfo(Fila);

            switch (Columna) {
                case 0:
                    return (p != null) ? p.getIdCiclo(): "";
                case 1:
                    return (p != null) ? p.getNombreCiclo(): "";
                case 2:
                    return (p != null) ? p.getNumeroCiclo(): "";
                case 3:
                    return (p != null) ? p.getUnidadCurricularCiclo().getNombreUnidadCurricular(): "";
                default:
                    return null;
            }
        } 
        catch (ListaVacia | IndexOutOfBoundsException ex) {
            
        }
        return cicloTabla;
    }


    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "#";
            case 1:
                return "Nombre ciclo";
            case 2:
                return "Numero de ciclo";
            case 3:
                return "Unidad";
            default:
                return null;
        }
    }
    
}
