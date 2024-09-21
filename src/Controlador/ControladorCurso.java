
package Controlador;

import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Cursa;

/**
 *
 * @author Victor
 */
public class ControladorCurso {
    private ListaDinamica<Cursa> listaCursos;
    private Cursa cursosControl;
    
    public ControladorCurso() {
        
    }
    
    public ControladorCurso(Integer tamano) {
        this.listaCursos = new ListaDinamica<>();
    }
    
    public Boolean Guardar(){
        Integer pos = VerificarPosicion();
        if (pos > -1) {
            cursosControl.setIdCurso(pos+1);
            listaCursos.getCabezera();
            return true;
        } 
        else {
            return false;
        }
    }
    
    public Integer VerificarPosicion(){
        
        Integer band = -1;
        
        for(int i = 0; i < this.listaCursos.getLongitud(); i++){
            if(this.listaCursos.getLongitud() == null){
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
        for (int i = 0; i > this.getListaCursos().getLongitud(); i++) {
            System.out.println(getListaCursos().getLongitud());
        }
    }

    public ListaDinamica<Cursa> getListaCursos() {
        
        return listaCursos;
    }

    public void setListaCursos(ListaDinamica<Cursa> listaCursos) {
        this.listaCursos = listaCursos;
    }

    public Cursa getCursosControl() {
        if (cursosControl == null){
            cursosControl = new Cursa();
        }
        return cursosControl;
    }

    public void setCursosControl(Cursa cursosControl) {
        this.cursosControl = cursosControl;
    }
    
}
