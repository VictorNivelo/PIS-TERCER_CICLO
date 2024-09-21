
package Controlador;

import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Persona;

/**
 *
 * @author Victor
 */
public class ControladorPersona {
    private ListaDinamica<Persona> listaPersonas;
    private Persona personas;

    public ControladorPersona() {
        listaPersonas = new ListaDinamica<>();
    }
    
    public Boolean guardarMatricula(){
        getPersonas().setIdPersona(getListaPersonas().getLongitud());
        getListaPersonas().Agregar(getPersonas());
        return true;
    }

    public ListaDinamica<Persona> getListaPersonas() {
        return listaPersonas;
    }

    public void setListaPersonas(ListaDinamica<Persona> listaPersonas) {
        this.listaPersonas = listaPersonas;
    }

    public Persona getPersonas() {
        if(personas == null){
            personas = new Persona();
        }
        return personas;
    }

    public void setPersonas(Persona personas) {
        this.personas = personas;
    }
    
}
