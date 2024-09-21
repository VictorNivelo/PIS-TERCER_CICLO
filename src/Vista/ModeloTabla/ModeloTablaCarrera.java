
package Vista.ModeloTabla;

import Controlador.TDA.ListaDinamica.Excepcion.ListaVacia;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Carrera;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Victor
 */
public class ModeloTablaCarrera extends AbstractTableModel {

    private ListaDinamica<Carrera> carreraTabla;

    public ListaDinamica<Carrera> getCarreraTabla() {
        return carreraTabla;
    }

    public void setCarreraTabla(ListaDinamica<Carrera> carreraTabla) {
        this.carreraTabla = carreraTabla;
    }
    
    @Override
    public int getRowCount() {
        return carreraTabla.getLongitud();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }
    
    @Override
    public Object getValueAt(int Fila, int Columna) {

        try {
            Carrera p = carreraTabla.getInfo(Fila);

            switch (Columna) {
                case 0:
                    return (p != null) ? p.getIdCarrera(): "";
                case 1:
                    return (p != null) ? p.getNombreCarrera(): "";
                case 2:
                    return (p != null) ? p.getDuracion() + " Años": "";
                case 3:
                    return (p != null) ? p.getNumeroCiclos()+ " Ciclos": "";
                case 4:
                    return (p != null) ? p.getFacutadCarrera().getNombreFacultad(): "";
                default:
                    return null;
            }
        } 
        catch (ListaVacia | IndexOutOfBoundsException ex) {
            
        }
        return carreraTabla;
    }


    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "#";
            case 1:
                return "Nombre carrera";
            case 2:
                return "Duracion";
            case 3:
                return "Numero de ciclos";
            case 4:
                return "Facultad";
            default:
                return null;
        }
    }
    
}
