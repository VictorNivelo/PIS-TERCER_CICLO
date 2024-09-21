
package Controlador.Dao.Modelo;

import Controlador.Dao.DaoImplement;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Persona;

/**
 *
 * @author Victor
 */
public class personaDao extends DaoImplement<Persona>{
    private ListaDinamica<Persona> ListaPersonas = new ListaDinamica<>();
    private Persona persona;
    
    public personaDao(){
        super (Persona.class);
    }
    
    public ListaDinamica<Persona> getListaPersonas() {
        ListaPersonas = all();
        return ListaPersonas;
    }

    public void setListaPersonas(ListaDinamica<Persona> ListaPersonas) {
        this.ListaPersonas = ListaPersonas;
    }

    public Persona getPersona() {
        if(persona == null){
            persona = new Persona();
        }
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
    
    public Boolean Persist(){
        persona.setIdPersona(all().getLongitud()+1);
        return Persist(persona);
    }
    
}
