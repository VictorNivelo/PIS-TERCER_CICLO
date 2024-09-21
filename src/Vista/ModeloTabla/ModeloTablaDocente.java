
package Vista.ModeloTabla;

import Controlador.TDA.ListaDinamica.Excepcion.ListaVacia;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Docente;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Victor
 */
public class ModeloTablaDocente extends AbstractTableModel {

    private ListaDinamica<Docente> docenteTabla;

    public ListaDinamica<Docente> getDocenteTabla() {
        return docenteTabla;
    }

    public void setDocenteTabla(ListaDinamica<Docente> docenteTabla) {
        this.docenteTabla = docenteTabla;
    }
       
    @Override
    public int getRowCount() {
        return docenteTabla.getLongitud();
    }

    @Override
    public int getColumnCount() {
        return 8;
    }
    
    @Override
    public Object getValueAt(int Fila, int Columna) {

        try {
            Docente d = docenteTabla.getInfo(Fila);

            switch (Columna) {
                case 0:
                    return (d != null) ? d.getIdDocente() : "";
                case 1:
                    return (d != null) ? d.getDatosDocente().getNumeroCedula() : "";
                case 2:
                    return (d != null) ? d.getDatosDocente().getNombre() : "";
                case 3:
                    return (d != null) ? d.getDatosDocente().getApellido() : "";
                case 4:
                    return (d != null) ? d.getDatosDocente().getGenero() : "";
                case 5:
                    return (d != null) ? d.getEspecialidad() : "";
                case 6:
                    return (d != null) ? d.getTitulacion() : "";
                case 7:
                    return (d != null) ? d.getAniosExperiencia() : "";

                default:
                    return null;
            }
        } 
        catch (ListaVacia | IndexOutOfBoundsException ex) {
            
        }
        return docenteTabla;
    }


    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "#";
            case 1:
                return "DNI";
            case 2:
                return "Nombre";
            case 3:
                return "Apellido";
            case 4:
                return "Genero";
            case 5:
                return "Especialidad";
            case 6:
                return "Titilacion";
            case 7:
                return "Experiencia";

            default:
                return null;
        }
    }
    
}
