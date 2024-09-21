
package Vista;

import Controlador.Dao.Modelo.alumnoDao;
import Controlador.Dao.Modelo.asistenciaDao;
import Controlador.Dao.Modelo.cursoDao;
import Controlador.Dao.Modelo.horarioDao;
import Controlador.TDA.ListaDinamica.Excepcion.ListaVacia;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Controlador.Utiles.UtilesControlador;
import Modelo.Alumno;
import Modelo.Asistencia;
import Modelo.ControlAccesoDocente;
import Modelo.Cursa;
import Modelo.Horario;
import Modelo.Materia;
import Modelo.Persona;
import Vista.Utiles.UtilVista;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Victor
 */
public class VistaGestionRegistroAsistencias extends javax.swing.JFrame {
    asistenciaDao asistenciaControlDao = new asistenciaDao();
    DefaultTableModel dtm = new DefaultTableModel();
    alumnoDao alumnoControlDao = new alumnoDao();
    horarioDao horarioControlDao = new horarioDao();
    cursoDao cursaControlDao = new cursoDao();
    SimpleDateFormat Formato = new SimpleDateFormat("dd/MMMM/yyyy");
    Materia materiaSeleccionada;
    ListaDinamica<Horario> listaHorarios;
    Map<Integer, Integer> secuenciaAlumnoMap = new HashMap<>();

