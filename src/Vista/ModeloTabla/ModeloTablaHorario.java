
package Vista.ModeloTabla;

import Controlador.TDA.ListaDinamica.Excepcion.ListaVacia;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Horario;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Victor
 */
public class ModeloTablaHorario extends AbstractTableModel {

    private ListaDinamica<Horario> horarioTabla;

    public ListaDinamica<Horario> getHorarioTabla() {
        return horarioTabla;
    }

    public void setHorarioTabla(ListaDinamica<Horario> horarioTabla) {
        this.horarioTabla = horarioTabla;
    }     
    
    @Override
    public int getRowCount() {
        return horarioTabla.getLongitud();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }
    
    @Override
    public Object getValueAt(int Fila, int Columna) {

        try {
            Horario p = horarioTabla.getInfo(Fila);

            switch (Columna) {
                case 0:
                    return (p != null) ? p.getIdHorario(): "";
                case 1:
                    return (p != null) ? p.getCodigoHorario(): "";
                case 2:
                    return (p != null) ? p.getDiaSemana(): "";
                case 3:
                    return (p != null) ? p.getHoraIncio() : "";
                case 4:
                    return (p != null) ? p.getHoraFin() : "";
                case 5:
                    return (p != null) ? p.getMateriaHorario().getNombreMateria(): "";
                default:
                    return null;
            }
        }
        catch (ListaVacia | IndexOutOfBoundsException ex) {
            
        }
        return horarioTabla;
    }


    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "#";
            case 1:
                return "Codigo";
            case 2:
                return "Dia";
            case 3:
                return "Inicio";
            case 4:
                return "Fin";
            case 5:
                return "Materia";
            default:
                return null;
        }
    }
    
}
