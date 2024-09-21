
package Vista;

import Controlador.Dao.Modelo.universidadDao;
import Controlador.TDA.ListaDinamica.Excepcion.ListaVacia;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Controlador.Utiles.UtilesControlador;
import Modelo.Universidad;
import Vista.ModeloTabla.ModeloTablaUniversidad;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Victor
 */
public class VistaGestionUniversidad extends javax.swing.JFrame {
    universidadDao universidadControlDao = new universidadDao();
    ModeloTablaUniversidad mtu = new ModeloTablaUniversidad();
    ListaDinamica<Universidad> listaUniversidades = new ListaDinamica<>();
    SimpleDateFormat Formato = new SimpleDateFormat("dd/MMMM/yyyy");
    
//    universidadDaoBD universidadControlDao = new universidadDaoBD();

    /**
     * Creates new form VistaGestionUniversidad
     */
    public VistaGestionUniversidad() {
        initComponents();
        this.setLocationRelativeTo(null);
        DateFundacion.setDateFormatString("dd/MMMM/yyyy");
        setIconImage(new ImageIcon(getClass().getResource("/Vista/RecursosGraficos/IconoPrograma.png")).getImage());
        CargarTabla();
    }
    
    private void CargarTabla(){
        mtu.setUniversidadTabla(universidadControlDao.getListaUniversid());
        tblUniversidades.setModel(mtu);
        tblUniversidades.updateUI();
        cbxTipoBusqueda.setSelectedIndex(-1);
    }
    
    public void Limpiar() {
        txtCorreo.setText("");
        txtDireccionU.setText("");
        txtNombreU.setText("");
        txtTelefono.setText("");
        DateFundacion.setDate(null);
        universidadControlDao.setUniversidad(null);
        CargarTabla();
    }
    
    private void Seleccionar(){
        int fila = tblUniversidades.getSelectedRow();
        if(fila < 0){
            JOptionPane.showMessageDialog(null, "Escoga un registro");
        }
        else{
            try {
                universidadControlDao.setUniversidad(mtu.getUniversidadTabla().getInfo(fila));
                
                Date Inicio = Formato.parse(universidadControlDao.getUniversidad().getFechaFU());
                DateFundacion.setDate(Inicio);
                txtNombreU.setText(universidadControlDao.getUniversidad().getNombreU());
                txtDireccionU.setText(universidadControlDao.getUniversidad().getDireccionU());
                txtTelefono.setText(universidadControlDao.getUniversidad().getTelefonoU());
                txtCorreo.setText(universidadControlDao.getUniversidad().getCorreoU());
                
            } 
            catch (Exception e) {
                
            }
        }
    }
    
    private boolean universidadExiste(Universidad nuevaUniversidad) {
        ListaDinamica<Universidad> universidades = universidadControlDao.getListaUniversid();
        if (universidades.EstaVacio()) {
            return false;
        }
        for (Universidad u : universidades.toArray()) {
            if (u.getNombreU().equals(nuevaUniversidad.getNombreU()) && u.getDireccionU().equals(nuevaUniversidad.getDireccionU())
                    && u.getTelefonoU().equals(nuevaUniversidad.getTelefonoU()) && u.getCorreoU().equals(nuevaUniversidad.getCorreoU())
                    && u.getFechaFU().equals(nuevaUniversidad.getFechaFU())) {
                return true;
            }
        }
        return false;
    }
    