    /**
     * Creates new form VistaRegistroAsistencias
     * @throws Controlador.TDA.ListaDinamica.Excepcion.ListaVacia
     */
    public VistaGestionRegistroAsistencias() throws ListaVacia {
        initComponents();
        this.setLocationRelativeTo(null);
        DateFechaSeleccionada.setDateFormatString("dd/MMMM/yyyy");     
        setIconImage(new ImageIcon(getClass().getResource("/Vista/RecursosGraficos/IconoPrograma.png")).getImage());
        cargarMateriasYHorariosDocente();
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
    
    public void AgregarCheckbox(int columna, JTable tabla) {
        TableColumn columnaTabla = tabla.getColumnModel().getColumn(columna);
        columnaTabla.setCellEditor(tabla.getDefaultEditor(Boolean.class));
        columnaTabla.setCellRenderer(tabla.getDefaultRenderer(Boolean.class));
    }

    public boolean estaSeleccionada(int fila, int columna, JTable tabla) {
        return tabla.getValueAt(fila, columna) != null;
    }
    
    public static String formatearFecha(Date fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMMM/yyyy");
        return sdf.format(fecha);
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
    
    @SuppressWarnings("unused")
    private void Limpiar() throws ListaVacia {
        cbxMateria.setSelectedIndex(-1);
        cbxHorario.setSelectedIndex(-1);
        DateFechaSeleccionada.setDate(null);
        asistenciaControlDao.setAsistencia(null);
        dtm.setRowCount(0);
        CargarTablaAlumnos();
    }
    
    public  Integer OrdenSeleccionado(){
        String OrdenO = cbxOrden.getSelectedItem().toString();

        if ("Asendente".equals(OrdenO)) {
            return 1;
        }
        if("Desendente".equals(OrdenO)){
            return 0;
        }
        return null;
    }
    
    private Alumno obtenerAlumnoDesdeTabla(int rowIndex) {
        Integer numeroSecuencia = (Integer) tblRegistroAsistencia.getValueAt(rowIndex, 0);
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cbxMateria = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        DateFechaSeleccionada = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cbxTipoBusqueda = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        btnOrdenar = new javax.swing.JButton();
        cbxOrden = new javax.swing.JComboBox<>();
        cbxTipoOrden = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        btnVerRegistro = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRegistroAsistencia = new javax.swing.JTable();
        btnModificar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        cbxHorario = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("VISTA REGISTRO DE ASISTENCIA");

        jPanel1.setBackground(new java.awt.Color(190, 193, 197));

        jPanel2.setBackground(new java.awt.Color(61, 90, 134));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/LojoUNL.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Haettenschweiler", 0, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("VISTA REGISTRO DE ASISTENCIAS");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(jLabel1)
                .addGap(68, 68, 68)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel3.setFont(new java.awt.Font("Candara Light", 1, 32)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Informacion");

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Materia");

        cbxMateria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxMateriaActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Fecha");

        jLabel4.setFont(new java.awt.Font("Candara Light", 1, 32)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Lista de alumnos");

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Buscar por");

        cbxTipoBusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nombre", "Apellido", "Numero DNI" }));
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

        btnOrdenar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btnOrdenar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Ordenar.png"))); // NOI18N
        btnOrdenar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdenarActionPerformed(evt);
            }
        });

        cbxOrden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Asendente", "Desendente" }));
        cbxOrden.setSelectedIndex(-1);

        cbxTipoOrden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nombre", "Apellido", "Numero DNI" }));
        cbxTipoOrden.setSelectedIndex(-1);

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Ordenar");

        jButton2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Regresar.png"))); // NOI18N
        jButton2.setText("REGRESAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnVerRegistro.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btnVerRegistro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/VerRegistro.png"))); // NOI18N
        btnVerRegistro.setText("VER");
        btnVerRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerRegistroActionPerformed(evt);
            }
        });

        tblRegistroAsistencia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblRegistroAsistencia);

        btnModificar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Modificar.png"))); // NOI18N
        btnModificar.setText("MODIFICAR");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Horario");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnVerRegistro))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE))
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(DateFechaSeleccionada, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                            .addComponent(cbxMateria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxHorario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnModificar, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxTipoOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnOrdenar))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cbxTipoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton1))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cbxOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbxTipoOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8))
                                .addGap(7, 7, 7))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel5)
                            .addComponent(cbxTipoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxMateria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnOrdenar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DateFechaSeleccionada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(cbxHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(btnModificar)
                    .addComponent(btnVerRegistro))
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
                        p.getDatosAlumno().getNumeroCedula(),
                        p.getDatosAlumno().getNombre(),
                        p.getDatosAlumno().getApellido(),};
                }

                Object[] columnas = {"#", "DNI", "Nombre", "Apellido", "Asistencia"};

                DefaultTableModel modeloTabla = new DefaultTableModel(datos, columnas);

                tblRegistroAsistencia.setModel(modeloTabla);
                AgregarCheckbox(4, tblRegistroAsistencia);

            }
        }
        catch (Exception e) {

        }

    }//GEN-LAST:event_jButton1ActionPerformed

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
                    case "Numero DNI":
                        TipoCampo = "NumeroCedula";
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
                        p.getDatosAlumno().getNumeroCedula(),
                        p.getDatosAlumno().getNombre(),
                        p.getDatosAlumno().getApellido(),
                        false
                    };
                }

                Object[] columnas = {"#", "DNI", "Nombre", "Apellido", "Asistencia"};

                DefaultTableModel modeloTabla = new DefaultTableModel(datos, columnas);

                tblRegistroAsistencia.setModel(modeloTabla);
                AgregarCheckbox(4, tblRegistroAsistencia);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_btnOrdenarActionPerformed

    private void btnVerRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerRegistroActionPerformed
  
        try {
            if (asistenciaControlDao == null) {
                JOptionPane.showMessageDialog(null, "No hay datos que cargar", "Error", JOptionPane.WARNING_MESSAGE);
            }
            else {
                if (cbxMateria.getSelectedIndex() == -1 || cbxHorario.getSelectedIndex() == -1 || DateFechaSeleccionada.getDate() == null) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione la materia, el horario y la fecha", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Date selectedDate = DateFechaSeleccionada.getDate();
                String fechaFormateada = Formato.format(selectedDate);
                Horario selectedHorario = UtilVista.obtenerHorarioControl(cbxHorario);
                Materia selectedMateria = UtilVista.obtenerComboMateria(cbxMateria);

                ListaDinamica<Asistencia> asistencias = asistenciaControlDao.getListaAsistencia();

                List<Object[]> data = new ArrayList<>();
                for (Asistencia asistencia : asistencias.toArray()) {
                    if (asistencia.getFechaAsistencia().equals(fechaFormateada)
                            && asistencia.getHorarioAsistencia().getIdHorario().equals(selectedHorario.getIdHorario())
                            && asistencia.getHorarioAsistencia().getMateriaHorario().getIdMateria().equals(selectedMateria.getIdMateria())) {
                        Object[] rowData = new Object[5];
                        rowData[0] = data.size() + 1;
                        rowData[1] = asistencia.getAlumnoAsistencia().getDatosAlumno().getNumeroCedula();
                        rowData[2] = asistencia.getAlumnoAsistencia().getDatosAlumno().getNombre();
                        rowData[3] = asistencia.getAlumnoAsistencia().getDatosAlumno().getApellido();
                        rowData[4] = asistencia.getEstadoAsistencia().equals("Presente");
                        data.add(rowData);
                    }
                }

                if (data.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No hay registros de asistencia para la fecha, horario y materia seleccionados", "Información", JOptionPane.INFORMATION_MESSAGE);
                } 
                else {
                    Object[] columnNames = {"#", "DNI", "Nombre", "Apellido", "Asistencia"};
                    Object[][] dataArray = data.toArray(new Object[0][0]);
                    DefaultTableModel model = new DefaultTableModel(dataArray, columnNames);
                    tblRegistroAsistencia.setModel(model);
                    AgregarCheckbox(4, tblRegistroAsistencia);
                }
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar los registros de asistencia", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
//        try {
//            if (cbxMateria.getSelectedIndex() == -1 || cbxHorario.getSelectedIndex() == -1 || DateFechaSeleccionada.getDate() == null) {
//                JOptionPane.showMessageDialog(null, "Por favor, seleccione la materia, el horario y la fecha", "Error", JOptionPane.WARNING_MESSAGE);
//                return;
//            }
//
//            Date selectedDate = DateFechaSeleccionada.getDate();
//            String fechaFormateada = Formato.format(selectedDate);
//            Horario selectedHorario = UtilVista.obtenerHorarioControl(cbxHorario);
//            Materia selectedMateria = UtilVista.obtenerComboMateria(cbxMateria);
//
//            ListaDinamica<Asistencia> asistencias = asistenciaControlDao.getListaAsistencia();
//
//            List<Object[]> data = new ArrayList<>();
//            for (Asistencia asistencia : asistencias.toArray()) {
//                if (asistencia.getFechaAsistencia().equals(fechaFormateada)
//                        && asistencia.getHorarioAsistencia().getIdHorario().equals(selectedHorario.getIdHorario())
//                        && asistencia.getHorarioAsistencia().getMateriaHorario().getIdMateria().equals(selectedMateria.getIdMateria())) {
//                    Object[] rowData = new Object[5];
//                    rowData[0] = data.size() + 1;
//                    rowData[1] = asistencia.getAlumnoAsistencia().getDatosAlumno().getNumeroCedula();
//                    rowData[2] = asistencia.getAlumnoAsistencia().getDatosAlumno().getNombre();
//                    rowData[3] = asistencia.getAlumnoAsistencia().getDatosAlumno().getApellido();
//                    rowData[4] = asistencia.getEstadoAsistencia().equals("Presente") ? "Presente" : "Ausente";
//                    data.add(rowData);
//                }
//            }
//
//            if (data.isEmpty()) {
//                JOptionPane.showMessageDialog(null, "No hay registros de asistencia para la fecha, horario y materia seleccionados", "Información", JOptionPane.INFORMATION_MESSAGE);
//            } 
//            else {
//                Object[] columnNames = {"#", "DNI", "Nombre", "Apellido", "Asistencia"};
//                Object[][] dataArray = data.toArray(new Object[0][0]);
//                DefaultTableModel model = new DefaultTableModel(dataArray, columnNames);
//                tblRegistroAsistencia.setModel(model);
//                AgregarCheckbox(4, tblRegistroAsistencia);
//            }
//        } 
//        catch (Exception e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Error al cargar los registros de asistencia", "Error", JOptionPane.ERROR_MESSAGE);
//        }
        
    }//GEN-LAST:event_btnVerRegistroActionPerformed

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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        try {
            VistaDocentesTomaAsistencia vt = new VistaDocentesTomaAsistencia();
            vt.setVisible(true);
            this.setVisible(false);
        } 
        catch (Exception e) {
            
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
      
        int fila = tblRegistroAsistencia.getSelectedRow();
    if (fila < 0) {
        JOptionPane.showMessageDialog(null, "Seleccione un registro", "Error", JOptionPane.WARNING_MESSAGE);
    } else {
        if (cbxMateria.getSelectedIndex() == -1 || DateFechaSeleccionada.getDate() == null || cbxHorario.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Por favor, complete la selección de materia, fecha y horario", "Error", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                boolean alMenosUnaModificacion = false;
                for (int i = 0; i < tblRegistroAsistencia.getRowCount(); i++) {
                    boolean estaPresente = (boolean) tblRegistroAsistencia.getValueAt(i, 4);
                    Alumno alumno = obtenerAlumnoDesdeTabla(i);

                    if (alumno != null) {
                        Date fechaAsistencia = DateFechaSeleccionada.getDate();
                        String fechaFormateada = Formato.format(fechaAsistencia);
                        Horario horarioSeleccionado = (Horario) cbxHorario.getSelectedItem();

                        Asistencia asistencia = new Asistencia();
                        asistencia.setIdAsistencia(fila + 1);
                        asistencia.setAlumnoAsistencia(alumno);
                        asistencia.setHorarioAsistencia(horarioSeleccionado);
                        asistencia.setEstadoAsistencia(estaPresente ? "Presente" : "Ausente");
                        asistencia.setFechaAsistencia(fechaFormateada);

                        asistenciaControlDao.Merge(asistencia, fila);

                        alMenosUnaModificacion = true;
                    }
                }
                if (alMenosUnaModificacion) {
                    JOptionPane.showMessageDialog(null, "Registros de asistencia modificados correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    Limpiar();
                    // Actualizar la tabla después de la modificación
                    btnVerRegistroActionPerformed(null);
                } else {
                    JOptionPane.showMessageDialog(null, "No se han realizado modificaciones", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al modificar los registros de asistencia", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
        
//        int fila = tblRegistroAsistencia.getSelectedRow();
//        if (fila < 0) {
//            JOptionPane.showMessageDialog(null, "Escoga un registro");
//        } 
//        else {
//            if (cbxMateria.getSelectedIndex() == -1) {
//                JOptionPane.showMessageDialog(null, "Falta seleccionar la materia", "Error", JOptionPane.WARNING_MESSAGE);
//            } 
//            else if (DateFechaSeleccionada.getDate() == null) {
//                JOptionPane.showMessageDialog(null, "Falta seleccionar la fecha", "Error", JOptionPane.WARNING_MESSAGE);
//            } 
//            else if (cbxHorario.getSelectedIndex() == -1) {
//                JOptionPane.showMessageDialog(null, "Falta seleccionar el horario", "Error", JOptionPane.WARNING_MESSAGE);
//            } 
//            else {
//                for (int i = 0; i < tblRegistroAsistencia.getRowCount(); i++) {
//                    boolean estaPresente = (boolean) tblRegistroAsistencia.getValueAt(i, 4);
//                    String estadoAsistencia = estaPresente ? "Presente" : "Ausente";
//                    Alumno alumno = obtenerAlumnoDesdeTabla(i);
//                    
//                    Date fechaAsistencia = DateFechaSeleccionada.getDate();
//                    String Fecha = Formato.format(fechaAsistencia);
//                    
//                    Asistencia asistenciaModificadad = new Asistencia();
//                    asistenciaModificadad.setIdAsistencia(fila+1);
//                    asistenciaModificadad.setAlumnoAsistencia(alumno);
//                    asistenciaModificadad.setHorarioAsistencia(UtilVista.obtenerHorarioControl(cbxHorario));
//                    asistenciaModificadad.setObservacion(asistenciaControlDao.getAsistencia().getObservacion());
//                    asistenciaModificadad.setEstadoAsistencia(estadoAsistencia);
//                    asistenciaModificadad.setFechaAsistencia(Fecha);
//                    asistenciaModificadad.setTematicaAsistencia(asistenciaControlDao.getAsistencia().getTematicaAsistencia());
//                    
//                    asistenciaControlDao.Merge(asistenciaModificadad, fila);
//                    
//                    CargarTablaAlumnos();
//                }
//                try {
//                    Limpiar();
//                } 
//                catch (Exception e) {
//                    
//                }
//            }
//        }

    }//GEN-LAST:event_btnModificarActionPerformed

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
            java.util.logging.Logger.getLogger(VistaGestionRegistroAsistencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaGestionRegistroAsistencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaGestionRegistroAsistencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaGestionRegistroAsistencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new VistaGestionRegistroAsistencias().setVisible(true);
                } 
                catch (ListaVacia ex) {
                    
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DateFechaSeleccionada;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnOrdenar;
    private javax.swing.JButton btnVerRegistro;
    private javax.swing.JComboBox<Object> cbxHorario;
    private javax.swing.JComboBox<Object> cbxMateria;
    private javax.swing.JComboBox<String> cbxOrden;
    private javax.swing.JComboBox<String> cbxTipoBusqueda;
    private javax.swing.JComboBox<String> cbxTipoOrden;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblRegistroAsistencia;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
