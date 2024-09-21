
package Vista;

import Controlador.Dao.Modelo.carreraDao;
import Controlador.TDA.ListaDinamica.Excepcion.ListaVacia;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Controlador.Utiles.UtilesControlador;
import Vista.ModeloTabla.ModeloTablaCarrera;
import Modelo.Carrera;
import Modelo.Facultad;
import Vista.Utiles.UtilVista;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Victor
 */
public class VistaGestionCarrera extends javax.swing.JFrame {
    carreraDao carreraControlDao = new carreraDao();
    ListaDinamica<Carrera> listaCarreras = new ListaDinamica<>();
    ModeloTablaCarrera mtc = new ModeloTablaCarrera();

    /**
     * Creates new form VistaGestionCarrera
     * @throws Controlador.TDA.ListaDinamica.Excepcion.ListaVacia
     */
    public VistaGestionCarrera() throws ListaVacia {
        initComponents();
        this.setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/Vista/RecursosGraficos/IconoPrograma.png")).getImage());
        
        UtilVista.CargarComboFacultad(cbxFacultad);
        CargarTabla();
    }
    
    private void CargarTabla() {
        mtc.setCarreraTabla(carreraControlDao.getListaCarreras());
        tblCarreras.setModel(mtc);
        tblCarreras.updateUI();
//        cbxMalla.setSelectedIndex(-1);
        cbxFacultad.setSelectedIndex(-1);
        cbxTipoBusqueda.setSelectedIndex(-1);
    }
    
    private void Limpiar() throws ListaVacia {
        txtNombreCarrera.setText("");
        txtDuracionCarrera.setText("");
        txtNumeroCiclos.setText("");
//        cbxMalla.setSelectedIndex(-1);
        cbxFacultad.setSelectedIndex(-1);
//        cbxTipoBusqueda.setSelectedIndex(-1);
        carreraControlDao.setCarreras(null);
        CargarTabla();
    }

