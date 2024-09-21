
package Vista.ModeloTabla;


import Controlador.TDA.ListaDinamica.Excepcion.ListaVacia;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Asistencia;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Victor
 */
public class ModeloTablaAsistencia extends AbstractTableModel {

    private ListaDinamica<Asistencia> asistenciaTabla;

    public ListaDinamica<Asistencia> getAsistenciaTabla() {
        return asistenciaTabla;
    }

    public void setAsistenciaTabla(ListaDinamica<Asistencia> asistenciaTabla) {
        this.asistenciaTabla = asistenciaTabla;
    }

    @Override
    public int getRowCount() {
        return asistenciaTabla.getLongitud();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }
        
    @Override
    public Object getValueAt(int Fila, int Columna) {

        try {
            Asistencia p = asistenciaTabla.getInfo(Fila);

            switch (Columna) {
                case 0:
                    return (p != null) ? p.getIdAsistencia() : "";
                case 1:
                    return (p != null) ? p.getTematicaAsistencia().getNombreTematica() : "";
                case 2:
                    return (p != null) ? p.getTematicaAsistencia().getFechaTematica() : "";
                case 3:
                    return (p != null) ? p.getEstadoAsistencia(): "";
                case 4:
                    return (p != null) ? p.getObservacion(): "";
                default:
                    return null;
            }
        } 
        catch (ListaVacia | IndexOutOfBoundsException ex) {
        }
        return asistenciaTabla;
    }


    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "#";
            case 1:
                return "Tematica";
            case 2:
                return "Fecha asistencia";
            case 3:
                return "Estado asistencia";
            case 4:
                return "Observacion";
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
