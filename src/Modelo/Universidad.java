
package Modelo;

/**
 *
 * @author Victor
 */
public class Universidad {
    private Integer IdUniversidad;
    private String NombreUniversidad;
    private String DireccionUniversidad;
    private String NumeroTelefono;
    private String CorreoUniversidad;
    private String FechaFundacion;

    public Universidad() {
        
    } 

    public Integer getIdU() {
        return IdUniversidad;
    }

    public void setIdU(Integer IdU) {
        this.IdUniversidad = IdU;
    }

    public String getNombreU() {
        return NombreUniversidad;
    }

    public void setNombreU(String NombreU) {
        this.NombreUniversidad = NombreU;
    }

    public String getDireccionU() {
        return DireccionUniversidad;
    }

    public void setDireccionU(String DireccionU) {
        this.DireccionUniversidad = DireccionU;
    }

    public String getTelefonoU() {
        return NumeroTelefono;
    }

    public void setTelefonoU(String TelefonoU) {
        this.NumeroTelefono = TelefonoU;
    }

    public String getCorreoU() {
        return CorreoUniversidad;
    }

    public void setCorreoU(String CorreoU) {
        this.CorreoUniversidad = CorreoU;
    }

    public String getFechaFU() {
        return FechaFundacion;
    }

    public void setFechaFU(String FechaFU) {
        this.FechaFundacion = FechaFU;
    }

    @Override
    public String toString() {
        return NombreUniversidad + "\n";
    }
        
}
