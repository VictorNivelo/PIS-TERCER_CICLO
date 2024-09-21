
package Modelo;

/**
 *
 * @author Victor
 */
public class Perfil {
    private Integer IdPerfil;
    private String NombreUsuario;
    private String FotoUsuario;
    private String Visibilidad;
    private String Estado;
    private String DescripcionUsuario;
    private Persona personaPerfil;

    public Perfil() {
        
    }

    public Integer getIdPerfil() {
        return IdPerfil;
    }

    public void setIdPerfil(Integer IdPerfil) {
        this.IdPerfil = IdPerfil;
    }

    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String NombreUsuario) {
        this.NombreUsuario = NombreUsuario;
    }

    public String getFotoUsuario() {
        return FotoUsuario;
    }

    public void setFotoUsuario(String FotoUsuario) {
        this.FotoUsuario = FotoUsuario;
    }

    public String getDescripcionUsuario() {
        return DescripcionUsuario;
    }

    public void setDescripcionUsuario(String DescripcionUsuario) {
        this.DescripcionUsuario = DescripcionUsuario;
    }

    public Persona getPersonaPerfil() {
        return personaPerfil;
    }

    public void setPersonaPerfil(Persona personaPerfil) {
        this.personaPerfil = personaPerfil;
    }

    public String getVisibilidad() {
        return Visibilidad;
    }

    public void setVisibilidad(String Visibilidad) {
        this.Visibilidad = Visibilidad;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    @Override
    public String toString() {
        return "Id:" + IdPerfil + ", Nombre:" + NombreUsuario + ", Foto:" + FotoUsuario + ", Descripcion:" + DescripcionUsuario ;
    } 
    
}
