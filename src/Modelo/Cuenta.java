
package Modelo;

/**
 *
 * @author Victor
 */
public class Cuenta {
    private Integer IdCuenta;
    private String Correo;
    private String Contrasena;
    private String EstadoCuenta;

    public Cuenta() {
        
    }

    public Integer getIdCuenta() {
        return IdCuenta;
    }

    public void setIdCuenta(Integer idCuenta) {
        this.IdCuenta = idCuenta;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }

    public String getContrasena() {
        return Contrasena;
    }

    public void setContrasena(String Contrasena) {
        this.Contrasena = Contrasena;
    }

    public String getEstadoCuenta() {
        return EstadoCuenta;
    }

    public void setEstadoCuenta(String EstadoCuenta) {
        this.EstadoCuenta = EstadoCuenta;
    }
    
    @Override
    public String toString() {
        return "idCuenta=" + IdCuenta + ", Correo=" + Correo + ", Contrasena=" + Contrasena + ", EstadoCuenta=" + EstadoCuenta;
    }
    
}
