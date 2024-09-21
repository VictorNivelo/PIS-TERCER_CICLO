
package Controlador;

import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Rol;

/**
 *
 * @author Victor
 */
public class ControladorRol {
    private ListaDinamica<Rol> roles;
    
//    public ControladorRol() {
//        roles = new ListaDinamica<>();
//        roles.Agregar(new Rol(1, "Administrador", "Es un admin"));
//        roles.Agregar(new Rol(2, "Personal administrativo", "Es un empleado administrativo"));
//        roles.Agregar(new Rol(3, "Docente", "Es un docente"));
//        roles.Agregar(new Rol(4, "Estrudiante", "Es un estudiante"));
//    }

    public ListaDinamica<Rol> getRoles() {
        return roles;
    }

    public void setRoles(ListaDinamica<Rol> roles) {
        this.roles = roles;
    }
    
}
