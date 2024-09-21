
package Controlador.Dao.Modelo;

import Controlador.Dao.DaoImplement;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Rol;

/**
 *
 * @author Victor
 */
public class rolDao extends DaoImplement<Rol>{
    private ListaDinamica<Rol> listaRol = new ListaDinamica<>();
    private Rol rol;

    public rolDao() {
        super(Rol.class);
    }
    
    public ListaDinamica<Rol> getListaRol() {
        listaRol = all();
        return listaRol;
    }

    public void setListaRol(ListaDinamica<Rol> listaRol) {
        this.listaRol = listaRol;
    }

    public Rol getRol() {
        if(rol ==null){
            rol = new Rol();
        }
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    public Boolean Persist(){
        rol.setIdRol(all().getLongitud()+1);
        return Persist(rol);
    }
    
//    public static void main(String[] args) {
//        rolDao rc = new rolDao();
//        rc.getRol().setDescripcionRol("Es un Administrativo");
//        rc.getRol().setNombreRol("Personal Administrativo");
//        rc.Persist();
//        rc.setRol(null);
//        
//        rc.getRol().setDescripcionRol("Es un Docente");
//        rc.getRol().setNombreRol("Docente");
//        rc.Persist();
//        rc.setRol(null);
//        
//        rc.getRol().setDescripcionRol("Es un Estudiante");
//        rc.getRol().setNombreRol("Estudiante");
//        rc.Persist();
//        rc.setRol(null);
//
//    }

}