    private void Guardar() throws ListaVacia, Exception {
        
        Date fechaNacimiento = DateFundacion.getDate();
        if (txtNombreU.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Falta llenar el nombre de la universidad", "Error", JOptionPane.WARNING_MESSAGE);
        }
        else if (txtDireccionU.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Falta llenar la direccion", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else if (txtTelefono.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Falta llenar el numero de telefono", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else if (txtCorreo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Falta llenar el correo de la universidad", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else if (DateFundacion.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Falta llenar fecha de fundacion", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else if (!validarFechaNoFutura(fechaNacimiento)) {
            JOptionPane.showMessageDialog(null, "La fecha de fundacion no puede ser futura", "Error", JOptionPane.WARNING_MESSAGE);
        }
        else {
            
            String nombre = txtNombreU.getText();
            String direccion = txtDireccionU.getText();
            String telefono = txtTelefono.getText();
            String correo = txtCorreo.getText();
            String fechaFundacion = Formato.format(DateFundacion.getDate());

            Universidad nuevaUniversidad = new Universidad();
            nuevaUniversidad.setNombreU(nombre);
            nuevaUniversidad.setDireccionU(direccion);
            nuevaUniversidad.setTelefonoU(telefono);
            nuevaUniversidad.setCorreoU(correo);
            nuevaUniversidad.setFechaFU(fechaFundacion);

            if (universidadExiste(nuevaUniversidad)) {
                JOptionPane.showMessageDialog(null, "La universidad ya existe", "UNIVERSIDAD EXISTENTE", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            Integer idUniversidad = listaUniversidades.getLongitud() + 1;
            nuevaUniversidad.setIdU(idUniversidad);

            universidadControlDao.setUniversidad(nuevaUniversidad);
            if (universidadControlDao.Persist()) {
                JOptionPane.showMessageDialog(null, "UNIVERSIDAD GUARDADADA EXITOSAMENTE", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
                universidadControlDao.setUniversidad(null);
            } 
            else {
                JOptionPane.showMessageDialog(null, "NO SE PUEDE GUARDAR", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
            }
            Limpiar();
        }
    }
    
    private boolean validarFechaNoFutura(Date date) {
        Date hoy = new Date();
        return !date.after(hoy);
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
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cbxTipoBusqueda = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        txtNombreU = new javax.swing.JTextField();
        txtDireccionU = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        DateFundacion = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUniversidades = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        cbxOrden = new javax.swing.JComboBox<>();
        btnOrdenar = new javax.swing.JButton();
        cbxTipoOrden = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SISTEMA DE GESTION DE UNIVERSIDADES");

        jPanel1.setBackground(new java.awt.Color(190, 193, 197));

        jPanel2.setBackground(new java.awt.Color(61, 90, 134));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/LojoUNL.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Haettenschweiler", 0, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("SERVICIO DE GESTION DE UNIVERSIDAD");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jLabel2)
                .addContainerGap(341, Short.MAX_VALUE))
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

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Nombre");

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Direccion");

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Telefono");

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Correo");

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Fundacion");

        jLabel9.setFont(new java.awt.Font("Candara Light", 1, 32)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Lista de universidades");

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Buscar por");

        cbxTipoBusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nombre", "Direccion", "Telefono", "Correo", "Fecha de fundacion" }));
        cbxTipoBusqueda.setSelectedIndex(-1);

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Busqueda");

        jButton1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Buscar.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Regresar.png"))); // NOI18N
        jButton2.setText("REGRESAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Guardar.png"))); // NOI18N
        jButton3.setText("GUARDAR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

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

        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyTyped(evt);
            }
        });

        tblUniversidades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblUniversidades.setSelectionBackground(new java.awt.Color(200, 222, 180));
        tblUniversidades.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tblUniversidades.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUniversidadesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUniversidades);

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Ordenar");

        cbxOrden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Asendente", "Desendente" }));
        cbxOrden.setSelectedIndex(-1);

        btnOrdenar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Ordenar.png"))); // NOI18N
        btnOrdenar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdenarActionPerformed(evt);
            }
        });

        cbxTipoOrden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nombre", "Direccion", "Telefono", "Correo", "Fecha de fundacion" }));
        cbxTipoOrden.setSelectedIndex(-1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3))
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombreU)
                            .addComponent(txtDireccionU)
                            .addComponent(txtTelefono)
                            .addComponent(txtCorreo)
                            .addComponent(DateFundacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxTipoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4))
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel12)
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
                        .addComponent(jLabel3)
                        .addComponent(jLabel9))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbxOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbxTipoOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel12))
                            .addComponent(btnOrdenar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel10)
                    .addComponent(cbxTipoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jButton1)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtDireccionU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(DateFundacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton2))
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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        VistaPersonalAdministracion abrirLogin = new VistaPersonalAdministracion();
        abrirLogin.setVisible(true);
        this.setVisible(false);
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
        try {
            Date fechaNacimiento = DateFundacion.getDate();
            if (txtNombreU.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar el nombre de la universidad", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (txtDireccionU.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar la direccion", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (txtTelefono.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar el numero de telefono", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (txtCorreo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar el correo de la universidad", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (DateFundacion.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Falta llenar fecha de fundacion", "Error", JOptionPane.WARNING_MESSAGE);
            }
            else if (!validarFechaNoFutura(fechaNacimiento)) {
                JOptionPane.showMessageDialog(null, "La fecha de fundacion no puede ser futura", "Error", JOptionPane.WARNING_MESSAGE);
            }
            else {
                Guardar();
            }
        } 
        catch (Exception e) {

        }
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
        int fila = tblUniversidades.getSelectedRow();
        Date fechaNacimiento = DateFundacion.getDate();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Escoga un registro");
        } else {

            if (txtNombreU.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar el nombre de la universidad", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (txtDireccionU.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar la direccion", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (txtTelefono.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar el numero de telefono", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (txtCorreo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar el correo de la universidad", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (DateFundacion.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Falta llenar fecha de fundacion", "Error", JOptionPane.WARNING_MESSAGE);
            }
            else if (!validarFechaNoFutura(fechaNacimiento)) {
                JOptionPane.showMessageDialog(null, "La fecha de fundacion no puede ser futura", "Error", JOptionPane.WARNING_MESSAGE);
            }
            else {
                                
                Integer IdUniversidad = universidadControlDao.getUniversidad().getIdU();
                String Nombre = txtNombreU.getText();
                String Direccion = txtDireccionU.getText();
                String Telefono = txtTelefono.getText();
                String Correo = txtCorreo.getText();
                Date InicioD = DateFundacion.getDate();
                String Fundacion = Formato.format(InicioD);
                
                Universidad universidadModificada = new Universidad();
                universidadModificada.setIdU(IdUniversidad);
                universidadModificada.setNombreU(Nombre);
                universidadModificada.setDireccionU(Direccion);
                universidadModificada.setTelefonoU(Telefono);
                universidadModificada.setCorreoU(Correo);
                universidadModificada.setFechaFU(Fundacion);

                universidadControlDao.Merge(universidadModificada, IdUniversidad -1);

//        (universidadModificada, IdUniversidad - 1);


//                Universidad universidadModificado = new Universidad(IdMateria, Nombre, Direccion, Telefono, Correo, Fundacion);

//                universidadControlDao.Merge(universidadModificado, IdUniversidad - 1);

                CargarTabla();

                Limpiar();
            }
        }
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        
        int fila = tblUniversidades.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Escoga un registro");
        }
        else {
            universidadControlDao.Eliminar(fila);
            CargarTabla();
        }
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        try {
            if (cbxTipoBusqueda.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Porfavor seleccione donde quiere buscar", "Error", JOptionPane.WARNING_MESSAGE);
            }
            else {
                ListaDinamica<Universidad> lista = universidadControlDao.getListaUniversid();

                String Campo = txtBuscar.getText();
                String TipoCampo = cbxTipoBusqueda.getSelectedItem().toString();

                switch (TipoCampo) {
                    case "Nombre":
                        TipoCampo = "NombreUniversidad";
                        break;
                    case "Direccion":
                        TipoCampo = "DireccionUniversidad";
                        break;
                    case "Telefono":
                        TipoCampo = "NumeroTelefono";
                        break;
                    case "Correo":
                        TipoCampo = "CorreoUniversidad";
                        break;
                    case "Fecha de fundacion":
                        TipoCampo = "FechaFundacion";
                        break;
                    default:
                        throw new AssertionError();
                }

                ListaDinamica<Universidad> ResultadoBusqueda;
                if (Campo.isEmpty()) {
                    ResultadoBusqueda = lista;
                } 
                else {
                    ResultadoBusqueda = UtilesControlador.BusquedaLineal(lista, Campo, TipoCampo);
                }

                mtu.setUniversidadTabla(ResultadoBusqueda);
                mtu.fireTableDataChanged();
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
         
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblUniversidadesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUniversidadesMouseClicked
        
        Seleccionar();
        
    }//GEN-LAST:event_tblUniversidadesMouseClicked

    private void btnOrdenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdenarActionPerformed

        try {
            if (cbxTipoOrden.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "No ha seleccionado el campo", "FALTA SELCCIONAR", JOptionPane.WARNING_MESSAGE);
            } 
            else if (cbxOrden.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "No ha seleccionado el orden", "FALTA SELCCIONAR", JOptionPane.WARNING_MESSAGE);
            } 
            else {
                ListaDinamica<Universidad> lista = universidadControlDao.getListaUniversid();
                String TipoCampo = cbxTipoOrden.getSelectedItem().toString();

                switch (TipoCampo) {
                    case "Nombre":
                        TipoCampo = "NombreUniversidad";
                        break;
                    case "Direccion":
                        TipoCampo = "DireccionUniversidad";
                        break;
                    case "Telefono":
                        TipoCampo = "NumeroTelefono";
                        break;
                    case "Correo":
                        TipoCampo = "CorreoUniversidad";
                        break;
                    case "Fecha de fundacion":
                        TipoCampo = "FechaFundacion";
                        break;
                    default:
                        throw new AssertionError();
                }

                Integer orden = OrdenSeleccionado();

                ListaDinamica<Universidad> resultadoOrdenado = UtilesControlador.QuickSort(lista, orden, TipoCampo);

                mtu.setUniversidadTabla(resultadoOrdenado);
                mtu.fireTableDataChanged();
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }//GEN-LAST:event_btnOrdenarActionPerformed

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
        
        Character c = evt.getKeyChar();
        String telefono = txtTelefono.getText();

        if (!Character.isDigit(c) && c != '(' && c != ')' && c != '-' && c != KeyEvent.VK_BACK_SPACE) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Solo ingreso de números y caracteres especiales (, ), -", "CARACTER NO VALIDO", JOptionPane.WARNING_MESSAGE);
        }
        if (telefono.length() >= 14 && c != KeyEvent.VK_BACK_SPACE) {
            evt.consume();
        }
        if ((telefono.length() == 2 || telefono.length() == 6) && c != KeyEvent.VK_BACK_SPACE) {
            txtTelefono.setText(telefono + "-");
        }
        
    }//GEN-LAST:event_txtTelefonoKeyTyped

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
            java.util.logging.Logger.getLogger(VistaGestionUniversidad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaGestionUniversidad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaGestionUniversidad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaGestionUniversidad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaGestionUniversidad().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DateFundacion;
    private javax.swing.JButton btnOrdenar;
    private javax.swing.JComboBox<String> cbxOrden;
    private javax.swing.JComboBox<String> cbxTipoBusqueda;
    private javax.swing.JComboBox<String> cbxTipoOrden;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
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
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblUniversidades;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDireccionU;
    private javax.swing.JTextField txtNombreU;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
