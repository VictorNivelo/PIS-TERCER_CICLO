
package Controlador;

import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Matricula;

/**
 *
 * @author romer
 */
public class ControladorMatricula {
    private ListaDinamica<Matricula> ListaMatriculas;
    private Matricula matricula;

    public ControladorMatricula() {
        ListaMatriculas = new ListaDinamica<>();
    }
    
    public Boolean guardarMatricula(){
        getMatricula().setIdMatricula(getListaMatriculas().getLongitud());
        getListaMatriculas().Agregar(getMatricula());
        return true;
    }

    public ListaDinamica<Matricula> getListaMatriculas() {
        return ListaMatriculas;
    }

    public void setListaMatriculas(ListaDinamica<Matricula> matriculas) {
        this.ListaMatriculas = matriculas;
    }

    public Matricula getMatricula() {
        if (matricula == null) {
            matricula = new Matricula();
        }
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }
    
}
