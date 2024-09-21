
package Modelo;


/**
 *
 * @author Victor
 */
public class Horario {
    private Integer IdHorario;
    private String CodigoHorario;
    private String DiaSemana;
    private String HoraIncio;
    private String HoraFin;
    
    private Materia materiaHorario;
    private Integer idMateria;

    public Horario() {
        
    }

    public Integer getIdHorario() {
        return IdHorario;
    }

    public void setIdHorario(Integer IdHorario) {
        this.IdHorario = IdHorario;
    }

    public String getDiaSemana() {
        return DiaSemana;
    }

    public void setDiaSemana(String DiaSemana) {
        this.DiaSemana = DiaSemana;
    }

    public String getHoraIncio() {
        return HoraIncio;
    }

    public void setHoraIncio(String HoraIncio) {
        this.HoraIncio = HoraIncio;
    }

    public String getHoraFin() {
        return HoraFin;
    }

    public void setHoraFin(String HoraFin) {
        this.HoraFin = HoraFin;
    }

    public Materia getMateriaHorario() {
        return materiaHorario;
    }

    public void setMateriaHorario(Materia materiaHorario) {
        this.materiaHorario = materiaHorario;
    }

    public String getCodigoHorario() {
        return CodigoHorario;
    }

    public void setCodigoHorario(String CodigoHorario) {
        this.CodigoHorario = CodigoHorario;
    }

    public Integer getMateriaID() {
        return idMateria;
    }

    public void setMateriaID(Integer MateriaID) {
        this.idMateria = MateriaID;
    }
    
    @Override
    public String toString() {
        return DiaSemana + "  "+ HoraIncio + " - " + HoraFin + "\n";
    }
    
}
