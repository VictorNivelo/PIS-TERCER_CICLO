
package Controlador.Dao.Modelo;

import Controlador.Dao.DaoImplement;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Ciclo;

/**
 *
 * @author Victor
 */
public class cicloDao extends DaoImplement<Ciclo>{
    private ListaDinamica<Ciclo> ListaCiclos = new ListaDinamica<>();
    private Ciclo ciclos;
    
    public cicloDao(){
        super (Ciclo.class);
    }

    public ListaDinamica<Ciclo> getListaCiclos() {
        ListaCiclos = all();
        return ListaCiclos;
    }

    public void setListaCiclos(ListaDinamica<Ciclo> ListaCiclos) {
        this.ListaCiclos = ListaCiclos;
    }

    public Ciclo getCiclos() {
        if(ciclos == null){
            ciclos = new Ciclo();
        }
        return ciclos;
    }

    public void setCiclos(Ciclo ciclos) {
        this.ciclos = ciclos;
    }
    
    public Boolean Persist(){
        ciclos.setIdCiclo(all().getLongitud()+1);
        return Persist(ciclos);
    }
    
}
    
//    public static void main(String[] args) {
//        cicloDao rc = new cicloDao();
//        rc.getCiclos().setNombreCiclo("Primer ciclo");
//        rc.getCiclos().setNumeroCiclo(1);
//        rc.Persist();
//        rc.setCiclos(null);
//        
//        rc.getCiclos().setNombreCiclo("Segundo ciclo");
//        rc.getCiclos().setNumeroCiclo(2);
//        rc.Persist();
//        rc.setCiclos(null);
//        
//        rc.getCiclos().setNombreCiclo("Tercer ciclo");
//        rc.getCiclos().setNumeroCiclo(3);
//        rc.Persist();
//        rc.setCiclos(null);
//        
//        rc.getCiclos().setNombreCiclo("Cuarto ciclo");
//        rc.getCiclos().setNumeroCiclo(4);
//        rc.Persist();
//        rc.setCiclos(null);
//        
//        rc.getCiclos().setNombreCiclo("Quinto ciclo");
//        rc.getCiclos().setNumeroCiclo(5);
//        rc.Persist();
//        rc.setCiclos(null);
//        
//        rc.getCiclos().setNombreCiclo("Sexto ciclo");
//        rc.getCiclos().setNumeroCiclo(6);
//        rc.Persist();
//        rc.setCiclos(null);
//        
//        rc.getCiclos().setNombreCiclo("Septimo ciclo");
//        rc.getCiclos().setNumeroCiclo(7);
//        rc.Persist();
//        rc.setCiclos(null);
//        
//        rc.getCiclos().setNombreCiclo("Octavo ciclo");
//        rc.getCiclos().setNumeroCiclo(8);
//        rc.Persist();
//        rc.setCiclos(null);
//        
//        rc.getCiclos().setNombreCiclo("Noveno ciclo");
//        rc.getCiclos().setNumeroCiclo(9);
//        rc.Persist();
//        rc.setCiclos(null);
//        
//        rc.getCiclos().setNombreCiclo("Decimo ciclo");
//        rc.getCiclos().setNumeroCiclo(10);
//        rc.Persist();
//        rc.setCiclos(null);
//
//    }
    
//}
