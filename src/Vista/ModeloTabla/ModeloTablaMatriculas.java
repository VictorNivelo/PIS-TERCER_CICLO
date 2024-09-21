
package Vista.ModeloTabla;

import Controlador.TDA.ListaDinamica.Excepcion.ListaVacia;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Matricula;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author romer
 */
public class ModeloTablaMatriculas extends AbstractTableModel{
    private ListaDinamica<Matricula> matriculas;

    public ListaDinamica<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(ListaDinamica<Matricula> matriculas) {
        this.matriculas = matriculas;
    }

    @Override
    public int getRowCount() {
        return matriculas.getLongitud();
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Matricula m = matriculas.getInfo(rowIndex);
            switch (columnIndex) {
                case 0:
                    return (m != null) ? m.getIdMatricula() : "";
                case 1:
                    return (m != null) ? m.getCodigoMatricula() : "";
                case 2:
                    return (m != null) ? m.getFechaMatricula() : "";
                case 3:
                    return (m != null) ? m.getEstadoMatricula() : "";
                case 4:
                    return (m != null) ? m.getPeriodoAcademicoMatricula().getFechaInicio() : "";
                case 5:
                    return (m != null) ? m.getPeriodoAcademicoMatricula().getFechaFin() : "";
                case 6:
                    return (m != null) ? m.getAlumnoMatricula().getDatosAlumno().getNumeroCedula() : "";
                case 7:
                    return (m != null) ? m.getAlumnoMatricula().getDatosAlumno().getNombre() + " "+m.getAlumnoMatricula().getDatosAlumno().getApellido(): "";
                default:
                    return null;
            }
        }
        catch (ListaVacia ex) {
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
                return "Fecha";
            case 3:
                return "Estado";
            case 4:
                return "Inicio";
            case 5:
                return "Fin";
            case 6:
                return "DNI Alumno";
            case 7:
                return "Nombre";
            default:
                return null;
        }
    }
    
}
