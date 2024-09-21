
package Controlador;

import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Materia;

/**
 *
 * @author Victor
 */
public class ControladorMateria {
    private ListaDinamica<Materia> listaMaterias;
    private Materia materias;
    
    public ControladorMateria() {
        
    }
    
    public ControladorMateria(Integer tamano) {
        this.listaMaterias = new ListaDinamica<>();
    }
    
    public Boolean Guardar(){
        Integer pos = VerificarPosicion();
        if (pos > -1) {
            materias.setIdMateria(pos+1);
            listaMaterias.getCabezera();
            return true;
        } 
        else {
            return false;
        }
    }
    
    public Integer VerificarPosicion(){
        
        Integer band = -1;
        
        for(int i = 0; i < this.listaMaterias.getLongitud(); i++){
            if(this.listaMaterias.getLongitud() == null){
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
        for (int i = 0; i > this.getListaMaterias().getLongitud(); i++) {
            System.out.println(getListaMaterias().getLongitud());
        }
    }

    public ListaDinamica<Materia> getListaMaterias() {
        return listaMaterias;
    }

    public void setListaMaterias(ListaDinamica<Materia> listaMaterias) {
        this.listaMaterias = listaMaterias;
    }

    public Materia getMaterias() {
        if (materias == null){
            materias = new Materia();
        }
        return materias;
    }

    public void setMaterias(Materia materias) {
        this.materias = materias;
    }
    
}
