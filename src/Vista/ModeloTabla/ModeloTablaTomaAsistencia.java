
package Vista.ModeloTabla;

import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Asistencia;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Victor
 */
public class ModeloTablaTomaAsistencia extends AbstractTableModel {
    ListaDinamica<Asistencia> asistenciasTabla;

    public ListaDinamica<Asistencia> getAsistenciasTabla() {
        return asistenciasTabla;
    }

    public void setAsistenciasTabla(ListaDinamica<Asistencia> asistenciasTabla) {
        this.asistenciasTabla = asistenciasTabla;
    }
    
    @Override
    public int getRowCount() {
        return asistenciasTabla.getLongitud();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }
    
//    private Boolean EstadoCuenta(int i) throws ListaVacia {
//        
//        Asistencia p = asistenciasTabla.getInfo(i);
//        
//        switch (p.getEstadoAsistencia().getDescripcion()) {
//            case "Presente":
//                return Boolean.TRUE;
//            case "Ausente":
//                    return Boolean.FALSE;
//            case "Justificado":
//                return Boolean.TRUE;
//                    
//            default:
//                throw new AssertionError();
//        }
//    }
    
    public void agregarCheckBox(int columna, JTable tabla){
        TableColumn tc = tabla.getColumnModel().getColumn(columna);
        tc.setCellEditor(tabla.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(tabla.getDefaultRenderer(Boolean.class));
    }
    
    public boolean Seleccionable(int fila, int columna, JTable tabla){
        return tabla.getValueAt(fila, columna) != null;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Asistencia periodo = asistenciasTabla.getInfo(rowIndex);
            switch (columnIndex) {
                case 0:
                    return (periodo != null) ? periodo.getIdAsistencia(): "";
                case 1:
                    return (periodo != null) ? periodo.getTematicaAsistencia().getFechaTematica(): "";
                case 2:
                    return (periodo != null) ? null: null; 
//                            EstadoCuenta(rowIndex) : Boolean.FALSE;
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
                return "Dia";
            case 2:
                return "Hora";
            default:
                return null;
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 2;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if (columnIndex == 2) {
            try {
                asistenciasTabla.getInfo(rowIndex);
                fireTableCellUpdated(rowIndex, columnIndex);
            } 
            catch (Exception e) {
                
            }
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 2) {
            return java.lang.Boolean.class;
        } 
        else {
            return super.getColumnClass(columnIndex);
        }
    }
    
}
