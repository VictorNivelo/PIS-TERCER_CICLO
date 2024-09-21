
package Vista.ModeloTabla;

import Controlador.TDA.ListaDinamica.Excepcion.ListaVacia;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Cursa;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Victor
 */
public class ModeloTablaCurso extends AbstractTableModel {

    private ListaDinamica<Cursa> cursoTabla;

    public ListaDinamica<Cursa> getCursoTabla() {
        return cursoTabla;
    }

    public void setCursoTabla(ListaDinamica<Cursa> cursoTabla) {
        this.cursoTabla = cursoTabla;
    }
       
    @Override
    public int getRowCount() {
        return cursoTabla.getLongitud();
    }

    @Override
    public int getColumnCount() {
        return 10;
    }
    
//    private String EstadoCuenta(int i) throws ListaVacia {
//        
//        Cursa p = personasTabla.getInfo(i);
//        
//        if (p.get().getEstadoCuenta()) {
//            return "Activa";
//        } 
//        else {
//            return "Inactiva";
//        }
//    }
//    
    
    @Override
    public Object getValueAt(int Fila, int Columna) {

        try {
            Cursa p = cursoTabla.getInfo(Fila);

            switch (Columna) {
                case 0:
                    return (p != null) ? p.getIdCurso() : "";
                case 1:
                    return (p != null) ? p.getCodigoCursa(): "";
                case 2:
                    return (p != null) ? p.getParaleloCursa().getNombre() : "";
                case 3:
                    return (p != null) ? p.getMatriculaCursa().getAlumnoMatricula().getDatosAlumno().getNumeroCedula(): "";
                case 4:
                    return (p != null) ? p.getMatriculaCursa().getAlumnoMatricula().getDatosAlumno().getNombre() + " " + p.getMatriculaCursa().getAlumnoMatricula().getDatosAlumno().getApellido(): "";
                case 5:
                    return (p != null) ? p.getMatriculaCursa().getCodigoMatricula(): "";
                case 6:
                    return (p != null) ? p.getDocenteCursa().getDatosDocente().getNumeroCedula(): "";
                case 7:
                    return (p != null) ? p.getDocenteCursa().getDatosDocente().getNombre()+" "+ p.getDocenteCursa().getDatosDocente().getApellido(): "";
                case 8:
                    return (p != null) ? p.getMateriaCursa().getNombreMateria(): "";
                case 9:
                    return (p != null) ? p.getMateriaCursa().getCicloMateria().getNombreCiclo(): "";
                default:
                    return null;
            }
        } 
        catch (ListaVacia | IndexOutOfBoundsException ex) {

        }
        return cursoTabla;


//        try {
//            Cursa p = cursoTabla.getInfo(Fila);
//
//            switch (Columna) {
//                case 0:
//                    return (p != null) ? p.getIdCurso() : "";
//                case 1:
//                    return (p != null) ? p.getParaleloCursa().getNombre(): "";
//                case 2:
//                    return (p != null) ? p.getListaMatriculaCursa().getInfo(Fila).getAlumnoMatricula().getDatosAlumno().getNumeroCedula(): "";
//                case 3:
//                    return (p != null) ? p.getListaMatriculaCursa().getInfo(Fila).getEstadoMatricula(): "";
//                case 4:
//                    return (p != null) ? p.getListaMatriculaCursa().getInfo(Fila).getCodigoMatricula(): "";
//                case 5:
//                    return (p != null) ? p.getCodigoCursoCursa().getNombreCodigoCurso(): "";
//                default:
//                    return null;
//            }
//        } 
//        catch (ListaVacia | IndexOutOfBoundsException ex) {
//        }
//        return cursoTabla;

/*Primero si sirve*/
//            Cursa p = cursoTabla.getInfo(Fila);
//
//            switch (Columna) {
//                case 0:
//                    return (p != null) ? p.getIdCurso() : "";
//                case 1:
//                    return (p != null) ? p.getParaleloCursa().getNombre(): "";
//                case 2:
//                    return (p != null) ? p.getMatriculaCursa().getAlumnoMatricula().getDatosAlumno().getNumeroCedula(): "";
//                case 3:
//                    return (p != null) ? p.getMatriculaCursa().getEstadoMatricula(): "";
//                case 4:
//                    return (p != null) ? p.getMatriculaCursa().getCodigoMatricula(): "";
//                case 5:
//                    return (p != null) ? p.getCodigoCursoCursa().getNombreCodigoCurso(): "";
//                default:
//                    return null;
//            }
//        } 
//        catch (ListaVacia | IndexOutOfBoundsException ex) {
//        }
//        return cursoTabla;
    }


    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "#";
            case 1:
                return "Codigo";
            case 2:
                return "Paralelo";
            case 3:
                return "DNI estudiante";
            case 4:
                return "Nombres estudiante";
            case 5:
                return "Codigo matricula";
            case 6:
                return "DNI docente";
            case 7:
                return "Nombres docente";
            case 8:
                return "Materia";
            case 9:
                return "Ciclo";

            default:
                return null;
        }
    }
    
}
