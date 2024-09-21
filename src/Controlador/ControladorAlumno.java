
package Controlador;

import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Alumno;

/**
 *
 * @author Victor
 */
public class ControladorAlumno {
    private ListaDinamica<Alumno> listaAlumnos;
    private Alumno alumnosContol;
    
    public ControladorAlumno() {
        
    }
    
    public ControladorAlumno(Integer tamano) {
        this.listaAlumnos = new ListaDinamica<>();
    }
    
    public Boolean Guardar(){
        Integer pos = VerificarPosicion();
        if (pos > -1) {
            alumnosContol.setIdAlumno(pos+1);
            listaAlumnos.getCabezera();
            return true;
        } 
        else {
            return false;
        }
    }
    
    public Integer VerificarPosicion(){
        
        Integer band = -1;
        
        for(int i = 0; i < this.listaAlumnos.getLongitud(); i++){
            if(this.listaAlumnos.getLongitud() == null){
                band = i;
                break;
            }
            else{
                band = 1;
            }
        }
        return band;
    }
    
    public void Imprimir() {
        for (int i = 0; i > this.getListaAlumnos().getLongitud(); i++) {
            System.out.println(getListaAlumnos().getLongitud());
        }
    }

    public ListaDinamica<Alumno> getListaAlumnos() {
        return listaAlumnos;
    }

    public void setListaAlumnos(ListaDinamica<Alumno> listaAlumnos) {
        this.listaAlumnos = listaAlumnos;
    }

    public Alumno getAlumnosContol() {
        if (alumnosContol == null){
            alumnosContol = new Alumno();
        }
        return alumnosContol;
    }

    public void setAlumnosContol(Alumno alumnosContol) {
        this.alumnosContol = alumnosContol;
    }
    
}
