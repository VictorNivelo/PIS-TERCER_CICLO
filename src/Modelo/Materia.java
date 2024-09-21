
package Modelo;


/**
 *
 * @author Victor
 */
public class Materia {
    private Integer IdMateria;
    private String NombreMateria;
    private String DescipcionMateria;
    private String NumeroHoras;
    
    private Ciclo cicloMateria;
    private Integer CicloID;
    
    public Materia() {
        
    }

    public Integer getIdMateria() {
        return IdMateria;
    }

    public void setIdMateria(Integer IdMateria) {
        this.IdMateria = IdMateria;
    }

    public String getNombreMateria() {
        return NombreMateria;
    }

    public void setNombreMateria(String NombreMateria) {
        this.NombreMateria = NombreMateria;
    }

    public String getDescipcionMateria() {
        return DescipcionMateria;
    }

    public void setDescipcionMateria(String DescipcionMateria) {
        this.DescipcionMateria = DescipcionMateria;
    }

    public String getNumeroHoras() {
        return NumeroHoras;
    }

    public void setNumeroHoras(String NumeroHoras) {
        this.NumeroHoras = NumeroHoras;
    }

    public Ciclo getCicloMateria() {
        return cicloMateria;
    }

    public void setCicloMateria(Ciclo cicloMateria) {
        this.cicloMateria = cicloMateria;
    }

    public Integer getCicloID() {
        return CicloID;
    }

    public void setCicloID(Integer CicloID) {
        this.CicloID = CicloID;
    }
    
    @Override
    public String toString() {
        return NombreMateria+"\n";
    }
    
}
