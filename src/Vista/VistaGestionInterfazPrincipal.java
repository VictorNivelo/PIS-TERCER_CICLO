
package Vista;

import Controlador.Dao.Bridge;
import Controlador.Dao.Modelo.presentacionDao;
import Controlador.TDA.ListaDinamica.Excepcion.ListaVacia;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Controlador.Utiles.UtilesControlador;
import Modelo.Presentacion;
import Vista.ModeloTabla.ModeloTablaPresentacion;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Victor
 */
public class VistaGestionInterfazPrincipal extends javax.swing.JFrame {
    presentacionDao presentacionControlDao = new presentacionDao();
    ListaDinamica<Presentacion> listaPresentacion = new ListaDinamica<>();
    ModeloTablaPresentacion mtp = new ModeloTablaPresentacion();
    private File ImagenPresentacion;
    private String rutaImagenGuardada;

    /**
     * Creates new form VistaGestionInterfazPrincipal
     */
    public VistaGestionInterfazPrincipal() {
        initComponents();
        this.setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/Vista/RecursosGraficos/IconoPrograma.png")).getImage());
        cbxEstadoPresentacion.setEnabled(false);
        CargarTabla();
    }
    
    private void CargarTabla() {
        mtp.setPresentacionTabla(presentacionControlDao.getListaPresentacion());
        tblPresentacion.setModel(mtp);
        tblPresentacion.updateUI();
        cbxEstadoPresentacion.setSelectedIndex(0);
        cbxTipoOrden.setSelectedIndex(-1);
    }
    
    private void Limpiar() throws ListaVacia {
        txtCodigo.setText("");
        txtImagen.setText("");
        txtTitulo.setText("");
        txtContenido.setText("");
        txtTiempo.setText("");
        cbxEstadoPresentacion.setSelectedIndex(-1);
        cbxTipoOrden.setSelectedIndex(-1);
        presentacionControlDao.setPresentaciones(null);
        CargarTabla();
    }
    
