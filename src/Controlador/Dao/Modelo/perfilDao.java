
package Controlador.Dao.Modelo;

import Controlador.Dao.DaoImplement;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Perfil;

/**
 *
 * @author Victor
 */
public class perfilDao extends DaoImplement<Perfil>{
    private ListaDinamica<Perfil> ListaPerfil = new ListaDinamica<>();
    private Perfil perfil;
    
    public perfilDao(){
        super (Perfil.class);
    }

    public ListaDinamica<Perfil> getListaPerfil() {
        ListaPerfil = all();
        return ListaPerfil;
    }

    public void setListaPerfil(ListaDinamica<Perfil> ListaPerfil) {
        this.ListaPerfil = ListaPerfil;
    }

    public Perfil getPerfil() {
        if(perfil == null){
            perfil = new Perfil();
        }
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Boolean Persist(){
        perfil.setIdPerfil(all().getLongitud()+1);
        return Persist(perfil);
    }
}
