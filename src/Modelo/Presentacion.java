
package Modelo;

/**
 *
 * @author Victor
 */
public class Presentacion {
    private Integer IdPresentacion;
    private String Codigo;
    private String Imagen;
    private String Titulo;
    private String Contenido;
    private String Tiempo;
    private String EstadoPresentacion;

    public Presentacion() {
        
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String Titulo) {
        this.Titulo = Titulo;
    }

    public String getTiempo() {
        return Tiempo;
    }

    public void setTiempo(String Tiempo) {
        this.Tiempo = Tiempo;
    }
            
    public Integer getIdPresentacion() {
        return IdPresentacion;
    }

    public void setIdPresentacion(Integer IdPresentacion) {
        this.IdPresentacion = IdPresentacion;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String Codigo) {
        this.Codigo = Codigo;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String Imagen) {
        this.Imagen = Imagen;
    }

    public String getContenido() {
        return Contenido;
    }

    public void setContenido(String Contenido) {
        this.Contenido = Contenido;
    }

    public String getEstadoPresentacion() {
        return EstadoPresentacion;
    }

    public void setEstadoPresentacion(String EstadoPresentacion) {
        this.EstadoPresentacion = EstadoPresentacion;
    }

    @Override
    public String toString() {
        return Codigo + "\n";
    }
    
}