    private void Seleccionar(){
        int fila = tblPresentacion.getSelectedRow();
        if(fila < 0){
            JOptionPane.showMessageDialog(null, "Escoga un registro");
        }
        else{
            try {
                cbxEstadoPresentacion.setEnabled(true);
                presentacionControlDao.setPresentaciones(mtp.getPresentacionTabla().getInfo(fila));
                
                txtCodigo.setText(presentacionControlDao.getPresentaciones().getCodigo());
                txtImagen.setText(presentacionControlDao.getPresentaciones().getImagen());
                txtTitulo.setText(presentacionControlDao.getPresentaciones().getTitulo());
                txtContenido.setText(presentacionControlDao.getPresentaciones().getContenido());
                txtTiempo.setText(presentacionControlDao.getPresentaciones().getTiempo());
                cbxEstadoPresentacion.setSelectedItem(presentacionControlDao.getPresentaciones().getEstadoPresentacion());
                
            } 
            catch (Exception e) {
                
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    private String generarCodigo() throws ListaVacia {
        int ultimoId = 0;

        String presentacionURL = "Files" + File.separatorChar + "Presentacion.json";

        try {
            ListaDinamica<Presentacion> listaPresentacion = (ListaDinamica<Presentacion>) Bridge.getConection().fromXML(new FileReader(presentacionURL));

            if (!listaPresentacion.EstaVacio()) {
                Presentacion ultimaPresentacion = listaPresentacion.getInfo(listaPresentacion.getLongitud() - 1);
                ultimoId = ultimaPresentacion.getIdPresentacion();
            }
        } 
        catch (FileNotFoundException e) {
        }

        ultimoId++;

        return "P-" + String.format("%04d", ultimoId);
    }

    private void Guardar() throws ListaVacia {

        if (txtImagen.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Falta llenar seleccionar la imagen", "Error", JOptionPane.WARNING_MESSAGE);
        }
        else if (txtTiempo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Falta llenar el tiempo", "Error", JOptionPane.WARNING_MESSAGE);
        }
        else if (txtTitulo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Falta llenar el titulo", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else if (txtContenido.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Falta llenar el contenido", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else if (cbxEstadoPresentacion.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Falta seleccionar el estado de la presentacion", "Error", JOptionPane.WARNING_MESSAGE);
        }
        else {
            Integer IdPresentacion = listaPresentacion.getLongitud() + 1;
            String Codigo = generarCodigo();
            String Tiempo = txtTiempo.getText();
            String Titulo = txtTitulo.getText();
            String Contenido = txtContenido.getText();
            String Estado = cbxEstadoPresentacion.getSelectedItem().toString();
                        
            presentacionControlDao.getPresentaciones().setIdPresentacion(IdPresentacion);
            
            presentacionControlDao.getPresentaciones().setCodigo(Codigo);
            presentacionControlDao.getPresentaciones().setTitulo(Titulo);
            presentacionControlDao.getPresentaciones().setTiempo(Tiempo);
            presentacionControlDao.getPresentaciones().setContenido(Contenido);
            presentacionControlDao.getPresentaciones().setEstadoPresentacion(Estado);
            
            String nombreUnico = UUID.randomUUID().toString();
            rutaImagenGuardada = "src/Vista/RecursosGraficos/Fondos/" + nombreUnico + ".jpg";
                        
            System.out.println("Ruta de destino: " + rutaImagenGuardada);

            try {
                if (ImagenPresentacion != null) {
                    File destino = new File(rutaImagenGuardada);
                    File carpetaDestino = destino.getParentFile();
                    if (!carpetaDestino.exists()) {
                        carpetaDestino.mkdirs();
                    }
                    Files.copy(ImagenPresentacion.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            } 
            catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al copiar el archivo: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            
            presentacionControlDao.getPresentaciones().setImagen(rutaImagenGuardada);

            cbxEstadoPresentacion.setEnabled(false);
                        
            if (presentacionControlDao.Persist()) {
                JOptionPane.showMessageDialog(null, "PRESENTACION GUARDADA EXISTOSAMENTE", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
                presentacionControlDao.setPresentaciones(null);
            } 
            else {
                JOptionPane.showMessageDialog(null, "NO SE PUEDE REGISTRAR", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
            }
            Limpiar();
        }
    }
    
    private File CargarFoto() throws Exception {
        File obj = null;
        JFileChooser choosser = new JFileChooser();

        String downloadsPath = System.getProperty("user.home") + File.separator + "Downloads";
        choosser.setCurrentDirectory(new File(downloadsPath));

        choosser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Imagenes", "jpg", "png", "jpeg");
        choosser.addChoosableFileFilter(filter);

        Integer resp = choosser.showOpenDialog(this);
        if (resp == JFileChooser.APPROVE_OPTION) {
            obj = choosser.getSelectedFile();
            System.out.println("Foto cargada correctamente");
        } 
        else {
            System.out.println("No se puede cargar la imagen");
        }
        return obj;
    }
    
    private void mostrarImagenEnVentana(String rutaImagen) {
        try {
            if (rutaImagen != null && !rutaImagen.isEmpty()) {
                ImagenPresentacion f = new ImagenPresentacion();
                f.mostrarImagen(rutaImagen);
                f.setVisible(true);
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al mostrar la imagen", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void copiarArchivo(File origen, File destino) throws Exception {
        Files.copy(origen.toPath(),(destino).toPath(),StandardCopyOption.REPLACE_EXISTING);
    }
    
    public static String extension(String fileName) {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1);
        }
        return extension;
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
        txtImagen = new javax.swing.JTextField();
        txtTiempo = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtContenido = new javax.swing.JTextArea();
        btnGuardar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPresentacion = new javax.swing.JTable();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        cbxEstadoPresentacion = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        cbxTipoOrden = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        btnCargarImagen = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        cbxOrden = new javax.swing.JComboBox<>();
        btnOrdenar = new javax.swing.JButton();
        cbxTipoBusqueda = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PERSONALIZACION DE PANTALLA PRINCIPAL");

        jPanel1.setBackground(new java.awt.Color(190, 193, 197));

        jPanel2.setBackground(new java.awt.Color(61, 90, 134));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/LojoUNL.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Haettenschweiler", 0, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("SERVICIO DE PERSONALIZACION DE PANTALLA PRINCIPAL");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap(111, Short.MAX_VALUE))
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

        jLabel4.setFont(new java.awt.Font("Candara Light", 1, 32)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Lista de presentaciones");

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Imagen");

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Contenido");

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Tiempo");

        txtImagen.setEditable(false);
        txtImagen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtImagenMouseClicked(evt);
            }
        });

        txtTiempo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTiempoKeyTyped(evt);
            }
        });

        jScrollPane1.setEnabled(false);
        jScrollPane1.setWheelScrollingEnabled(false);

        txtContenido.setColumns(20);
        txtContenido.setLineWrap(true);
        txtContenido.setRows(5);
        txtContenido.setWrapStyleWord(true);
        txtContenido.setAutoscrolls(false);
        jScrollPane1.setViewportView(txtContenido);

        btnGuardar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Guardar.png"))); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
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

        tblPresentacion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblPresentacion.setSelectionBackground(new java.awt.Color(200, 222, 180));
        tblPresentacion.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tblPresentacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPresentacionMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblPresentacion);

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

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Estado");

        cbxEstadoPresentacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activa", "Inactiva" }));
        cbxEstadoPresentacion.setSelectedIndex(-1);

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Buscar por");

        cbxTipoOrden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Codigo", "Imagen", "Tiempo", "Contenido", "Estado" }));
        cbxTipoOrden.setSelectedIndex(-1);

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Buscar");

        btnBuscar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Buscar.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Codigo");

        btnCargarImagen.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btnCargarImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Cargar.png"))); // NOI18N
        btnCargarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarImagenActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Titulo");

        txtCodigo.setEditable(false);
        txtCodigo.setEnabled(false);

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

        cbxTipoBusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Codigo", "Imagen", "Tiempo", "Contenido", "Estado" }));
        cbxTipoBusqueda.setSelectedIndex(-1);

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                        .addComponent(btnGuardar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxEstadoPresentacion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTiempo, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                            .addComponent(txtTitulo)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(txtImagen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCargarImagen))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCodigo))
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxTipoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscar))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(50, 619, Short.MAX_VALUE)
                        .addComponent(btnEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificar))
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13)
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
                        .addComponent(jLabel4))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbxOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbxTipoOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel13))
                            .addComponent(btnOrdenar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar)
                    .addComponent(jLabel11)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxTipoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(txtImagen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnCargarImagen))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(cbxEstadoPresentacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(jButton2)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar))
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

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        

        if (txtImagen.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Falta llenar seleccionar la imagen", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else if (txtTiempo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Falta llenar el tiempo", "Error", JOptionPane.WARNING_MESSAGE);
        }
        else if (txtTitulo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Falta llenar el titulo", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else if (txtContenido.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Falta llenar el contenido", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else if (cbxEstadoPresentacion.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Falta seleccionar el estado de la presentacion", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else {
            try {
                Guardar();
            } 
            catch (Exception e) {
                
            }
        }

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        VistaPersonalAdministracion vpa = new VistaPersonalAdministracion();
        vpa.setVisible(true);
        this.setVisible(false);
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed

        int fila = tblPresentacion.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Escoga un registro");
        } 
        else {
            if (txtImagen.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar seleccionar la imagen", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (txtTiempo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar el tiempo", "Error", JOptionPane.WARNING_MESSAGE);
            }
            else if (txtTitulo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar el titulo", "Error", JOptionPane.WARNING_MESSAGE);
            }
            else if (txtContenido.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar el contenido", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (cbxEstadoPresentacion.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar el estado de la presentacion", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else {

                Integer IdPresentacion = presentacionControlDao.getPresentaciones().getIdPresentacion();
                String Codigo = txtCodigo.getText();
                String Tiempo = txtTiempo.getText();
                String Titulo = txtTitulo.getText();
                String Contenido = txtContenido.getText();
                String Estado = cbxEstadoPresentacion.getSelectedItem().toString();

                Presentacion personaModiPersona = new Presentacion();
                personaModiPersona.setIdPresentacion(IdPresentacion);
                personaModiPersona.setCodigo(Codigo);
                personaModiPersona.setTiempo(Tiempo);
                personaModiPersona.setTitulo(Titulo);
                personaModiPersona.setContenido(Contenido);
                personaModiPersona.setEstadoPresentacion(Estado);
                personaModiPersona.setImagen(presentacionControlDao.getPresentaciones().getImagen());

                presentacionControlDao.Merge(personaModiPersona, IdPresentacion - 1);

                CargarTabla();

                try {
                    Limpiar();
                } 
                catch (Exception e) {

                }
            }
        }
        cbxEstadoPresentacion.setEnabled(false);
        
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        
        int fila = tblPresentacion.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Escoga un registro");
        } 
        else {
            try {
                String rutaImagenEliminar = presentacionControlDao.getPresentaciones().getImagen();
                if (rutaImagenEliminar != null && !rutaImagenEliminar.isEmpty()) {
                    File archivoEliminar = new File(rutaImagenEliminar);
                    if (archivoEliminar.exists()) {
                        archivoEliminar.delete();
                    }
                }
            } 
            catch (Exception e) {
                e.printStackTrace();
            }

            presentacionControlDao.Eliminar(fila);
            
            CargarTabla();

            try {
                Limpiar();
            } 
            catch (ListaVacia ex) {
                ex.printStackTrace();
            }
        }
        
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        
        try {
            if (cbxTipoBusqueda.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Porfavor seleccione donde quiere buscar", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else {
                ListaDinamica<Presentacion> lista = presentacionControlDao.all();

                String Campo = txtBuscar.getText();
                String TipoCampo = cbxTipoOrden.getSelectedItem().toString();

                switch (TipoCampo) {
                    case "Codigo":
                        TipoCampo = "Codigo";
                        break;
                    case "Imagen":
                        TipoCampo = "Imagen";
                        break;
                    case "Titulo":
                        TipoCampo = "Titulo";
                        break;
                    case "Contenido":
                        TipoCampo = "Contenido";
                        break;
                    case "Tiempo":
                        TipoCampo = "Tiempo";
                        break;
                    case "Estado":
                        TipoCampo = "EstadoPresentacion";
                        break;
                    default:
                        throw new AssertionError();
                }

                ListaDinamica<Presentacion> ResultadoBusqueda = UtilesControlador.BusquedaLineal(lista, Campo, TipoCampo);

                mtp.setPresentacionTabla(ResultadoBusqueda);
                mtp.fireTableDataChanged();
            }
        } 
        catch (Exception e) {

        }
        
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void tblPresentacionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPresentacionMouseClicked
        
        Seleccionar();
        
    }//GEN-LAST:event_tblPresentacionMouseClicked

    private void btnCargarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarImagenActionPerformed
        
        try {
            ImagenPresentacion = CargarFoto();
            if (ImagenPresentacion != null) {
                txtImagen.setText(ImagenPresentacion.getAbsolutePath());
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar la imagen", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_btnCargarImagenActionPerformed

    private void txtImagenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtImagenMouseClicked

        if (evt.getClickCount() == 2) {
            mostrarImagenEnVentana(txtImagen.getText());
        }
        
    }//GEN-LAST:event_txtImagenMouseClicked

    private void btnOrdenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdenarActionPerformed

        try {
            if (cbxTipoOrden.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "No ha seleccionado el campo", "FALTA SELCCIONAR", JOptionPane.WARNING_MESSAGE);
            } 
            else if (cbxOrden.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "No ha seleccionado el orden", "FALTA SELCCIONAR", JOptionPane.WARNING_MESSAGE);
            } 
            else {
                ListaDinamica<Presentacion> lista = presentacionControlDao.all();
                String TipoCampo = cbxTipoOrden.getSelectedItem().toString();

                switch (TipoCampo) {
                    case "Codigo":
                        TipoCampo = "Codigo";
                        break;
                    case "Imagen":
                        TipoCampo = "Imagen";
                        break;
                    case "Titulo":
                        TipoCampo = "Titulo";
                        break;
                    case "Contenido":
                        TipoCampo = "Contenido";
                        break;
                    case "Tiempo":
                        TipoCampo = "Tiempo";
                        break;
                    case "Estado":
                        TipoCampo = "EstadoPresentacion";
                        break;
                    default:
                        throw new AssertionError();
                }

                Integer orden = OrdenSeleccionado();

                ListaDinamica<Presentacion> resultadoOrdenado = UtilesControlador.QuickSort(lista, orden, TipoCampo);

                mtp.setPresentacionTabla(resultadoOrdenado);
                mtp.fireTableDataChanged();
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }//GEN-LAST:event_btnOrdenarActionPerformed

    private void txtTiempoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTiempoKeyTyped
        
        Character c = evt.getKeyChar();

        if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Solo ingreso de numeros", "CARACTER NO VALIDO", JOptionPane.WARNING_MESSAGE);
        }
        if (txtTiempo.getText().length() >= 10 && c != KeyEvent.VK_BACK_SPACE) {
            evt.consume();
        }
        
    }//GEN-LAST:event_txtTiempoKeyTyped

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
            java.util.logging.Logger.getLogger(VistaGestionInterfazPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaGestionInterfazPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaGestionInterfazPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaGestionInterfazPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaGestionInterfazPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCargarImagen;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnOrdenar;
    private javax.swing.JComboBox<String> cbxEstadoPresentacion;
    private javax.swing.JComboBox<String> cbxOrden;
    private javax.swing.JComboBox<String> cbxTipoBusqueda;
    private javax.swing.JComboBox<String> cbxTipoOrden;
    private javax.swing.JButton jButton2;
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
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblPresentacion;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextArea txtContenido;
    private javax.swing.JTextField txtImagen;
    private javax.swing.JTextField txtTiempo;
    private javax.swing.JTextField txtTitulo;
    // End of variables declaration//GEN-END:variables
}
