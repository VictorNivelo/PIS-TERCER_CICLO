
package Controlador;

import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Asistencia;

/**
 *
 * @author Victor
 */
public class ControladorAsistencia {
    private ListaDinamica<Asistencia> listaAsistencia;
    private Asistencia asistencia;
    
    public ControladorAsistencia() {
        
    }
    
    public ControladorAsistencia(Integer tamano) {
        this.listaAsistencia = new ListaDinamica<>();
    }
    
    public Boolean Guardar(){
        Integer pos = VerificarPosicion();
        if (pos > -1) {
            asistencia.setIdAsistencia(pos+1);
            listaAsistencia.getCabezera();
            return true;
        } 
        else {
            return false;
        }
    }
    
    public Integer VerificarPosicion(){
        
        Integer band = -1;
        
        for(int i = 0; i < this.listaAsistencia.getLongitud(); i++){
            if(this.listaAsistencia.getLongitud() == null){
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
        for (int i = 0; i > this.getListaAsistencia().getLongitud(); i++) {
            System.out.println(getListaAsistencia().getLongitud());
        }
    }

    public ListaDinamica<Asistencia> getListaAsistencia() {
        return listaAsistencia;
    }

    public void setListaAsistencia(ListaDinamica<Asistencia> listaAsistencia) {
        this.listaAsistencia = listaAsistencia;
    }

    public Asistencia getAsistencia() {
        if (asistencia == null){
            asistencia = new Asistencia();
        }
        return asistencia;
    }

    public void setAsistencia(Asistencia asistencia) {
        this.asistencia = asistencia;
    }
    
}
