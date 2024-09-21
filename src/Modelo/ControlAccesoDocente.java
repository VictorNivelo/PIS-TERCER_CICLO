
package Modelo;

/**
 *
 * @author Victor
 */
public class ControlAccesoDocente {
    
    private static Docente docenteLogeado;
    private static String nombreDocenteLogeado;
    
    public static void setNombreDocenteLogeado(String nombre) {
        nombreDocenteLogeado = nombre;
    }

    public static String getNombreDocenteLogeado() {
        return nombreDocenteLogeado;
    }

    public static Docente getDocenteLogeado() {
        return docenteLogeado;
    }

    public static void setDocenteLogeado(Docente docente) {
        docenteLogeado = docente;
    }
    
    public static Integer getIdDocenteLogeado() {
        if (docenteLogeado != null) {
            return docenteLogeado.getIdDocente();
        } 
        else {
            return null;
        }
    }
}
