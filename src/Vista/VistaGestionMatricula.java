
package Vista;

import Controlador.Dao.Bridge;
import Controlador.Dao.Modelo.alumnoDao;
import Controlador.Dao.Modelo.matriculaDao;
import Controlador.TDA.ListaDinamica.Excepcion.ListaVacia;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Controlador.Utiles.UtilesControlador;
import Modelo.Alumno;
import Modelo.Matricula;
import Modelo.PeriodoAcademico;
import Vista.ModeloTabla.ModeloTablaMatriculas;
import Vista.Utiles.UtilVista;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


/**
 *
 * @author romer
 */
public class VistaGestionMatricula extends javax.swing.JFrame {
    ListaDinamica<Matricula> listaMatricula = new ListaDinamica<>();
    matriculaDao MatriculaControlDao = new matriculaDao();
    ModeloTablaMatriculas mtm = new ModeloTablaMatriculas(); 
    
    /**
     * Creates new form Matricula
     * @throws Controlador.TDA.ListaDinamica.Excepcion.ListaVacia
     */
    public VistaGestionMatricula() throws ListaVacia {
        initComponents();
        this.setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/Vista/RecursosGraficos/IconoPrograma.png")).getImage());
        UtilVista.cargarcomboPeriodo(cbxPeriodo);
        UtilVista.cargarcomboAlumnos(cbxAlumno);
        cbxEstadoMatricula.setEnabled(false);
        CargarTabla();
    }
    
    private void CargarTabla() {
        mtm.setMatriculas(MatriculaControlDao.all());
        tbMatriculas.setModel(mtm);
        tbMatriculas.updateUI();
        cbxAlumno.setSelectedIndex(-1);
        cbxPeriodo.setSelectedIndex(-1);
        cbxEstadoMatricula.setSelectedIndex(0);
    }
    
    private void Limpiar() throws ListaVacia {
        txtCodigoMatricula.setText("");
        cbxAlumno.setSelectedIndex(-1);
        cbxPeriodo.setSelectedIndex(-1);
        cbxEstadoMatricula.setSelectedIndex(-1);
        MatriculaControlDao.setMatricula(null);
        CargarTabla();
    }
    
    public Boolean verificar() {
        if (cbxAlumno.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un Cursa.");
            return false;
        }
        if (cbxPeriodo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un periodo académico.");
            return false;
        }
        return true;
    }
    
