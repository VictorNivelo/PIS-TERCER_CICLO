
package Vista.ModeloTabla;

import Controlador.TDA.ListaDinamica.Excepcion.ListaVacia;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Facultad;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Victor
 */
public class ModeloTablaFacultad extends AbstractTableModel {

    private ListaDinamica<Facultad> facultadTabla;

    public ListaDinamica<Facultad> getFacultadTabla() {
        return facultadTabla;
    }

    public void setFacultadTabla(ListaDinamica<Facultad> facultadTabla) {
        this.facultadTabla = facultadTabla;
    }
    
    @Override
    public int getRowCount() {
        return facultadTabla.getLongitud();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }
    
    @Override
    public Object getValueAt(int Fila, int Columna) {

        try {
            Facultad f = facultadTabla.getInfo(Fila);

            switch (Columna) {
                case 0:
                    return (f != null) ? f.getIdFacultad(): "";
                case 1:
                    return (f != null) ? f.getNombreFacultad(): "";
                case 2:
                    return (f != null) ? f.getFechaCreacion(): "";
                case 3:
                    return (f != null) ? f.getUniversidadFacultad().getNombreU(): "";
                default:
                    return null;
            }
        }
        catch (ListaVacia | IndexOutOfBoundsException ex) {
            
        }
        return facultadTabla;
    }


    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "#";
            case 1:
                return "Nombre";
            case 2:
                return "Fecha creacion";
            case 3:
                return "Universidad";
            default:
                return null;
        }
    }
    
}
