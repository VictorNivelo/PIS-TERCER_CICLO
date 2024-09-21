
package Modelo;

/**
 *
 * @author Victor
 */
public class Persona {
    private Integer IdPersona;
    private String TipoDni;
    private String NumeroCedula;
    private String Nombre;
    private String Apellido;
    private String Genero;
    private String FechaNacimineto;
    private String Direccion;
    private String Telefono;
    
    private Rol rolPersona;
    private Integer RolID;
    
    private Cuenta cuentaPersona;
    private Integer CuendaID;
    
    public Persona() {
        
    }

    public Integer getIdPersona() {
        return IdPersona;
    }

    public void setIdPersona(Integer IdPersona) {
        this.IdPersona = IdPersona;
    }

    public String getTipoDni() {
        return TipoDni;
    }

    public void setTipoDni(String TipoDni) {
        this.TipoDni = TipoDni;
    }

    public String getNumeroCedula() {
        return NumeroCedula;
    }

    public void setNumeroCedula(String NumeroCedula) {
        this.NumeroCedula = NumeroCedula;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }

    public String getGenero() {
        return Genero;
    }

    public void setGenero(String Genero) {
        this.Genero = Genero;
    }

    public String getFechaNacimineto() {
        return FechaNacimineto;
    }

    public void setFechaNacimineto(String FechaNacimineto) {
        this.FechaNacimineto = FechaNacimineto;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public Rol getRolPersona() {
        return rolPersona;
    }

    public void setRolPersona(Rol rolPersona) {
        this.rolPersona = rolPersona;
    }

    public Cuenta getCuentaPersona() {
        return cuentaPersona;
    }

    public void setCuentaPersona(Cuenta cuentaPersona) {
        this.cuentaPersona = cuentaPersona;
    }

    public Integer getRolID() {
        return RolID;
    }

    public void setRolID(Integer RolID) {
        this.RolID = RolID;
    }

    public Integer getCuendaID() {
        return CuendaID;
    }

    public void setCuendaID(Integer CuendaID) {
        this.CuendaID = CuendaID;
    }

    @Override
    public String toString() {
        return NumeroCedula + " " + Nombre  +" "+ Apellido +  "\n";
    }

}
