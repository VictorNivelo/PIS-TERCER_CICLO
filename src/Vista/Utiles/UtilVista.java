
package Vista.Utiles;

import Controlador.Dao.Modelo.alumnoDao;
import Controlador.Dao.Modelo.asistenciaDao;
import Controlador.Dao.Modelo.carreraDao;
import Modelo.Ciclo;
import Controlador.Dao.Modelo.cicloDao;
import Controlador.Dao.Modelo.nombreCicloDao;
import Controlador.Dao.Modelo.cursoDao;
import Controlador.Dao.Modelo.docenteDao;
import Controlador.Dao.Modelo.facultadDao;
import Controlador.Dao.Modelo.horarioDao;
import Controlador.Dao.Modelo.mallaCurricularDao;
import Controlador.Dao.Modelo.materiaDao;
import Controlador.Dao.Modelo.matriculaDao;
import Controlador.Dao.Modelo.paraleloDao;
import Controlador.Dao.Modelo.periodoAcademicoDao;
import Controlador.Dao.Modelo.personaDao;
import Controlador.Dao.Modelo.rolDao;
import Controlador.Dao.Modelo.unidadCurricularDao;
import Controlador.Dao.Modelo.universidadDao;
import Controlador.TDA.ListaDinamica.Excepcion.ListaVacia;
import Modelo.Alumno;
import Modelo.Asistencia;
import Modelo.Carrera;
import Modelo.NombreCiclo;
import Modelo.Cursa;
import Modelo.Docente;
import Modelo.Facultad;
import Modelo.Horario;
import Modelo.MallaC;
import Modelo.Materia;
import Modelo.Matricula;
import Modelo.Paralelo;
import Modelo.PeriodoAcademico;
import Modelo.Persona;
import Modelo.Rol;
import Modelo.UnidadCurricular;
import Modelo.Universidad;
import javax.swing.JComboBox;

/**
 *
 * @author Victor
 */
public class UtilVista {
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void cargarcomboRoles(JComboBox cbx) throws ListaVacia{
        rolDao Rc = new rolDao();
        cbx.removeAllItems();
        
        if(Rc.getListaRol().EstaVacio()){
            throw new ListaVacia("No hay roles que mostrar");
        }
        else{
           for (int i = 0; i < Rc.getListaRol().getLongitud(); i++) {
            cbx.addItem(Rc.getListaRol().getInfo(i));
           }
        }
    }
    
