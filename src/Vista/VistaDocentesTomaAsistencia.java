package Vista;

import Controlador.Dao.Modelo.alumnoDao;
import Controlador.Dao.Modelo.asistenciaDao;
import Controlador.Dao.Modelo.cursoDao;
import Controlador.Dao.Modelo.horarioDao;
import Controlador.TDA.ListaDinamica.Excepcion.ListaVacia;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Controlador.Utiles.UtilesControlador;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import Modelo.Alumno;
import Modelo.Asistencia;
import Modelo.ControlAccesoDocente;
import Modelo.Cursa;
import Modelo.Horario;
import Modelo.Materia;
import Modelo.Persona;
import Modelo.Tematica;
import Vista.Utiles.UtilVista;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.swing.JOptionPane;

/**
 *
 * @author Victor
 */
public class VistaDocentesTomaAsistencia extends javax.swing.JFrame {

    DefaultTableModel dtm = new DefaultTableModel();
    alumnoDao alumnoControlDao = new alumnoDao();
    cursoDao cursaControlDao = new cursoDao();
    horarioDao horarioControlDao = new horarioDao();
    asistenciaDao asistenciaControlDao = new asistenciaDao();
    Materia materiaSeleccionada;
    ListaDinamica<Horario> listaHorarios;
    ListaDinamica<Asistencia> listaAsistencia = new ListaDinamica<>();
    int numeroSecuencia = 1;
    SimpleDateFormat Formato = new SimpleDateFormat("dd/MMMM/yyyy");
    Map<Integer, Integer> secuenciaAlumnoMap = new HashMap<>();

    /**
     * Creates new form VistaTomaAsistencia
     *
     * @throws Controlador.TDA.ListaDinamica.Excepcion.ListaVacia
     */
    public VistaDocentesTomaAsistencia() throws ListaVacia {
        initComponents();
        this.setLocationRelativeTo(null);
        cargarMateriasYHorariosDocente();
        setIconImage(new ImageIcon(getClass().getResource("/Vista/RecursosGraficos/IconoPrograma.png")).getImage());
        DateFechaActual.setDateFormatString("dd/MMMM/yyyy");
        dtm.setColumnIdentifiers(new String[]{"#", "DNI", "Nombre", "Apellido", "Asistencia"});
        tblt.setModel(dtm);
        AgregarCheckbox(4, tblt);
        cbxHorario.setSelectedIndex(-1);
        cbxMateria.setSelectedIndex(-1);
    }

    private void cargarMateriasYHorariosDocente() {
        int idDocenteLogeado = ControlAccesoDocente.getIdDocenteLogeado();
        cbxMateria.removeAllItems();
        cbxHorario.removeAllItems();

        ListaDinamica<Cursa> listaMaterias = cursaControlDao.all();
        listaHorarios = horarioControlDao.all();
        materiaSeleccionada = null;

        for (Cursa curso : listaMaterias.toArray()) {
            int idDocenteMateria = curso.getDocenteCursa().getIdDocente();

            if (idDocenteLogeado == idDocenteMateria) {
                Materia materia = curso.getMateriaCursa();
                boolean materiaYaAgregada = false;
                for (int i = 0; i < cbxMateria.getItemCount(); i++) {
                    Materia materiaEnCombo = (Materia) cbxMateria.getItemAt(i);
                    if (materiaEnCombo.getNombreMateria().equals(materia.getNombreMateria())) {
                        materiaYaAgregada = true;
                        break;
                    }
                }
                if (!materiaYaAgregada) {
                    cbxMateria.addItem(materia);
                }
                if (curso.getMateriaCursa().equals(materiaSeleccionada)) {
                    for (Horario horario : listaHorarios.toArray()) {
                        if (Objects.equals(horario.getMateriaHorario().getIdMateria(), materiaSeleccionada.getIdMateria())) {
                            cbxHorario.addItem(horario);
                        }
                    }
                }
            }
        }
    }

