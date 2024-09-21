
package Controlador;

import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Ciclo;

/**
 *
 * @author Victor
 */
public class ControladorCiclo {
    private ListaDinamica<Ciclo> listaCiclos;
    private Ciclo ciclos;
    
    public ControladorCiclo() {

    }
    
    public ControladorCiclo(Integer tamano) {
        this.listaCiclos = new ListaDinamica<>();
    }
    
    public Boolean Guardar(){
        Integer pos = VerificarPosicion();
        if (pos > -1) {
            ciclos.setIdCiclo(pos+1);
            listaCiclos.getCabezera();
            return true;
        } 
        else {
            return false;
        }
    }
    
    public Integer VerificarPosicion(){
        
        Integer band = -1;
        
        for(int i = 0; i < this.listaCiclos.getLongitud(); i++){
            if(this.listaCiclos.getLongitud() == null){
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
        for (int i = 0; i > this.getListaCiclos().getLongitud(); i++) {
            System.out.println(getListaCiclos().getLongitud());
        }
    }

    public ListaDinamica<Ciclo> getListaCiclos() {
        return listaCiclos;
    }

    public void setListaCiclos(ListaDinamica<Ciclo> listaCiclos) {
        this.listaCiclos = listaCiclos;
    }

    public Ciclo getCiclos() {
        if (ciclos == null){
            ciclos = new Ciclo();
        }
        return ciclos;
    }

    public void setCiclos(Ciclo ciclos) {
        this.ciclos = ciclos;
    }

}