    @SuppressWarnings("rawtypes")
    public static Rol obtenerRolControl(JComboBox cbx){
        return (Rol) cbx.getSelectedItem();
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void cargarcomboParalelo(JComboBox cbx) throws ListaVacia{
        paraleloDao Rc = new paraleloDao();
        cbx.removeAllItems();
        
        if(Rc.getListaParalelo().EstaVacio()){
            throw new ListaVacia("No hay paralelos que mostrar");
        }
        else{
           for (int i = 0; i < Rc.getListaParalelo().getLongitud(); i++) {
            cbx.addItem(Rc.getListaParalelo().getInfo(i));
           }
        }
    }
    
    @SuppressWarnings("rawtypes")
    public static Paralelo obtenerControlParalelo(JComboBox cbx){
        return (Paralelo) cbx.getSelectedItem();
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void cargarcomboCodigoCurso(JComboBox cbx) throws ListaVacia{
        nombreCicloDao Rc = new nombreCicloDao();
        cbx.removeAllItems();
        
        if(Rc.getListaNombreCiclo().EstaVacio()){
            throw new ListaVacia("No hay nombres que mostrar");
        }
        else{
           for (int i = 0; i < Rc.getListaNombreCiclo().getLongitud(); i++) {
            cbx.addItem(Rc.getListaNombreCiclo().getInfo(i));
           }
        }
    }
    
    @SuppressWarnings("rawtypes")
    public static NombreCiclo obtenerCodigoCursoControl(JComboBox cbx){
        return (NombreCiclo) cbx.getSelectedItem();
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void CargarComboMateria(JComboBox cbx) throws ListaVacia{
        materiaDao Mc = new materiaDao();
        cbx.removeAllItems();
        
        if(Mc.getListaMateria().EstaVacio()){
            throw new ListaVacia("No hay materias que mostrar");
        }
        else{
           for (int i = 0; i < Mc.getListaMateria().getLongitud(); i++) {
            cbx.addItem(Mc.getListaMateria().getInfo(i));
           }
        }
    }
    
    @SuppressWarnings({"rawtypes" })
    public static Materia obtenerComboMateria(JComboBox cbx){
        return (Materia) cbx.getSelectedItem();
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void cargarComboCiclo(JComboBox cbxC) throws ListaVacia{
        cicloDao Cc = new cicloDao();
        cbxC.removeAllItems();

        
        if(Cc.getListaCiclos().EstaVacio()){
            throw new ListaVacia("No hay ciclos que mostrar");
        }
        else{
           for (int i = 0; i < Cc.getListaCiclos().getLongitud(); i++) {
            cbxC.addItem(Cc.getListaCiclos().getInfo(i));
            
           }
        }
    }
    
    @SuppressWarnings("rawtypes")
    public static Ciclo obtenerCicloControl(JComboBox cbx){
        return (Ciclo) cbx.getSelectedItem();
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void cargarcomboCarrera(JComboBox cbx) throws ListaVacia{
        carreraDao CaC = new carreraDao();
        cbx.removeAllItems();
        
        if(CaC.getListaCarreras().EstaVacio()){
            throw new ListaVacia("No hay carreras que mostrar");
        }
        else{
           for (int i = 0; i < CaC.getListaCarreras().getLongitud(); i++) {
            cbx.addItem(CaC.getListaCarreras().getInfo(i));
           }
        }
    }
    
    @SuppressWarnings("rawtypes")
    public static Carrera obtenerCarreraControl(JComboBox cbx){
        return (Carrera) cbx.getSelectedItem();
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void cargarcomboMalla(JComboBox cbx) throws ListaVacia{
        mallaCurricularDao MCc = new mallaCurricularDao();
        cbx.removeAllItems();
        
        if(MCc.getListaMalla().EstaVacio()){
            throw new ListaVacia("No hay malla que mostrar");
        }
        else{
           for (int i = 0; i < MCc.getListaMalla().getLongitud(); i++) {
               MallaC mallaCurriculaP = MCc.getListaMalla().getInfo(i);
               if (mallaCurriculaP.getEstadoMallaCurricular()!= null && mallaCurriculaP.getEstadoMallaCurricular().equalsIgnoreCase("Activa")) {
                    cbx.addItem(mallaCurriculaP);
                }
           }
        }
    }
    
    @SuppressWarnings("rawtypes")
    public static MallaC obtenerMallaControl(JComboBox cbx){
        return (MallaC) cbx.getSelectedItem();
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void cargarcomboHorario(JComboBox cbx) throws ListaVacia{
        horarioDao Hc = new horarioDao();
        cbx.removeAllItems();
        
        if(Hc.getListaHorario().EstaVacio()){
            throw new ListaVacia("No hay horarios que mostrar");
        }
        else{
           for (int i = 0; i < Hc.getListaHorario().getLongitud(); i++) {
            cbx.addItem(Hc.getListaHorario().getInfo(i));
           }
        }
    }
    
    @SuppressWarnings("rawtypes")
    public static Horario obtenerHorarioControl(JComboBox cbx){
        return (Horario) cbx.getSelectedItem();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void cargarcomboPeriodo(JComboBox cbx) throws ListaVacia {
        periodoAcademicoDao PAc = new periodoAcademicoDao();
        cbx.removeAllItems();

        if (PAc.getListaPeriodos().EstaVacio()) {
            throw new ListaVacia("No hay periodos que mostrar");
        } 
        else {
            for (int i = 0; i < PAc.getListaPeriodos().getLongitud(); i++) {
                PeriodoAcademico PeriodoA = PAc.getListaPeriodos().getInfo(i);
                if (PeriodoA.getEstadoPeriodoAcedemico() != null && PeriodoA.getEstadoPeriodoAcedemico().equalsIgnoreCase("Activo")) {
                    cbx.addItem(PeriodoA);
                }
            }
        }
    }

    @SuppressWarnings("rawtypes")
    public static PeriodoAcademico obtenerPeriodoControl(JComboBox cbx){
        return (PeriodoAcademico) cbx.getSelectedItem();
    }
        
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void cargarcomboCurso(JComboBox cbx) throws ListaVacia{
        cursoDao Cc = new cursoDao();
        cbx.removeAllItems();
        
        if(Cc.getListaCursa().EstaVacio()){
            throw new ListaVacia("No hay cursos que mostrar");
        }
        else{
           for (int i = 0; i < Cc.getListaCursa().getLongitud(); i++) {
            cbx.addItem(Cc.getListaCursa().getInfo(i));
           }
        }
    }
    
    @SuppressWarnings("rawtypes")
    public static Cursa obtenerCursoControl(JComboBox cbx){
        return (Cursa) cbx.getSelectedItem();
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void cargarcomboPersona(JComboBox cbx) throws ListaVacia {
        personaDao Pc = new personaDao();
        cbx.removeAllItems();

        if (Pc.getListaPersonas().EstaVacio()) {
            throw new ListaVacia("No hay personas que mostrar");
        } 
        else {
            for (int i = 0; i < Pc.getListaPersonas().getLongitud(); i++) {
                cbx.addItem(Pc.getListaPersonas().getInfo(i));
            }
        }
    }
    
    @SuppressWarnings("rawtypes")
    public static Persona obtenerPersonaControl(JComboBox cbx){
        return (Persona) cbx.getSelectedItem();
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void cargarcomboAsistencia(JComboBox cbx) throws ListaVacia{
        asistenciaDao Ac = new asistenciaDao();
        cbx.removeAllItems();
        
        if(Ac.getListaAsistencia().EstaVacio()){
            throw new ListaVacia("No hay asistencias que mostrar");
        }
        else{
           for (int i = 0; i < Ac.getListaAsistencia().getLongitud(); i++) {
            cbx.addItem(Ac.getListaAsistencia().getInfo(i));
           }
        }
    }
    
    @SuppressWarnings("rawtypes")
    public static Asistencia obtenerAsistenciaControl(JComboBox cbx){
        return (Asistencia) cbx.getSelectedItem();
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void cargarcomboMatricula(JComboBox cbx) throws ListaVacia{
        matriculaDao Mc = new matriculaDao();
        cbx.removeAllItems();
        
        if(Mc.getListaMatriculas().EstaVacio()){
            throw new ListaVacia("No hay asistencias que mostrar");
        }
        else{
           for (int i = 0; i < Mc.getListaMatriculas().getLongitud(); i++) {
            cbx.addItem(Mc.getListaMatriculas().getInfo(i));
           }
        }
    }
    
    @SuppressWarnings("rawtypes")
    public static Matricula obtenerMatriculaControl(JComboBox cbx){
        return (Matricula) cbx.getSelectedItem();
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void CargarComboUniversidad(JComboBox cbx) throws ListaVacia{
        universidadDao Uc = new universidadDao();
        cbx.removeAllItems();
        
        if(Uc.getListaUniversid().EstaVacio()){
            throw new ListaVacia("No hay universidades que mostrar");
        }
        else{
           for (int i = 0; i < Uc.getListaUniversid().getLongitud(); i++) {
            cbx.addItem(Uc.getListaUniversid().getInfo(i));
           }
        }
    }
    
    @SuppressWarnings("rawtypes")
    public static Universidad obtenerUniversidadControl(JComboBox cbx){
        return (Universidad) cbx.getSelectedItem();
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void CargarComboFacultad(JComboBox cbx) throws ListaVacia{
        facultadDao Fc = new facultadDao();
        cbx.removeAllItems();
        
        if(Fc.getListaFacultad().EstaVacio()){
            throw new ListaVacia("No hay facultades que mostrar");
        }
        else{
           for (int i = 0; i < Fc.getListaFacultad().getLongitud(); i++) {
            cbx.addItem(Fc.getListaFacultad().getInfo(i));
           }
        }
    }
    
    @SuppressWarnings("rawtypes")
    public static Facultad obtenerFacultadControl(JComboBox cbx){
        return (Facultad) cbx.getSelectedItem();
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void cargarcomboUnidad(JComboBox cbx) throws ListaVacia{
        unidadCurricularDao UCc = new unidadCurricularDao();
        cbx.removeAllItems();
        
        if(UCc.getListaUnidadCurricular().EstaVacio()){
            throw new ListaVacia("No hay unidades que mostrar");
        }
        else{
           for (int i = 0; i < UCc.getListaUnidadCurricular().getLongitud(); i++) {
            cbx.addItem(UCc.getListaUnidadCurricular().getInfo(i));
           }
        }
    }
    
    @SuppressWarnings("rawtypes")
    public static UnidadCurricular obtenerUnidadControl(JComboBox cbx){
        return (UnidadCurricular) cbx.getSelectedItem();
    }
    
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void cargarcomboAlumnos(JComboBox cbx) throws ListaVacia {
        alumnoDao Ac = new alumnoDao();
        cbx.removeAllItems();

        if (Ac.getListaAlumnos().EstaVacio()) {
            throw new ListaVacia("No hay alumnos que mostrar");
        } 
        else {
            for (int i = 0; i < Ac.getListaAlumnos().getLongitud(); i++) {
                cbx.addItem(Ac.getListaAlumnos().getInfo(i));
            }
        }
    }

    @SuppressWarnings("rawtypes")
    public static Alumno obtenerAlumnosControl(JComboBox cbx) {
        return (Alumno) cbx.getSelectedItem();
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void cargarcomboDocente(JComboBox cbx) throws ListaVacia {
        docenteDao Ac = new docenteDao();
        cbx.removeAllItems();

        if (Ac.getListaDocentes().EstaVacio()) {
            throw new ListaVacia("No hay docentes que mostrar");
        } 
        else {
            for (int i = 0; i < Ac.getListaDocentes().getLongitud(); i++) {
                cbx.addItem(Ac.getListaDocentes().getInfo(i));
            }
        }
    }

    @SuppressWarnings("rawtypes")
    public static Docente obtenerDocenteControl(JComboBox cbx) {
        return (Docente) cbx.getSelectedItem();
    }
    
    //cargar combos especificos por rol
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void cargarcomboPersonaAlumnos(JComboBox cbx) throws ListaVacia {
        personaDao rc = new personaDao();
        cbx.removeAllItems();

        if (rc.getListaPersonas().EstaVacio()) {
            throw new ListaVacia("No hay alumnos que mostrar");
        } 
        else {
            for (int i = 0; i < rc.getListaPersonas().getLongitud(); i++) {
                Persona persona = rc.getListaPersonas().getInfo(i);

                if (persona.getRolPersona()!= null && persona.getRolPersona().getNombreRol().equalsIgnoreCase("Estudiante")) {
                    cbx.addItem(persona);
                }
            }
        }
    }

    @SuppressWarnings("rawtypes")
    public static Persona obtenerPersonaAlumnosControl(JComboBox cbx) {
        return (Persona) cbx.getSelectedItem();
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void cargarcomboPersonaDocentes(JComboBox cbx) throws ListaVacia {
        personaDao Dc = new personaDao();
        cbx.removeAllItems();

        if (Dc.getListaPersonas().EstaVacio()) {
            throw new ListaVacia("No hay docentes que mostrar");
        } 
        else {
            for (int i = 0; i < Dc.getListaPersonas().getLongitud(); i++) {
                Persona persona = Dc.getListaPersonas().getInfo(i);

                if (persona.getRolPersona() != null && persona.getRolPersona().getNombreRol().equalsIgnoreCase("Docente")) {
                    cbx.addItem(persona);
                }
            }
        }
    }

    @SuppressWarnings("rawtypes")
    public static Persona obtenerPersonaDocentesControl(JComboBox cbx) {
        return (Persona) cbx.getSelectedItem();
    }
    
}