    private void Seleccionar() {
        int fila = tblCarreras.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Escoga un registro");
        } 
        else {
            try {
                carreraControlDao.setCarreras(mtc.getCarreraTabla().getInfo(fila));

                txtNombreCarrera.setText(carreraControlDao.getCarreras().getNombreCarrera());
                txtDuracionCarrera.setText(carreraControlDao.getCarreras().getDuracion().toString());
                txtNumeroCiclos.setText(carreraControlDao.getCarreras().getNumeroCiclos().toString());
                cbxFacultad.setSelectedIndex(carreraControlDao.getCarreras().getFacutadCarrera().getIdFacultad() - 1);

            } 
            catch (Exception e) {

            }
        }
    }

    private boolean carreraExiste(Carrera nuevaCarrera) {
        ListaDinamica<Carrera> carreras = carreraControlDao.getListaCarreras();
        if (carreras.EstaVacio()) {
            return false;
        }
        for (Carrera c : carreras.toArray()) {
            if (c.getNombreCarrera().equals(nuevaCarrera.getNombreCarrera())
                    && c.getDuracion().equals(nuevaCarrera.getDuracion())
                    && c.getNumeroCiclos().equals(nuevaCarrera.getNumeroCiclos())
                    && c.getFacutadCarrera().getIdFacultad().equals(nuevaCarrera.getFacutadCarrera().getIdFacultad())) {
                return true;
            }
        }
        return false;
    }

    private void Guardar() throws ListaVacia {
        if (cbxFacultad.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Falta seleccionar la facultad", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else if (txtNombreCarrera.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Falta llenar nombre de la carrera", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else if (txtDuracionCarrera.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Falta llenar duracion", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else if (txtNumeroCiclos.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Falta llenar el numero de ciclos", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else {
            //Datos de carrera
            String nombre = txtNombreCarrera.getText();
            Integer duracion = Integer.valueOf(txtDuracionCarrera.getText());
            Integer numeroCiclos = Integer.valueOf(txtNumeroCiclos.getText());

            Facultad facultadCarrera = UtilVista.obtenerFacultadControl(cbxFacultad);

            Carrera nuevaCarrera = new Carrera();
            nuevaCarrera.setNombreCarrera(nombre);
            nuevaCarrera.setDuracion(duracion);
            nuevaCarrera.setNumeroCiclos(numeroCiclos);
            nuevaCarrera.setFacutadCarrera(facultadCarrera);

            if (carreraExiste(nuevaCarrera)) {
                JOptionPane.showMessageDialog(null, "La carrera ya existe", "CARRERA EXISTENTE", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            Integer idCarrera = listaCarreras.getLongitud() + 1;
            nuevaCarrera.setIdCarrera(idCarrera);

            try {
                carreraControlDao.setCarreras(nuevaCarrera);
                if (carreraControlDao.Persist()) {
                    JOptionPane.showMessageDialog(null, "CARRERA GUARDADA EXITOSAMENTE", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
                    carreraControlDao.setCarreras(null);
                }
                else {
                    JOptionPane.showMessageDialog(null, "NO SE PUEDE REGISTRAR", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
                }
                Limpiar();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
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
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNombreCarrera = new javax.swing.JTextField();
        txtDuracionCarrera = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCarreras = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cbxTipoBusqueda = new javax.swing.JComboBox<>();
        txtBuscar = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtNumeroCiclos = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        cbxFacultad = new javax.swing.JComboBox<>();
        btnOrdenar = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        cbxOrden = new javax.swing.JComboBox<>();
        cbxTipoOrden = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GESTION DE CARRERAS");

        jPanel1.setBackground(new java.awt.Color(191, 192, 197));
        jPanel1.setToolTipText("");

        jPanel2.setBackground(new java.awt.Color(61, 90, 134));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/LojoUNL.png"))); // NOI18N

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Haettenschweiler", 0, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("SERVICIO DE ADMINISTRACION DE CARRERAS");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(jLabel2)
                .addContainerGap(230, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Nombre");

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Duracion");

        txtNombreCarrera.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreCarreraKeyTyped(evt);
            }
        });

        txtDuracionCarrera.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDuracionCarreraKeyTyped(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Candara Light", 1, 32)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Informacion");

        jLabel3.setFont(new java.awt.Font("Candara Light", 1, 32)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Lista de carreras");

        tblCarreras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblCarreras.setSelectionBackground(new java.awt.Color(200, 222, 180));
        tblCarreras.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tblCarreras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCarrerasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCarreras);

        jButton2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Modificar.png"))); // NOI18N
        jButton2.setText("MODIFICAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Eliminar.png"))); // NOI18N
        jButton3.setText("ELIMINAR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Guardar.png"))); // NOI18N
        jButton4.setText("GUARDAR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Regresar.png"))); // NOI18N
        jButton5.setText("REGRESAR");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Buscar por");

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Buscar");

        cbxTipoBusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nombre", "Duracion", "Numero de ciclos", "Facultad" }));
        cbxTipoBusqueda.setSelectedIndex(-1);

        jButton6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Buscar.png"))); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Nro de ciclos");

        txtNumeroCiclos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroCiclosKeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Facultad");

        cbxFacultad.setMaximumSize(new java.awt.Dimension(32767, 38767));

        btnOrdenar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Ordenar.png"))); // NOI18N
        btnOrdenar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdenarActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Ordenar");

        cbxOrden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Asendente", "Desendente" }));
        cbxOrden.setSelectedIndex(-1);

        cbxTipoOrden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nombre", "Duracion", "Numero de ciclos", "Facultad" }));
        cbxTipoOrden.setSelectedIndex(-1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxFacultad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombreCarrera))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDuracionCarrera))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNumeroCiclos)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxTipoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(txtBuscar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton6))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxTipoOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOrdenar))
                    .addComponent(jScrollPane1))
                .addContainerGap())
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel6))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnOrdenar)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbxOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbxTipoOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel12)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(cbxTipoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)
                        .addComponent(cbxFacultad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtNombreCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtDuracionCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtNumeroCiclos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton5)
                    .addComponent(jButton4))
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

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        
        VistaPersonalAdministracion abrirLogin = new VistaPersonalAdministracion();
        abrirLogin.setVisible(true);
        this.setVisible(false);
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
        try {
            if (cbxFacultad.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar la facultad", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (txtNombreCarrera.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar nombre de la carrera", "Error", JOptionPane.WARNING_MESSAGE);
            }
            else if (txtDuracionCarrera.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar duracion", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (txtNumeroCiclos.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar el numero de ciclos", "Error", JOptionPane.WARNING_MESSAGE);
            } 
