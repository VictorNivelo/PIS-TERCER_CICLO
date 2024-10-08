
package Vista;

import Controlador.Dao.Modelo.alumnoDao;
import Controlador.Dao.Modelo.personaDao;
import Controlador.TDA.ListaDinamica.Excepcion.ListaVacia;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Controlador.Utiles.UtilesControlador;
import Modelo.Alumno;
import Modelo.Persona;
import Vista.ModeloTabla.ModeloTablaAlumnos;
import Vista.Utiles.UtilVista;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Victor
 */
public class VistaGestionAlumnos extends javax.swing.JFrame {
    alumnoDao alumnoControlDao = new alumnoDao();
    ListaDinamica<Alumno> listaAlumnos = new ListaDinamica<>();
    ModeloTablaAlumnos mta = new ModeloTablaAlumnos(); 

    /**
     * Creates new form VistaGestionAlumnos
     * @throws Controlador.TDA.ListaDinamica.Excepcion.ListaVacia
     */
    public VistaGestionAlumnos() throws ListaVacia {
        initComponents();
        this.setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/Vista/RecursosGraficos/IconoPrograma.png")).getImage());
        UtilVista.cargarcomboPersonaAlumnos(cbxAlumno);
        cbxEstado.setEnabled(false);
        cbxAlumno.setMaximumRowCount(cbxAlumno.getItemCount());
        CargarTabla();
    }
    
    private void CargarTabla() {
        mta.setAlumnosTabla(alumnoControlDao.getListaAlumnos());
        tblAlumnos.setModel(mta);
        tblAlumnos.updateUI();
        cbxAlumno.setSelectedIndex(-1);
        cbxEstado.setSelectedIndex(0);
        cbxTipoBusqueda.setSelectedIndex(-1);
    }
    
    private void Limpiar() throws ListaVacia {
        cbxAlumno.setSelectedIndex(-1);
        cbxEstado.setSelectedIndex(-1);
        cbxTipoBusqueda.setSelectedIndex(-1);
        alumnoControlDao.setAlumnos(null);
        CargarTabla();
    }
    
    private void Seleccionar(){
        int fila = tblAlumnos.getSelectedRow();
        if(fila < 0){
            JOptionPane.showMessageDialog(null, "Escoga un registro");
        }
        else{
            try {
                cbxEstado.setEnabled(true);
                alumnoControlDao.setAlumnos(mta.getAlumnosTabla().getInfo(fila));
                
                cbxAlumno.setSelectedIndex(alumnoControlDao.getAlumnos().getIdAlumno()-1);
                cbxEstado.setSelectedItem(alumnoControlDao.getAlumnos().getEstadoAlumno());
                
            } 
            catch (Exception e) {
                
            }
        }
    }
    
    private boolean alumnoExiste(Alumno nuevoAlumno) {
        ListaDinamica<Alumno> alumnos = alumnoControlDao.getListaAlumnos();
        if (alumnos.EstaVacio()) {
            return false;
        }
        for (Alumno a : alumnos.toArray()) {
            if (a.getIdAlumno().equals(nuevoAlumno.getIdAlumno())) {
                return true;
            }
        }
        return false;
    }

