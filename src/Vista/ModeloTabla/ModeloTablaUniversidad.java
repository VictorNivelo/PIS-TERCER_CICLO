
package Vista.ModeloTabla;

import Controlador.TDA.ListaDinamica.Excepcion.ListaVacia;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Universidad;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Victor
 */
public class ModeloTablaUniversidad extends AbstractTableModel {

    private ListaDinamica<Universidad> universidadTabla;

    public ListaDinamica<Universidad> getUniversidadTabla() {
        return universidadTabla;
    }

    public void setUniversidadTabla(ListaDinamica<Universidad> universidadTabla) {
        this.universidadTabla = universidadTabla;
    }
    
    @Override
    public int getRowCount() {
        return universidadTabla.getLongitud();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }
    
    @Override
    public Object getValueAt(int Fila, int Columna) {

        try {
            Universidad u = universidadTabla.getInfo(Fila);

            switch (Columna) {
                case 0:
                    return (u != null) ? u.getIdU(): "";
                case 1:
                    return (u != null) ? u.getNombreU() : "";
                case 2:
                    return (u != null) ? u.getDireccionU(): "";
                case 3:
                    return (u != null) ? u.getTelefonoU(): "";
                case 4:
                    return (u != null) ? u.getCorreoU(): "";
                case 5:
                    return (u != null) ? u.getFechaFU(): "";
                
                default:
                    return null;
            }
        } 
        catch (ListaVacia | IndexOutOfBoundsException ex) {
        }
        return universidadTabla;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "#";
            case 1:
                return "Nombre";
            case 2:
                return "Direccion";
            case 3:
                return "Telefono";
            case 4:
                return "Correo";
            case 5:
                return "Fundacion";
            
            default:
                return null;
        }
    }
    
    public double sumarColumna(int columna) {
        double suma = 0.0;

        for (int fila = 0; fila < getRowCount(); fila++) {
            try {
                Object valor = getValueAt(fila, columna);

                if (valor instanceof Number) {
                    suma += ((Number) valor).doubleValue();
                }
            } 
            catch (Exception e) {
            }
        }

        return suma;
    }
    
}