//            else if (cbxMalla.getSelectedIndex() == -1) {
//                JOptionPane.showMessageDialog(null, "Falta seleccionar la malla", "Error", JOptionPane.ERROR_MESSAGE);
//            } 
            else {
                Guardar();
            }
        } 
        catch (Exception e) {

        }
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        int fila = tblCarreras.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Escoga un registro");
        } 
        else {
            if (cbxFacultad.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar la facultad", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (txtNombreCarrera.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar nombre de la carrera", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (txtDuracionCarrera.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar duracion", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (txtNumeroCiclos.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar el numero de ciclos", "Error", JOptionPane.WARNING_MESSAGE);
            }
            else {
                Integer IdCarrera = carreraControlDao.getCarreras().getIdCarrera();
                String Nombre = txtNombreCarrera.getText();
                Integer Duracion = Integer.valueOf(txtDuracionCarrera.getText());
                Integer Nc = Integer.valueOf(txtNumeroCiclos.getText());

                Carrera carreraModificada = new Carrera();
                carreraModificada.setIdCarrera(IdCarrera);
                carreraModificada.setNombreCarrera(Nombre);
                carreraModificada.setDuracion(Duracion);
                carreraModificada.setNumeroCiclos(Nc);
                carreraModificada.setFacutadCarrera(UtilVista.obtenerFacultadControl(cbxFacultad));

                carreraControlDao.Merge(carreraModificada, IdCarrera - 1);

                CargarTabla();

                try {
                    Limpiar();
                } 
                catch (ListaVacia ex) {

                }
            }
        }
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
        int fila = tblCarreras.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Escoga un registro");
        } 
        else {
            carreraControlDao.Eliminar(fila);
            CargarTabla();
        }
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
    
        try {
            if (cbxTipoBusqueda.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Por favor seleccione donde quiere buscar", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else {
                ListaDinamica<Carrera> lista = carreraControlDao.all();

                String Campo = txtBuscar.getText();
                String TipoCampo = cbxTipoBusqueda.getSelectedItem().toString();

                switch (TipoCampo) {
                    case "Nombre":
                        TipoCampo = "NombreCarrera";
                        break;
                    case "Duracion":
                        TipoCampo = "Duracion";
                        break;
                    case "Numero de ciclos":
                        TipoCampo = "NumeroCiclos";
                        break;
                    case "Facultad":
                        TipoCampo = "facutadCarrera.NombreFacultad";
                        break;
                    default:
                        throw new AssertionError();
                }
                ListaDinamica<Carrera> ResultadoBusqueda;

                if (Campo.isEmpty()) {
                    ResultadoBusqueda = lista;
                } 
                else {
                    ResultadoBusqueda = UtilesControlador.BusquedaLineal(lista, Campo, TipoCampo);
                }

                mtc.setCarreraTabla(ResultadoBusqueda);
                mtc.fireTableDataChanged();
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }//GEN-LAST:event_jButton6ActionPerformed

    private void tblCarrerasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCarrerasMouseClicked
        
        Seleccionar();
        
    }//GEN-LAST:event_tblCarrerasMouseClicked

    private void txtNombreCarreraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreCarreraKeyTyped
        
        char c = evt.getKeyChar();

        if (!Character.isLetter(c) && c != KeyEvent.VK_BACK_SPACE && c != ' ') {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Solo ingreso de letras", "CARACTER NO VALIDO", JOptionPane.WARNING_MESSAGE);
        }
        if (c != KeyEvent.VK_BACK_SPACE) {

        }
        
    }//GEN-LAST:event_txtNombreCarreraKeyTyped

    private void txtDuracionCarreraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDuracionCarreraKeyTyped
        
        Character c = evt.getKeyChar();

        if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Solo ingreso de numeros", "CARACTER NO VALIDO", JOptionPane.WARNING_MESSAGE);
        }
        
    }//GEN-LAST:event_txtDuracionCarreraKeyTyped

    private void btnOrdenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdenarActionPerformed

        try {
            if (cbxTipoOrden.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "No ha seleccionado el campo", "FALTA SELCCIONAR", JOptionPane.WARNING_MESSAGE);
            } 
            else if (cbxOrden.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "No ha seleccionado el orden", "FALTA SELCCIONAR", JOptionPane.WARNING_MESSAGE);
            } 
            else {
                ListaDinamica<Carrera> lista = carreraControlDao.all();
                String TipoCampo = cbxTipoOrden.getSelectedItem().toString();

                switch (TipoCampo) {
                    case "Nombre":
                        TipoCampo = "NombreCarrera";
                        break;
                    case "Duracion":
                        TipoCampo = "Duracion";
                        break;
                    case "Numero de ciclos":
                        TipoCampo = "NumeroCiclos";
                        break;
                    case "Facultad":
                        TipoCampo = "facutadCarrera.NombreFacultad";
                        break;
                    default:
                        throw new AssertionError();
                }

                Integer orden = OrdenSeleccionado();

                ListaDinamica<Carrera> resultadoOrdenado = UtilesControlador.QuickSort(lista, orden, TipoCampo);

                mtc.setCarreraTabla(resultadoOrdenado);
                mtc.fireTableDataChanged();
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_btnOrdenarActionPerformed

    private void txtNumeroCiclosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroCiclosKeyTyped
        
        Character c = evt.getKeyChar();

        if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Solo ingreso de numeros", "CARACTER NO VALIDO", JOptionPane.WARNING_MESSAGE);
        }
        
    }//GEN-LAST:event_txtNumeroCiclosKeyTyped

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
            java.util.logging.Logger.getLogger(VistaGestionCarrera.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaGestionCarrera.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaGestionCarrera.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaGestionCarrera.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new VistaGestionCarrera().setVisible(true);
                }
                catch (Exception e) {
                    
                }
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOrdenar;
    private javax.swing.JComboBox<String> cbxFacultad;
    private javax.swing.JComboBox<String> cbxOrden;
    private javax.swing.JComboBox<String> cbxTipoBusqueda;
    private javax.swing.JComboBox<String> cbxTipoOrden;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblCarreras;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtDuracionCarrera;
    private javax.swing.JTextField txtNombreCarrera;
    private javax.swing.JTextField txtNumeroCiclos;
    // End of variables declaration//GEN-END:variables
}