    private void CargarAlumnoTabla(Materia materiaSeleccionada) {
        try {
            dtm.setRowCount(0);
            numeroSecuencia = 1;
            secuenciaAlumnoMap.clear();
            ListaDinamica<Alumno> listaAlumnos = obtenerAlumnosDeMateria(materiaSeleccionada);

            for (Alumno alumno : listaAlumnos.toArray()) {
                dtm.addRow(new Object[]{numeroSecuencia, alumno.getDatosAlumno().getNumeroCedula(), alumno.getDatosAlumno().getNombre(), alumno.getDatosAlumno().getApellido(), true});
                secuenciaAlumnoMap.put(numeroSecuencia, alumno.getDatosAlumno().getIdPersona());
                numeroSecuencia++;
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ListaDinamica<Alumno> obtenerAlumnosDeMateria(Materia materiaSeleccionada) {
        ListaDinamica<Alumno> listaAlumnos = new ListaDinamica<>();
        ListaDinamica<Cursa> listaCursas = cursaControlDao.all();

        for (Cursa cursa : listaCursas.toArray()) {
            if (Objects.equals(cursa.getMateriaCursa().getIdMateria(), materiaSeleccionada.getIdMateria())) {
                listaAlumnos.Agregar(cursa.getMatriculaCursa().getAlumnoMatricula());
            }
        }
        return listaAlumnos;
    }
    
    public void AgregarCheckbox(int columna, JTable tabla) {
        TableColumn columnaTabla = tabla.getColumnModel().getColumn(columna);
        columnaTabla.setCellEditor(tabla.getDefaultEditor(Boolean.class));
        columnaTabla.setCellRenderer(tabla.getDefaultRenderer(Boolean.class));
    }

    public boolean estaSeleccionada(int fila, int columna, JTable tabla) {
        return tabla.getValueAt(fila, columna) != null;
    }
    
    private void Limpiar() throws ListaVacia {
        DateFechaActual.setDate(null);
        cbxMateria.setSelectedIndex(-1);
        cbxHorario.setSelectedIndex(-1);
        txtTematica.setText("");
        txtObservacion.setText("");
        asistenciaControlDao.setAsistencia(null);
        dtm.setRowCount(0);
        CargarTablaAlumnos();
    }
    
    private boolean asistenciaExiste(Asistencia nuevaAsistencia) {
        ListaDinamica<Asistencia> asistencias = asistenciaControlDao.all();
        if (asistencias.EstaVacio()) {
            return false;
        }
        for (Asistencia a : asistencias.toArray()) {
            if (a.getAlumnoAsistencia().getIdAlumno().equals(nuevaAsistencia.getAlumnoAsistencia().getIdAlumno())
                    && a.getFechaAsistencia().equals(nuevaAsistencia.getFechaAsistencia())
                    && a.getHorarioAsistencia().getIdHorario().equals(nuevaAsistencia.getHorarioAsistencia().getIdHorario())) {
                return true;
            }
        }
        return false;
    }
    
    private void Guardar() throws ListaVacia {
        Date fechaIngresada = DateFechaActual.getDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateFechaActual.getDate());
        int diaSeleccionado = calendar.get(Calendar.DAY_OF_WEEK);
        String diaHorario = UtilVista.obtenerHorarioControl(cbxHorario).getDiaSemana();
        int diaHorarioNumerico = convertirDiaSemanaANumero(diaHorario);

        if (DateFechaActual.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Falta seleccionar la fecha", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else if (!validarFechaNoFutura(fechaIngresada)) {
            JOptionPane.showMessageDialog(null, "La fecha de la asistencia no puede ser futura", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else if (cbxMateria.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Falta seleccionar la materia", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else if (txtTematica.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Falta llenar tematica de la clase", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else if (txtObservacion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Falta llenar la observacion", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else if (diaSeleccionado != diaHorarioNumerico) {
            JOptionPane.showMessageDialog(null, "El día seleccionado no coincide con el día del horario", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else {
            boolean asistenciaGuardada = false;

            for (int i = 0; i < tblt.getRowCount(); i++) {
                boolean estaPresente = (boolean) tblt.getValueAt(i, 4);
                String estadoAsistencia = estaPresente ? "Presente" : "Ausente";

                Alumno alumno = obtenerAlumnoDesdeTabla(i);

                if (alumno != null) {
                    Integer idA = listaAsistencia.getLongitud()+1;
                    Date fechaAsistencia = DateFechaActual.getDate();
                    String Fecha = Formato.format(fechaAsistencia);

                    Asistencia nuevaAsistencia = new Asistencia();
                    nuevaAsistencia.setIdAsistencia(idA);
                    nuevaAsistencia.setFechaAsistencia(Fecha);
                    nuevaAsistencia.setEstadoAsistencia(estadoAsistencia);
                    nuevaAsistencia.setObservacion(txtObservacion.getText());
                    nuevaAsistencia.setHorarioAsistencia(UtilVista.obtenerHorarioControl(cbxHorario));

                    Tematica nuevaTematica = new Tematica();
                    nuevaTematica.setIdTematica(idA);
                    nuevaTematica.setNombreTematica(txtTematica.getText());
                    String fechaTematica = formatearFecha(fechaIngresada);
                    nuevaTematica.setFechaTematica(fechaTematica);
                    nuevaAsistencia.setTematicaAsistencia(nuevaTematica);

                    nuevaAsistencia.setAlumnoAsistencia(alumno);
                    
                    if (asistenciaExiste(nuevaAsistencia)) {
                        JOptionPane.showMessageDialog(null, "La asistencia ya existe para este dia y horario", "Error", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    
                    if (asistenciaControlDao.Persist(nuevaAsistencia)) {
                        asistenciaGuardada = true;
                    } 
                    else {
                        JOptionPane.showMessageDialog(null, "No se pudo guardar la asistencia del alumno: " + alumno.getDatosAlumno().getNombre(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

            if (asistenciaGuardada) {
                JOptionPane.showMessageDialog(null, "Asistencia guardada exitosamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                try {
                    Limpiar();
                } 
                catch (ListaVacia ex) {
                    ex.printStackTrace();
                }
            } 
            else {
                JOptionPane.showMessageDialog(null, "No se pudo guardar ninguna asistencia", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private int convertirDiaSemanaANumero(String diaSemana) {
        switch (diaSemana) {
            case "Lunes":
                return Calendar.MONDAY;
            case "Martes":
                return Calendar.TUESDAY;
            case "Miercoles":
                return Calendar.WEDNESDAY;
            case "Jueves":
                return Calendar.THURSDAY;
            case "Viernes":
                return Calendar.FRIDAY;
            case "Sabado":
                return Calendar.SATURDAY;
            case "Domingo":
                return Calendar.SUNDAY;
            default:
                return -1;
        }
    }
    
    private boolean validarFechaNoFutura(Date date) {
        Date hoy = new Date();
        return !date.after(hoy);
    }

    private Alumno obtenerAlumnoDesdeTabla(int rowIndex) {
        Integer numeroSecuencia = (Integer) tblt.getValueAt(rowIndex, 0);
        Integer idAlumno = secuenciaAlumnoMap.get(numeroSecuencia);
        if (idAlumno != null) {
            ListaDinamica<Alumno> listaAlumnos = obtenerAlumnosDeMateria(materiaSeleccionada);
            for (Alumno alumno : listaAlumnos.toArray()) {
                if (alumno.getDatosAlumno().getIdPersona().equals(idAlumno)) {
                    return alumno;
                }
            }
        }
        return null;
    }

    private void CargarTablaAlumnos() {
        try {
            Object[] datosLista = cursaControlDao.getListaCursa().toArray();
            for (Object dato : datosLista) {
                if (dato instanceof Persona) {
                    Persona persona = (Persona) dato;
                    dtm.addRow(new Object[]{persona.getIdPersona(), persona.getNumeroCedula(), persona.getNombre(), persona.getApellido(), true});
                }
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String formatearFecha(Date fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMMM/yyyy");
        return sdf.format(fecha);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblt = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        cbxTipoBusqueda = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtTematica = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        DateFechaActual = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        cbxHorario = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        txtObservacion = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        cbxMateria = new javax.swing.JComboBox<>();
        jButton6 = new javax.swing.JButton();
        btnOrdenar = new javax.swing.JButton();
        cbxOrden = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        cbxTipoOrden = new javax.swing.JComboBox<>();
        jButton7 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("REGISTRO DE ASISTENCIA");

        jPanel1.setBackground(new java.awt.Color(190, 193, 197));

        jPanel2.setBackground(new java.awt.Color(61, 90, 134));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/LojoUNL.png"))); // NOI18N

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Haettenschweiler", 0, 48)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("SERVICIO DE TOMA DE ASISTENCIA");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel2.setFont(new java.awt.Font("Candara Light", 1, 32)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Curso");

        jLabel4.setFont(new java.awt.Font("Candara Light", 1, 32)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Lista de alumnos");

        tblt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblt.setSelectionBackground(new java.awt.Color(200, 222, 180));
        tblt.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(tblt);

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Buscar por");

        cbxTipoBusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nombre", "Apellido" }));
        cbxTipoBusqueda.setSelectedIndex(-1);

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Busqueda");

        jButton1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Buscar.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Regresar.png"))); // NOI18N
        jButton4.setText("REGRESAR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Seleccionar.png"))); // NOI18N
        jButton5.setText("SELECCIONAR");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Tematica");

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Fecha");

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Horario");

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Observacion");

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Materia");

        cbxMateria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxMateriaActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Guardar.png"))); // NOI18N
        jButton6.setText("GUARDAR ASISTENCIA");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        btnOrdenar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Ordenar.png"))); // NOI18N
        btnOrdenar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdenarActionPerformed(evt);
            }
        });

        cbxOrden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Asendente", "Desendente" }));
        cbxOrden.setSelectedIndex(-1);

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Ordenar");

        cbxTipoOrden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nombre", "Apellido" }));
        cbxTipoOrden.setSelectedIndex(-1);

        jButton7.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/RegistroAsistencias.png"))); // NOI18N
        jButton7.setText("REGISTRO DE ASISTENCIAS");
        jButton7.setToolTipText("");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5))
                    .addComponent(txtTematica)
                    .addComponent(txtObservacion)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxHorario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxMateria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DateFechaActual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxTipoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 894, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxTipoOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOrdenar)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel4))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbxOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbxTipoOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8))
                            .addComponent(btnOrdenar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cbxTipoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jLabel13)
                    .addComponent(cbxMateria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(cbxHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DateFechaActual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTematica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtObservacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton6)
                    .addComponent(jButton7))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        try {
            if (cbxTipoBusqueda.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Porfavor seleccione donde quiere buscar", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else {
                ListaDinamica<Alumno> lista = alumnoControlDao.all();

                String Campo = txtBuscar.getText();
                String TipoCampo = cbxTipoBusqueda.getSelectedItem().toString();

                switch (TipoCampo) {
                    case "Nombre":
                        TipoCampo = "Nombre";
                        break;
                    case "Apellido":
                        TipoCampo = "Apellido";
                        break;
                    default:
                        throw new AssertionError();
                }

                ListaDinamica<Alumno> ResultadoBusqueda = UtilesControlador.BusquedaLineal(lista, Campo, TipoCampo);

                Object[][] datos = new Object[ResultadoBusqueda.getLongitud()][3];

                for (int i = 0; i < ResultadoBusqueda.getLongitud(); i++) {
                    Alumno p = ResultadoBusqueda.getInfo(i);
                    datos[i] = new Object[]{
                        p.getIdAlumno(),
                        p.getDatosAlumno().getNombre(),
                        p.getDatosAlumno().getApellido(),};
                }

                Object[] columnas = {"#", "Nombre", "Apellido", "Asistencia"};

                DefaultTableModel modeloTabla = new DefaultTableModel(datos, columnas);

                tblt.setModel(modeloTabla);
                AgregarCheckbox(4, tblt);

            }
        } 
        catch (Exception e) {

        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
        try {
            VistaDocentePrincipal abrirLogin = new VistaDocentePrincipal();
            abrirLogin.setVisible(true);
            this.setVisible(false);
        } 
        catch (Exception e) {
            
        }
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        
        if(cbxMateria.getSelectedIndex() == -1){
            JOptionPane.showMessageDialog(null, "Porfavor seleccione la materia", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
        else if(cbxHorario.getSelectedIndex() == -1){
            JOptionPane.showMessageDialog(null, "Porfavor seleccione el horario", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            Materia materiaSeleccionada = (Materia) cbxMateria.getSelectedItem();
            CargarAlumnoTabla(materiaSeleccionada);
        }
            
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btnOrdenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdenarActionPerformed

        try {
            if (cbxTipoOrden.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "No ha seleccionado el campo", "FALTA SELCCIONAR", JOptionPane.WARNING_MESSAGE);
            } 
            else if (cbxOrden.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "No ha seleccionado el orden", "FALTA SELCCIONAR", JOptionPane.WARNING_MESSAGE);
            } 
            else {

                ListaDinamica<Alumno> lista = alumnoControlDao.all();

                String Campo = txtBuscar.getText();
                String TipoCampo = cbxTipoBusqueda.getSelectedItem().toString();

                switch (TipoCampo) {
                    case "Nombre":
                        TipoCampo = "Nombre";
                        break;
                    case "Apellido":
                        TipoCampo = "Apellido";
                        break;
                    default:
                        throw new AssertionError();
                }

                ListaDinamica<Alumno> ResultadoBusqueda = UtilesControlador.BusquedaLineal(lista, Campo, TipoCampo);

                Object[][] datos = new Object[ResultadoBusqueda.getLongitud()][4];

                for (int i = 0; i < ResultadoBusqueda.getLongitud(); i++) {
                    Alumno p = ResultadoBusqueda.getInfo(i);
                    datos[i] = new Object[]{
                        p.getIdAlumno(),
                        p.getDatosAlumno().getNombre(),
                        p.getDatosAlumno().getApellido(),
                        false
                    };
                }

                Object[] columnas = {"#", "Nombre", "Apellido", "Asistencia"};

                DefaultTableModel modeloTabla = new DefaultTableModel(datos, columnas);

                tblt.setModel(modeloTabla);
                AgregarCheckbox(4, tblt);
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }//GEN-LAST:event_btnOrdenarActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        
        try {
            Date fechaNacimiento = DateFechaActual.getDate();
            if (DateFechaActual.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar la fecha", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (!validarFechaNoFutura(fechaNacimiento)) {
                JOptionPane.showMessageDialog(null, "La fecha de la asistencia no puede ser futura", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (cbxMateria.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar la materia", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (txtTematica.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar tematica de la clase", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (txtObservacion.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar la observacion", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else {
                Guardar();
            }
        } 
        catch (Exception e) {

        }
        
    }//GEN-LAST:event_jButton6ActionPerformed

    private void cbxMateriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxMateriaActionPerformed

        materiaSeleccionada = (Materia) cbxMateria.getSelectedItem();
        cbxHorario.removeAllItems();

        if (materiaSeleccionada != null) {
            for (Horario horario : listaHorarios.toArray()) {
                if (Objects.equals(horario.getMateriaHorario().getIdMateria(), materiaSeleccionada.getIdMateria())) {
                    cbxHorario.addItem(horario);
                }
            }
        }

    }//GEN-LAST:event_cbxMateriaActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        
        try {
            VistaGestionRegistroAsistencias vra = new VistaGestionRegistroAsistencias();
            vra.setVisible(true);
            this.setVisible(false);
        } 
        catch (Exception e) {
            
        }
        
    }//GEN-LAST:event_jButton7ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VistaDocentesTomaAsistencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaDocentesTomaAsistencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaDocentesTomaAsistencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaDocentesTomaAsistencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new VistaDocentesTomaAsistencia().setVisible(true);
                } 
                catch (ListaVacia ex) {
                    
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DateFechaActual;
    private javax.swing.JButton btnOrdenar;
    private javax.swing.JComboBox<Object> cbxHorario;
    private javax.swing.JComboBox<Object> cbxMateria;
    private javax.swing.JComboBox<String> cbxOrden;
    private javax.swing.JComboBox<String> cbxTipoBusqueda;
    private javax.swing.JComboBox<String> cbxTipoOrden;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblt;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtObservacion;
    private javax.swing.JTextField txtTematica;
    // End of variables declaration//GEN-END:variables
}