    private void Guardar() throws ListaVacia {
        if (cbxAlumno.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Falta seleccionar un alumno", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else if (cbxEstado.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Falta seleccionar el estado del alumno", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else {
            String estado = cbxEstado.getSelectedItem().toString();
            Persona datosAlumno = UtilVista.obtenerPersonaAlumnosControl(cbxAlumno);

            Alumno nuevoAlumno = new Alumno();
            nuevoAlumno.setDatosAlumno(datosAlumno);
            nuevoAlumno.setEstadoAlumno(estado);

            if (alumnoExiste(nuevoAlumno)) {
                JOptionPane.showMessageDialog(null, "El alumno ya existe", "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            alumnoControlDao.setAlumnos(nuevoAlumno);
            try {
                if (alumnoControlDao.Persist()) {
                    JOptionPane.showMessageDialog(null, "Alumno guardado exitosamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                    alumnoControlDao.setAlumnos(null);
                } 
                else {
                    JOptionPane.showMessageDialog(null, "No se pudo guardar el alumno", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
            Limpiar();
            cbxEstado.setEnabled(false);
        }
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
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbxAlumno = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cbxTipoBusqueda = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAlumnos = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        cbxEstado = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        txtAlumnoBusqueda = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        cbxOrden = new javax.swing.JComboBox<>();
        btnOrdenar = new javax.swing.JButton();
        cbxTipoOrden = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GESTION DE ALUMNOS");

        jPanel1.setBackground(new java.awt.Color(191, 192, 197));

        jPanel2.setBackground(new java.awt.Color(61, 90, 134));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/LojoUNL.png"))); // NOI18N

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Haettenschweiler", 0, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("SERVICIO DE GESTION DE ALUMNOS");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(104, 104, 104)
                .addComponent(jLabel2)
                .addContainerGap(321, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel3.setFont(new java.awt.Font("Candara Light", 1, 32)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Alumno");

        jButton1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Regresar.png"))); // NOI18N
        jButton1.setText("REGRESAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Alumno");

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Estado");

        jButton2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Guardar.png"))); // NOI18N
        jButton2.setText("GUARDAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Candara Light", 1, 32)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Lista de alumnos");

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Buscar por");

        cbxTipoBusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Numero de cedula", "Nombre", "Apellido", "Genero", "Estado", "Telefono", "Correo", "Codido matricula" }));
        cbxTipoBusqueda.setSelectedIndex(-1);

        jButton3.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Buscar.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Busqueda");

        tblAlumnos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblAlumnos.setSelectionBackground(new java.awt.Color(200, 222, 180));
        tblAlumnos.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tblAlumnos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAlumnosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblAlumnos);

        jButton4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Modificar.png"))); // NOI18N
        jButton4.setText("MODIFICAR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Eliminar.png"))); // NOI18N
        jButton5.setText("ELIMINAR");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        cbxEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cursando", "Retirado" }));
        cbxEstado.setSelectedIndex(-1);

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Alumno");

        txtAlumnoBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAlumnoBusquedaKeyTyped(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Buscar.png"))); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Ordenar");

        cbxOrden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Asendente", "Desendente" }));
        cbxOrden.setSelectedIndex(-1);

        btnOrdenar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Ordenar.png"))); // NOI18N
        btnOrdenar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdenarActionPerformed(evt);
            }
        });

        cbxTipoOrden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Numero de cedula", "Nombre", "Apellido", "Genero", "Estado", "Telefono", "Correo", "Codido matricula" }));
        cbxTipoOrden.setSelectedIndex(-1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxEstado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxAlumno, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAlumnoBusqueda)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxTipoOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxTipoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBuscar)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnOrdenar, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
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
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(jLabel8))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbxOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbxTipoOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9)
                                .addComponent(cbxTipoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10)
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel11)
                                .addComponent(txtAlumnoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnOrdenar)
                        .addGap(34, 34, 34))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addGap(1, 1, 1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cbxAlumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
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
        
        VistaPersonalAdministracion abrirLogin = new VistaPersonalAdministracion();
        abrirLogin.setVisible(true);
        this.setVisible(false);
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
        try {
            if (cbxTipoBusqueda.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Porfavor seleccione donde quiere buscar", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else {
                ListaDinamica<Alumno> lista = alumnoControlDao.all();

                String Campo = txtBuscar.getText();
                String TipoCampo = cbxTipoBusqueda.getSelectedItem().toString();

                switch (TipoCampo) {
                    case "Numero de cedula":
                        TipoCampo = "DatosAlumno.NumeroCedula";
                        break;
                    case "Nombre":
                        TipoCampo = "DatosAlumno.Nombre";
                        break;
                    case "Apellido":
                        TipoCampo = "DatosAlumno.Apellido";
                        break;
                    case "Genero":
                        TipoCampo = "DatosAlumno.Genero";
                        break;
                    case "Telefono":
                        TipoCampo = "DatosAlumno.Telefono";
                        break;
                    case "Estado":
                        TipoCampo = "EstadoAlumno";
                        break;
                    case "Correo":
                        TipoCampo = "DatosAlumno.cuentaPersona.Correo";
                        break;
                    case "Codigo de matricula":
                        TipoCampo = "matriculaAlumno.CodigoMatricula";
                        break;
                    default:
                        throw new AssertionError();
                }

                ListaDinamica<Alumno> ResultadoBusqueda = UtilesControlador.BusquedaLineal(lista, Campo, TipoCampo);

                mta.setAlumnosTabla(ResultadoBusqueda);
                mta.fireTableDataChanged();
            }
        } 
        catch (Exception e) {

        }
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tblAlumnosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAlumnosMouseClicked
        
        Seleccionar();
        
    }//GEN-LAST:event_tblAlumnosMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        try {
            if (cbxAlumno.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar alumno", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (cbxEstado.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar estado", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else {
                Guardar();
            }
        } 
        catch (Exception e) {
            
        }
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
        int fila = tblAlumnos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Escoga un registro","REGISTRO NO SELECCIONADO",JOptionPane.WARNING_MESSAGE);
        } 
        else {
            if (cbxAlumno.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar alumno", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (cbxEstado.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar estado", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else {
                Integer IdAlumno = alumnoControlDao.getAlumnos().getIdAlumno();
                String Estado = cbxEstado.getSelectedItem().toString();
                
                Alumno alumnoModificado = new Alumno();
                alumnoModificado.setIdAlumno(IdAlumno);
                alumnoModificado.setDatosAlumno(UtilVista.obtenerPersonaAlumnosControl(cbxAlumno));
                alumnoModificado.setEstadoAlumno(Estado);
                
                alumnoControlDao.Merge(alumnoModificado, IdAlumno-1);

                CargarTabla();
                
                cbxEstado.setEnabled(false);

                try {
                    Limpiar();
                } 
                catch (Exception e) {

                }
            }
        }
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        
        int fila = tblAlumnos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Escoga un registro","REGISTRO NO SELECCIONADO",JOptionPane.WARNING_MESSAGE);
        }
        else {
            alumnoControlDao.Eliminar(fila);
            CargarTabla();
        }
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        
        try {
            if (txtAlumnoBusqueda.getText().isEmpty()) {
                UtilVista.cargarcomboPersonaAlumnos(cbxAlumno);
                JOptionPane.showMessageDialog(null, "Ingrese el alumno a buscar", "FALTA LLENAR", JOptionPane.WARNING_MESSAGE);
            } 
            else {
                personaDao PD = new personaDao();
                ListaDinamica<Persona> lista = PD.all();

                String Campo = txtAlumnoBusqueda.getText();

                ListaDinamica<Persona> ResultadoBusqueda = new ListaDinamica<>();

                ListaDinamica<Persona> ResultadoCe = UtilesControlador.BusquedaLineal(lista, Campo, "NumeroCedula");
                ResultadoBusqueda.concatenar(ResultadoCe);

                ListaDinamica<Persona> ResultadoN = UtilesControlador.BusquedaLineal(lista, Campo, "Nombre");
                ResultadoBusqueda.concatenar(ResultadoN);
                
                ListaDinamica<Persona> ResultadoA = UtilesControlador.BusquedaLineal(lista, Campo, "Apellido");
                ResultadoBusqueda.concatenar(ResultadoA);

                cbxAlumno.removeAllItems();

                for (Persona pb : ResultadoBusqueda.toArray()) {
                    if (pb.getRolPersona().getNombreRol().equals("Estudiante")) {
                        cbxAlumno.addItem(pb);
                    }
                }
            }
        } 
        catch (Exception e) {

        }
        
    }//GEN-LAST:event_jButton6ActionPerformed

    private void txtAlumnoBusquedaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAlumnoBusquedaKeyTyped
        
