
package Vista.ModeloTabla;

import Controlador.TDA.ListaDinamica.Excepcion.ListaVacia;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Presentacion;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Victor
 */
public class ModeloTablaPresentacion extends AbstractTableModel {

    private ListaDinamica<Presentacion> presentacionTabla;

    public ListaDinamica<Presentacion> getPresentacionTabla() {
        return presentacionTabla;
    }

    public void setPresentacionTabla(ListaDinamica<Presentacion> presentacionTabla) {
        this.presentacionTabla = presentacionTabla;
    }
    
    @Override
    public int getRowCount() {
        return presentacionTabla.getLongitud();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }
    
    @Override
    public Object getValueAt(int Fila, int Columna) {

        try {
            Presentacion p = presentacionTabla.getInfo(Fila);

            switch (Columna) {
                case 0:
                    return (p != null) ? p.getIdPresentacion() : "";
                case 1:
                    return (p != null) ? p.getCodigo() : "";
                case 2:
                    return (p != null) ? p.getImagen() : "";
                case 3:
                    return (p != null) ? p.getTiempo() : "";
                case 4:
                    return (p != null) ? p.getTitulo(): "";
                case 5:
                    return (p != null) ? p.getContenido() : "";
                case 6:
                    return (p != null) ? p.getEstadoPresentacion() : "";
                default:
                    return null;
            }
        } 
        catch (ListaVacia | IndexOutOfBoundsException ex) {
        }
        return presentacionTabla;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "#";
            case 1:
                return "Codigo";
            case 2:
                return "Imagen";
            case 3:
                return "Tiempo";
            case 4:
                return "Titulo";
            case 5:
                return "Contenido";
            case 6:
                return "Estado";
            default:
                return null;
        }
    }
    
}