    private void Seleccionar(){
        int fila = tbMatriculas.getSelectedRow();
        if(fila < 0){
            JOptionPane.showMessageDialog(null, "Escoga un registro");
        }
        else{
            try {
                MatriculaControlDao.setMatricula(mtm.getMatriculas().getInfo(fila));
                
                cbxEstadoMatricula.setEnabled(true);
                
                txtCodigoMatricula.setText(MatriculaControlDao.getMatricula().getCodigoMatricula());
                cbxEstadoMatricula.setSelectedItem(MatriculaControlDao.getMatricula().getEstadoMatricula());
                cbxPeriodo.setSelectedIndex(MatriculaControlDao.getMatricula().getPeriodoAcademicoMatricula().getIdPeriodoAcademino() -1);
                cbxAlumno.setSelectedIndex(MatriculaControlDao.getMatricula().getAlumnoMatricula().getIdAlumno() -1);
                
            } 
            catch (Exception e) {
                
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    private String generarCodigo() throws ListaVacia {
        int ultimoId = 0;

        String presentacionURL = "Files" + File.separatorChar + "Matricula.json";

        try {
            ListaDinamica<Matricula> listaPresentacion = (ListaDinamica<Matricula>) Bridge.getConection().fromXML(new FileReader(presentacionURL));

            if (!listaPresentacion.EstaVacio()) {
                Matricula ultimaPresentacion = listaPresentacion.getInfo(listaPresentacion.getLongitud() - 1);
                ultimoId = ultimaPresentacion.getIdMatricula();
            }
        } 
        catch (FileNotFoundException e) {
        }

        ultimoId++;

        return "M-" + String.format("%04d", ultimoId);
    }
            
    private boolean matriculaExiste(Matricula nuevaMatricula) {
        ListaDinamica<Matricula> matriculas = MatriculaControlDao.all();
        if (matriculas.EstaVacio()) {
            return false;
        }
        for (Matricula m : matriculas.toArray()) {
            if (m.getAlumnoMatricula().getIdAlumno().equals(nuevaMatricula.getAlumnoMatricula().getIdAlumno())
                    && m.getPeriodoAcademicoMatricula().getIdPeriodoAcademino().equals(nuevaMatricula.getPeriodoAcademicoMatricula().getIdPeriodoAcademino())) {
                return true;
            }
        }
        return false;
    }

    private void Guardar() throws ListaVacia {
        if (cbxEstadoMatricula.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Falta seleccionar el estado", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else if (cbxPeriodo.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Falta seleccionar el periodo", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else if (cbxAlumno.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Falta seleccionar el alumno", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else {
            // Obtener datos de matrícula
            String estado = cbxEstadoMatricula.getSelectedItem().toString();
            PeriodoAcademico periodo = UtilVista.obtenerPeriodoControl(cbxPeriodo);
            Alumno alumno = UtilVista.obtenerAlumnosControl(cbxAlumno);

            Matricula nuevaMatricula = new Matricula();
            nuevaMatricula.setEstadoMatricula(estado);
            nuevaMatricula.setPeriodoAcademicoMatricula(periodo);
            nuevaMatricula.setAlumnoMatricula(alumno);

            if (matriculaExiste(nuevaMatricula)) {
                JOptionPane.showMessageDialog(null, "La matrícula ya existe para este alumno", "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            Integer idMatricula = listaMatricula.getLongitud() + 1;
            String codigo = generarCodigo();

            nuevaMatricula.setIdMatricula(idMatricula);
            nuevaMatricula.setCodigoMatricula(codigo);

            MatriculaControlDao.setMatricula(nuevaMatricula);
            try {
                if (MatriculaControlDao.persist()) {
                    JOptionPane.showMessageDialog(null, "Matrícula guardada exitosamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                    MatriculaControlDao.setMatricula(null);
                } 
                else {
                    JOptionPane.showMessageDialog(null, "No se pudo guardar la matrícula", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
            Limpiar();
        }
    }
    
    public Integer OrdenSeleccionado(){
        String OrdenO = cbxOrden.getSelectedItem().toString();

        if ("Asendente".equals(OrdenO)) {
            return 1;
        }
        if("Desendente".equals(OrdenO)){
            return 0;
        }
        return null;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cbxPeriodo = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        cbxAlumno = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbMatriculas = new javax.swing.JTable();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cbxTipoBusqueda = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cbxEstadoMatricula = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        txtCodigoMatricula = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtBusquedaAlumno = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        cbxOrden = new javax.swing.JComboBox<>();
        btnOrdenar = new javax.swing.JButton();
        cbxTipoOrden = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GESTION MATRICULA");

        jPanel4.setBackground(new java.awt.Color(190, 193, 197));

        jLabel8.setFont(new java.awt.Font("Candara Light", 1, 32)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Informacion");

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Periodo");

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Alumno");

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Estado");

        tbMatriculas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbMatriculas.setSelectionBackground(new java.awt.Color(200, 222, 180));
        tbMatriculas.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tbMatriculas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbMatriculasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbMatriculas);

        btnGuardar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Guardar.png"))); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnModificar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Modificar.png"))); // NOI18N
        btnModificar.setText("MODIFICAR");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Eliminar.png"))); // NOI18N
        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Regresar.png"))); // NOI18N
        jButton3.setText("REGRESAR");
        jButton3.setToolTipText("");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Buscar por:");

        cbxTipoBusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Codigo", "Fecha", "Estado", "Periodo", "DNI alumno", "Nombre alumno" }));
        cbxTipoBusqueda.setSelectedIndex(-1);

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Busqueda:");

        btnBuscar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Buscar.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(61, 90, 134));
        jPanel3.setPreferredSize(new java.awt.Dimension(0, 140));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/LojoUNL.png"))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Haettenschweiler", 0, 48)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("SERVICIO DE REGISTRO DE MATRICULAS");
        jLabel7.setToolTipText("");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(jLabel3)
                .addGap(27, 27, 27)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 606, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel10.setFont(new java.awt.Font("Candara Light", 1, 32)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Lista de matriculas");

        cbxEstadoMatricula.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activa", "Inactiva", "Suspendida" }));
        cbxEstadoMatricula.setSelectedIndex(-1);

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Codigo");

        txtCodigoMatricula.setEditable(false);
        txtCodigoMatricula.setEnabled(false);

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Alumno");

        txtBusquedaAlumno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBusquedaAlumnoKeyTyped(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Buscar.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Ordenar");

        cbxOrden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Asendente", "Desendente" }));
        cbxOrden.setSelectedIndex(-1);

        btnOrdenar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Ordenar.png"))); // NOI18N
        btnOrdenar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdenarActionPerformed(evt);
            }
        });

        cbxTipoOrden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Codigo", "Fecha", "Estado", "Periodo", "DNI alumno", "Nombre alumno" }));
        cbxTipoOrden.setSelectedIndex(-1);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addComponent(btnGuardar))
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCodigoMatricula)
                            .addComponent(cbxEstadoMatricula, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxPeriodo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                        .addComponent(txtBusquedaAlumno)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton1))
                                    .addComponent(cbxAlumno, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxTipoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 562, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxTipoOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOrdenar))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificar)))
                .addContainerGap())
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1200, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(jLabel10))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbxOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbxTipoOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel13))
                            .addComponent(btnOrdenar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel1)
                    .addComponent(cbxTipoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar)
                    .addComponent(jLabel9)
                    .addComponent(txtCodigoMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cbxEstadoMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(cbxPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel12)
                                .addComponent(txtBusquedaAlumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cbxAlumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminar)
                    .addComponent(btnModificar)
                    .addComponent(btnGuardar)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        
        try {
            if (cbxEstadoMatricula.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar el estado", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (cbxPeriodo.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar el periodo", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (cbxAlumno.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar el alumno", "Error", JOptionPane.WARNING_MESSAGE);
            }
            else {
                Guardar();
                cbxEstadoMatricula.setEnabled(false);
            }
        } 
        catch (Exception e) {
            
        }
        
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
        VistaPersonalAdministracion abrirLogin = new VistaPersonalAdministracion();
        abrirLogin.setVisible(true);
        this.setVisible(false);
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        
        try {
            if (cbxTipoBusqueda.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Porfavor seleccione donde quiere buscar", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else {
                ListaDinamica<Matricula> lista = MatriculaControlDao.all();

                String Campo = txtBuscar.getText();
                String TipoCampo = cbxTipoBusqueda.getSelectedItem().toString();

                switch (TipoCampo) {
                    case "Codigo":
                        TipoCampo = "CodigoMatricula";
                        break;
                    case "Fecha":
                        TipoCampo = "FechaMatricula";
                        break;
                    case "Estado":
                        TipoCampo = "EstadoMatricula";
                        break;
                    case "Periodo":
                        TipoCampo = "periodoAcademicoMatricula.FechaInicio";
                        break;
                    case "DNI alumno":
                        TipoCampo = "alumnoMatricula.DatosAlumno.NumeroCedula";
                        break;
                    case "Nombre alumno":
                        TipoCampo = "alumnoMatricula.DatosAlumno.Nombre";
                        break;
                    default:
                        throw new AssertionError();
                }

                ListaDinamica<Matricula> ResultadoBusqueda = UtilesControlador.BusquedaLineal(lista, Campo, TipoCampo);

                mtm.setMatriculas(ResultadoBusqueda);
                mtm.fireTableDataChanged();
            }
        } 
        catch (Exception e) {

        }
        
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void tbMatriculasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMatriculasMouseClicked
        
        Seleccionar();
        
    }//GEN-LAST:event_tbMatriculasMouseClicked

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        
        int fila = tbMatriculas.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Escoga un registro");
        } 
        else {
            if (cbxEstadoMatricula.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar el estado", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (cbxPeriodo.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar el periodo", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (cbxAlumno.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar el alumno", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else {

                Integer IdMatricula = MatriculaControlDao.getMatricula().getIdMatricula();
                String Codigo = txtCodigoMatricula.getText();
                String FechaMatricula = MatriculaControlDao.getMatricula().getFechaMatricula();
                String Estado = cbxEstadoMatricula.getSelectedItem().toString();

                Matricula matriculaModicada = new Matricula();
                matriculaModicada.setIdMatricula(IdMatricula);
                matriculaModicada.setCodigoMatricula(Codigo);
                matriculaModicada.setFechaMatricula(FechaMatricula);
                matriculaModicada.setEstadoMatricula(Estado);
                matriculaModicada.setAlumnoMatricula(UtilVista.obtenerAlumnosControl(cbxAlumno));
                matriculaModicada.setPeriodoAcademicoMatricula(UtilVista.obtenerPeriodoControl(cbxPeriodo));

                MatriculaControlDao.Merge(matriculaModicada, IdMatricula - 1);

                CargarTabla();
                cbxEstadoMatricula.setEnabled(false);

                try {
                    Limpiar();
                } 
                catch (Exception e) {

                }
            }
        }
        
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        
        int fila = tbMatriculas.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Escoga un registro");
        } 
        else {
            MatriculaControlDao.Eliminar(fila);
            CargarTabla();
        }
        
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        try {
            if (txtBusquedaAlumno.getText().isEmpty()) {
                UtilVista.cargarcomboAlumnos(cbxAlumno);
                JOptionPane.showMessageDialog(null, "Ingrese el alumno a buscar", "FALTA LLENAR", JOptionPane.WARNING_MESSAGE);
            } 
            else {
                alumnoDao PD = new alumnoDao();
                ListaDinamica<Alumno> lista = PD.all();

                String Campo = txtBusquedaAlumno.getText();

                ListaDinamica<Alumno> ResultadoBusqueda = new ListaDinamica<>();

                ListaDinamica<Alumno> ResultadoN = UtilesControlador.BusquedaLineal(lista, Campo, "DatosAlumno.NumeroCedula");
                ResultadoBusqueda.concatenar(ResultadoN);

                ListaDinamica<Alumno> ResultadoNO = UtilesControlador.BusquedaLineal(lista, Campo, "DatosAlumno.Nombre");
                ResultadoBusqueda.concatenar(ResultadoNO);
                
                ListaDinamica<Alumno> ResultadoA = UtilesControlador.BusquedaLineal(lista, Campo, "DatosAlumno.Apellido");
                ResultadoBusqueda.concatenar(ResultadoA);

                cbxAlumno.removeAllItems();

                for (Alumno pb : ResultadoBusqueda.toArray()) {
                    cbxAlumno.addItem(pb);
                }
            }
        } 
        catch (Exception e) {

        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtBusquedaAlumnoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaAlumnoKeyTyped
        
//        Character c = evt.getKeyChar();
//
//        if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
//            evt.consume();
//            JOptionPane.showMessageDialog(null, "Solo ingreso de numeros", "CARACTER NO VALIDO", JOptionPane.WARNING_MESSAGE);
//        }
//        if (txtBusquedaAlumno.getText().length() >= 10 && c != KeyEvent.VK_BACK_SPACE) {
//            evt.consume();
//        }
//        
    }//GEN-LAST:event_txtBusquedaAlumnoKeyTyped

    private void btnOrdenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdenarActionPerformed

        try {
            if (cbxTipoOrden.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "No ha seleccionado el campo", "FALTA SELCCIONAR", JOptionPane.WARNING_MESSAGE);
            } 
            else if (cbxOrden.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "No ha seleccionado el orden", "FALTA SELCCIONAR", JOptionPane.WARNING_MESSAGE);
            } 
            else {
                ListaDinamica<Matricula> lista = MatriculaControlDao.all();
                String TipoCampo = cbxTipoOrden.getSelectedItem().toString();

                switch (TipoCampo) {
                    case "Codigo":
                        TipoCampo = "CodigoMatricula";
                        break;
                    case "Fecha":
                        TipoCampo = "FechaMatricula";
                        break;
                    case "Estado":
                        TipoCampo = "EstadoMatricula";
                        break;
                    case "Periodo":
                        TipoCampo = "periodoAcademicoMatricula.FechaInicio";
                        break;
                    case "DNI alumno":
                        TipoCampo = "alumnoMatricula.DatosAlumno.NumeroCedula";
                        break;
                    case "Nombre alumno":
                        TipoCampo = "alumnoMatricula.DatosAlumno.Nombre";
                        break;
                    default:
                        throw new AssertionError();
                }

                Integer orden = OrdenSeleccionado();

                ListaDinamica<Matricula> resultadoOrdenado = UtilesControlador.QuickSort(lista, orden, TipoCampo);

                mtm.setMatriculas(resultadoOrdenado);
                mtm.fireTableDataChanged();
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }//GEN-LAST:event_btnOrdenarActionPerformed

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
            java.util.logging.Logger.getLogger(VistaGestionMatricula.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaGestionMatricula.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaGestionMatricula.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaGestionMatricula.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new VistaGestionMatricula().setVisible(true);
                } 
                catch (ListaVacia ex) {
                    
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnOrdenar;
    private javax.swing.JComboBox<Object> cbxAlumno;
    private javax.swing.JComboBox<String> cbxEstadoMatricula;
    private javax.swing.JComboBox<String> cbxOrden;
    private javax.swing.JComboBox<String> cbxPeriodo;
    private javax.swing.JComboBox<String> cbxTipoBusqueda;
    private javax.swing.JComboBox<String> cbxTipoOrden;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbMatriculas;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtBusquedaAlumno;
    private javax.swing.JTextField txtCodigoMatricula;
    // End of variables declaration//GEN-END:variables
}