//        Character c = evt.getKeyChar();
//
//        if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
//            evt.consume();
//            JOptionPane.showMessageDialog(null, "Solo ingreso de numeros", "CARACTER NO VALIDO", JOptionPane.WARNING_MESSAGE);
//        }
//        if (txtAlumnoBusqueda.getText().length() >= 10 && c != KeyEvent.VK_BACK_SPACE) {
//            evt.consume();
//        }
        
    }//GEN-LAST:event_txtAlumnoBusquedaKeyTyped

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
                String TipoCampo = cbxTipoOrden.getSelectedItem().toString();

                switch (TipoCampo) {
                    case "Numero de cedula":
                        TipoCampo = "DatosAlumno.NumeroCedula";
                        break;
                    case "Nombre":
                        TipoCampo = "DatosAlumno.Nombre";
                        break;
                    case "Apellido":
                        TipoCampo = "DatosAlumno.Apellido";
                        break;
                    case "Genero":
                        TipoCampo = "DatosAlumno.Genero";
                        break;
                    case "Telefono":
                        TipoCampo = "DatosAlumno.Telefono";
                        break;
                    case "Estado":
                        TipoCampo = "EstadoAlumno";
                        break;
                    case "Correo":
                        TipoCampo = "DatosAlumno.cuentaPersona.Correo";
                        break;
                    case "Codigo de matricula":
                        TipoCampo = "matriculaAlumno.CodigoMatricula";
                        break;
                    default:

                }

                Integer orden = OrdenSeleccionado();

                ListaDinamica<Alumno> resultadoOrdenado = UtilesControlador.QuickSort(lista, orden, TipoCampo);

                mta.setAlumnosTabla(resultadoOrdenado);
                mta.fireTableDataChanged();
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
            java.util.logging.Logger.getLogger(VistaGestionAlumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaGestionAlumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaGestionAlumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaGestionAlumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new VistaGestionAlumnos().setVisible(true);
                } 
                catch (ListaVacia ex) {

                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOrdenar;
    private javax.swing.JComboBox<Object> cbxAlumno;
    private javax.swing.JComboBox<String> cbxEstado;
    private javax.swing.JComboBox<String> cbxOrden;
    private javax.swing.JComboBox<String> cbxTipoBusqueda;
    private javax.swing.JComboBox<String> cbxTipoOrden;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblAlumnos;
    private javax.swing.JTextField txtAlumnoBusqueda;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
