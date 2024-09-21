package Controlador.Dao.Modelo;

import Controlador.Dao.DaoImplement;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Alumno;
import Modelo.Cuenta;

/**
 *
 * @author Victor
 */
public class alumnoDao extends DaoImplement<Alumno> {

    private ListaDinamica<Alumno> listaAlumnos = new ListaDinamica<>();
    private Alumno alumnos;

    public alumnoDao() {
        super(Alumno.class);
    }

    public ListaDinamica<Alumno> getListaAlumnos() {
        listaAlumnos = all();
        return listaAlumnos;
    }

    public void setListaAlumnos(ListaDinamica<Alumno> listaAlumnos) {
        this.listaAlumnos = listaAlumnos;
    }

    public Alumno getAlumnos() {
        if (alumnos == null) {
            alumnos = new Alumno();
        }
        return alumnos;
    }

    public void setAlumnos(Alumno alumnos) {
        this.alumnos = alumnos;
    }

    
    public Boolean Persist(){
        alumnos.setIdAlumno(all().getLongitud()+1);
        return Persist(alumnos);
    }

    //METODO NUEVO AGREGAR
    public Alumno obtenerAlumnoPorUsuario(String usuario) {
        ListaDinamica<Alumno> listaAlumnos = all();

        for (Alumno alumno : listaAlumnos.toArray()) {
            Cuenta cuenta = alumno.getDatosAlumno().getCuentaPersona();
            if (cuenta != null && cuenta.getCorreo().equals(usuario)) {
                return alumno;
            }
        }

        return null;
    }
}
