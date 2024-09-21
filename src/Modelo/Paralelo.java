
package Modelo;

/**
 *
 * @author Victor
 */
public class Paralelo {
    private Integer IdParalelo;
    private String CodigoParalelo;
    private String Nombre;

    public Paralelo() {
        
    }

    public Integer getIdParalelo() {
        return IdParalelo;
    }

    public void setIdParalelo(Integer IdParalelo) {
        this.IdParalelo = IdParalelo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getCodigoParalelo() {
        return CodigoParalelo;
    }

    public void setCodigoParalelo(String CodigoParalelo) {
        this.CodigoParalelo = CodigoParalelo;
    }

    @Override
    public String toString() {
        return Nombre + "\n";
    }
    
}
