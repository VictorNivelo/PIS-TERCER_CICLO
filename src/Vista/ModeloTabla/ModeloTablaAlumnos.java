
package Vista.ModeloTabla;

import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Alumno;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author romer
 */
public class ModeloTablaAlumnos extends AbstractTableModel {
    ListaDinamica<Alumno> alumnosTabla;

    public ListaDinamica<Alumno> getAlumnosTabla() {
        return alumnosTabla;
    }

    public void setAlumnosTabla(ListaDinamica<Alumno> alumnosTabla) {
        this.alumnosTabla = alumnosTabla;
    }

    @Override
    public int getRowCount() {
        return alumnosTabla.getLongitud();
    }

    @Override
    public int getColumnCount() {
        return 9;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Alumno alumno = alumnosTabla.getInfo(rowIndex);
            switch (columnIndex) {
                case 0:
                    return (alumno != null) ? alumno.getIdAlumno() : "";
                case 1:
                    return (alumno != null) ? alumno.getDatosAlumno().getNombre() : " ";
                case 2:
                    return (alumno != null) ? alumno.getDatosAlumno().getApellido() : "";
                case 3:
                    return (alumno != null) ? alumno.getDatosAlumno().getNumeroCedula() : "";
                case 4:
                    return (alumno != null) ? alumno.getDatosAlumno().getGenero() : "";
                case 5:
                    return (alumno != null) ? alumno.getDatosAlumno().getDireccion() : "";
                case 6:
                    return (alumno != null) ? alumno.getDatosAlumno().getTelefono() : "";
                case 7:
                    return (alumno != null) ? alumno.getDatosAlumno().getCuentaPersona().getCorreo(): "";
                case 8:
                    return (alumno != null) ? alumno.getEstadoAlumno(): "";
                default:
                    return null;
            }
        }
        catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "#";
            case 1:
                return "Nombre";
            case 2:
                return "Apellido";
            case 3:
                return "Numero DNI";
            case 4:
                return "Genero";
            case 5:
                return "Direccion";
            case 6:
                return "Telefono";
            case 7:
                return "Correo";
            case 8:
                return "Estado";

            default:
                return null;
        }
    }
    
}
