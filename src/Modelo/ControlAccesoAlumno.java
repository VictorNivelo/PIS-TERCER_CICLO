
package Modelo;

/**
 *
 * @author Victor
 */
public class ControlAccesoAlumno {
    private static Alumno alumnoLogeado;
    private static String nombreAlumnoLogeado;
    
    public static void setNombreAlumnoLogeado(String nombre) {
        nombreAlumnoLogeado = nombre;
    }

    public static String getNombreAlumnoLogeado() {
        return nombreAlumnoLogeado;
    }

    public static Alumno getAlumnoLogeado() {
        return alumnoLogeado;
    }

    public static void setAlumnoLogeado(Alumno docente) {
        alumnoLogeado = docente;
    }

    public static Integer getIdAlumnoLogeado() {
        if (alumnoLogeado != null) {
            return alumnoLogeado.getIdAlumno();
        } 
        else {
            return null;
        }
    }
}
