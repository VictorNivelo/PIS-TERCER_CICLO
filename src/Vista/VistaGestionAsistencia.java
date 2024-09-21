
package Vista;

import Controlador.Dao.Modelo.asistenciaDao;
import Controlador.TDA.ListaDinamica.Excepcion.ListaVacia;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Controlador.Utiles.UtilesControlador;
import Modelo.Asistencia;
import Modelo.EstadoAsistencia;
import Modelo.Tematica;
import Vista.ModeloTabla.ModeloTablaAsistencia;
import Vista.Utiles.UtilVista;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Victor
 */
public class VistaGestionAsistencia extends javax.swing.JFrame {
    ModeloTablaAsistencia mta = new ModeloTablaAsistencia();
    asistenciaDao AsistenciaControl = new asistenciaDao();
    ListaDinamica<Asistencia> listaAsistencia = new ListaDinamica<>();
    SimpleDateFormat Formato = new SimpleDateFormat("dd/MMMM/yyyy");

    /**
     * Creates new form VistaPrincipal
     * @throws Controlador.TDA.ListaDinamica.Excepcion.ListaVacia
     */
    public VistaGestionAsistencia() throws ListaVacia {
        initComponents();
        this.setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/Vista/RecursosGraficos/IconoPrograma.png")).getImage());
        LlenarComboConEnum();
        DateFechaTematica.setDateFormatString("dd/MMMM/yyyy");
        UtilVista.cargarcomboHorario(cbxHorario);
        CargarTabla();
    }
        
    private void CargarTabla() {
        mta.setAsistenciaTabla(AsistenciaControl.getListaAsistencia());
        tblAsistencia.setModel(mta);
        tblAsistencia.updateUI();
        cbxEstadoAsistencia.setSelectedIndex(-1);
        cbxHorario.setSelectedIndex(-1);
        DateFechaTematica.setDate(null);
    }
    
    public void LlenarComboConEnum() {
        for (EstadoAsistencia tipo : EstadoAsistencia.values()) {
            cbxEstadoAsistencia.addItem(tipo.getDescripcion());
        }
    }
    
    private void Limpiar() throws ListaVacia {
        txtObservacion.setText("");
        txtTematica.setText("");
        cbxEstadoAsistencia.setSelectedIndex(-1);
        cbxHorario.setSelectedIndex(-1);
        DateFechaTematica.setDate(null);
        AsistenciaControl.setAsistencia(null);
        CargarTabla();
    }
    
    private void Seleccionar(){
        int fila = tblAsistencia.getSelectedRow();
        if(fila < 0){
            JOptionPane.showMessageDialog(null, "Escoga un registro");
        }
        else{
            try {
                AsistenciaControl.setAsistencia(mta.getAsistenciaTabla().getInfo(fila));
                
                txtObservacion.setText(AsistenciaControl.getAsistencia().getObservacion());
                cbxEstadoAsistencia.setSelectedItem(AsistenciaControl.getAsistencia().getEstadoAsistencia().toString());
                txtTematica.setText(AsistenciaControl.getAsistencia().getTematicaAsistencia().getNombreTematica());
                Date Fecha = Formato.parse(AsistenciaControl.getAsistencia().getTematicaAsistencia().getFechaTematica());
                DateFechaTematica.setDate(Fecha);
                cbxHorario.setSelectedIndex(AsistenciaControl.getAsistencia().getHorarioAsistencia().getIdHorario() -1);

            } 
            catch (Exception e) {
                
            }
        }
    }
    
    @SuppressWarnings("unused")
    private void Guardar() throws ListaVacia {
        
        Date fechaNacimiento = DateFechaTematica.getDate();
        if (cbxHorario.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Falta seleccionar ek gorario", "Error", JOptionPane.WARNING_MESSAGE);
        }
        else if (cbxEstadoAsistencia.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Falta seleccionar la asistencia", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else if (txtObservacion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Falta llenar duracion", "Error", JOptionPane.WARNING_MESSAGE);
        }
        else if (txtTematica.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Falta llenar duracion", "Error", JOptionPane.WARNING_MESSAGE);
        }
        else if (DateFechaTematica.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Falta llenar fecha", "Error", JOptionPane.WARNING_MESSAGE);
        }
        else if (!validarFechaNoFutura(fechaNacimiento)) {
            JOptionPane.showMessageDialog(null, "La fecha de la asistencia no puede ser futura", "Error", JOptionPane.WARNING_MESSAGE);
        }
        else {
                        
            Integer IdAsistencia = listaAsistencia.getLongitud() + 1;
            
            int indiceSeleccionado = cbxEstadoAsistencia.getSelectedIndex();
            EstadoAsistencia[] valores = EstadoAsistencia.values();
            EstadoAsistencia estadoSeleccionado = valores[indiceSeleccionado];
            String Observacion = txtObservacion.getText();
            Date ft = DateFechaTematica.getDate();
            String FechaT = Formato.format(ft);
            String TEM = txtTematica.getText();
            
            Tematica t = new Tematica();
            t.setIdTematica(IdAsistencia);
            t.setNombreTematica(TEM);
            t.setFechaTematica(FechaT);
//            IdAsistencia, TEM, FechaT);
                                    
            AsistenciaControl.getAsistencia().setIdAsistencia(IdAsistencia);
            AsistenciaControl.getAsistencia().setHorarioAsistencia(UtilVista.obtenerHorarioControl(cbxHorario));
//            AsistenciaControl.getAsistencias().setEstadoAsistencia(estadoSeleccionado);
            AsistenciaControl.getAsistencia().setObservacion(Observacion);
            AsistenciaControl.getAsistencia().setTematicaAsistencia(t);
                        
            if (AsistenciaControl.Persist()) {
                JOptionPane.showMessageDialog(null, "ASISTENCIA GUARDADA EXISTOSAMENTE", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
                AsistenciaControl.setAsistencia(null);
            } 
            else {
                JOptionPane.showMessageDialog(null, "NO SE PUEDE REGISTRAR", "INFORMACION", JOptionPane.WARNING_MESSAGE);
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
    
//    public static <T> ListaDinamica<T> buscarEnLista(ListaDinamica<T> lista, String campo, Field field) {
//        ListaDinamica<T> resultados = new ListaDinamica<>();
//
//        for (T elemento : lista.toArray()) {
//            try {
//                Field campoElemento = UtilesControlador.getField(elemento.getClass(), field);
//                campoElemento.setAccessible(true);
//
//                Object valorElemento = campoElemento.get(elemento);
//
//                if (valorElemento != null && valorElemento.toString().contains(campo)) {
//                    resultados.Agregar(elemento);
//                }
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return resultados;
//    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JToggleButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAsistencia = new javax.swing.JTable();
        btnRegistrarAsistencias = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        cbxEstadoAsistencia = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        txtObservacion = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        cbxTipoBusqueda = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtTematica = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        DateFechaTematica = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        cbxHorario = new javax.swing.JComboBox<>();
        btnOrdenar = new javax.swing.JButton();
        cbxOrden = new javax.swing.JComboBox<>();
        cbxTipoOrden = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GESTION DE ASISTENCIAS");

        jPanel2.setBackground(new java.awt.Color(191, 192, 197));

        jPanel1.setBackground(new java.awt.Color(61, 90, 134));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/LojoUNL.png"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Haettenschweiler", 0, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CENTRO DE CONTROL DE ASISTENCIAS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel2.setFont(new java.awt.Font("Candara Light", 1, 32)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Informacion");

        jLabel7.setFont(new java.awt.Font("Candara Light", 1, 32)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Lista de asistencia");

        btnRegresar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btnRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Regresar.png"))); // NOI18N
        btnRegresar.setText("REGRESAR");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        tblAsistencia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblAsistencia.setSelectionBackground(new java.awt.Color(200, 222, 180));
        tblAsistencia.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tblAsistencia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAsistenciaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblAsistencia);

        btnRegistrarAsistencias.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btnRegistrarAsistencias.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Guardar.png"))); // NOI18N
        btnRegistrarAsistencias.setText("GUARDAR");
        btnRegistrarAsistencias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarAsistenciasActionPerformed(evt);
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

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Estado");

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Observacion");

        txtObservacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtObservacionKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Buscar por");

        cbxTipoBusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tematica", "Fecha", "Estado de asistencia", "Observacion" }));
        cbxTipoBusqueda.setSelectedIndex(-1);

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Buscar");

        jButton1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Buscar.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Tematica");

        txtTematica.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTematicaKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Fecha");

        DateFechaTematica.setDateFormatString("dd mm yyyy");

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Horario");

        btnOrdenar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Ordenar.png"))); // NOI18N
        btnOrdenar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdenarActionPerformed(evt);
            }
        });

        cbxOrden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Asendente", "Desendente" }));
        cbxOrden.setSelectedIndex(-1);

        cbxTipoOrden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Numero de cedula", "Nombre", "Apellido", "Genero", "Estado", "Telefono", "Correo", "Codido matricula" }));
        cbxTipoOrden.setSelectedIndex(-1);

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Ordenar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnRegresar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRegistrarAsistencias))
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtObservacion))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)))
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxEstadoAsistencia, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTematica)
                            .addComponent(DateFechaTematica, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxHorario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(651, 651, 651)
                        .addComponent(btnEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxTipoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxTipoOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOrdenar)))
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel7)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbxOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbxTipoOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnOrdenar)
                        .addGap(1, 1, 1)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel12)
                                .addComponent(cbxTipoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel13)
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(cbxHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRegresar)
                            .addComponent(btnRegistrarAsistencias)
                            .addComponent(btnModificar)
                            .addComponent(btnEliminar)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(cbxEstadoAsistencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTematica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(DateFechaTematica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtObservacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        try {
            if (cbxTipoBusqueda.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Porfavor seleccione donde quiere buscar", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else {
                ListaDinamica<Asistencia> lista = AsistenciaControl.all();

                String Campo = txtBuscar.getText();
                String TipoCampo = cbxTipoBusqueda.getSelectedItem().toString();

                switch (TipoCampo) {
                    case "Tematica":
                        TipoCampo = "AsistenciaTematica.NombreTematica";
                        break;
                    case "Fecha":
                        TipoCampo = "AsistenciaTematica.FechaTematica";
                        break;
                    case "Estado de asistencia":
                        TipoCampo = "EstadoAsistencia";
                        break;
                    case "Observacion":
                        TipoCampo = "Observacion";
                        break;
                    default:
                        throw new AssertionError();
                }

                ListaDinamica<Asistencia> ResultadoBusqueda = UtilesControlador.BusquedaLineal(lista, Campo, TipoCampo);

                mta.setAsistenciaTabla(ResultadoBusqueda);
                mta.fireTableDataChanged();
            }
        } 
        catch (Exception e) {

        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        
        int fila = tblAsistencia.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Escoga un registro");
        }
        else {
            AsistenciaControl.Eliminar(fila);
            CargarTabla();
        }
        
    }//GEN-LAST:event_btnEliminarActionPerformed

    @SuppressWarnings("unused")
    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        
        int fila = tblAsistencia.getSelectedRow();
        Date fechaNacimiento = DateFechaTematica.getDate();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Escoga un registro");
        } 
        else {
            if (cbxHorario.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar ek gorario", "Error", JOptionPane.WARNING_MESSAGE);
            }
            else if (cbxEstadoAsistencia.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar la asistencia", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (txtObservacion.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar duracion", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (txtTematica.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar duracion", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (DateFechaTematica.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Falta llenar fecha", "Error", JOptionPane.WARNING_MESSAGE);
            }
            else if (!validarFechaNoFutura(fechaNacimiento)) {
                JOptionPane.showMessageDialog(null, "La fecha de la asistencia no puede ser futura", "Error", JOptionPane.WARNING_MESSAGE);
            }
            else {

                Integer IdAsistencia = AsistenciaControl.getAsistencia().getIdAsistencia();

                int indiceSeleccionado = cbxEstadoAsistencia.getSelectedIndex();
                EstadoAsistencia[] valores = EstadoAsistencia.values();
                EstadoAsistencia estadoSeleccionado = valores[indiceSeleccionado];
                String Observacion = txtObservacion.getText();
                Date ft = DateFechaTematica.getDate();
                String FechaT = Formato.format(ft);
                String TEM = txtTematica.getText();

                Tematica t = new Tematica();
                t.setIdTematica(IdAsistencia);
                t.setNombreTematica(TEM);
                t.setFechaTematica(FechaT);

                Asistencia asistenciaModificada = new Asistencia();
                asistenciaModificada.setIdAsistencia(IdAsistencia);
//                asistenciaModificada.setEstadoAsistencia(estadoSeleccionado);
                asistenciaModificada.setObservacion(Observacion);
                asistenciaModificada.setTematicaAsistencia(t);
                asistenciaModificada.setHorarioAsistencia(UtilVista.obtenerHorarioControl(cbxHorario));
//                IdAsistencia, estadoSeleccionado, Observacion, t);

                AsistenciaControl.Merge(asistenciaModificada, IdAsistencia - 1);

                CargarTabla();

                try {
                    Limpiar();
                } 
                catch (ListaVacia ex) {

                }
            }
        }
        
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnRegistrarAsistenciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarAsistenciasActionPerformed
        
        try {
            Date fechaNacimiento = DateFechaTematica.getDate();
            if (cbxHorario.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar ek gorario", "Error", JOptionPane.WARNING_MESSAGE);
            }
            else if (cbxEstadoAsistencia.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar la asistencia", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (txtObservacion.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar duracion", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (txtTematica.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar duracion", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (DateFechaTematica.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Falta llenar fecha", "Error", JOptionPane.WARNING_MESSAGE);
            }
            else if (!validarFechaNoFutura(fechaNacimiento)) {
                JOptionPane.showMessageDialog(null, "La fecha de la asistencia no puede ser futura", "Error", JOptionPane.WARNING_MESSAGE);
            }
            else {                
                Guardar();
            }
        } 
        catch (Exception e) {

        }
        
    }//GEN-LAST:event_btnRegistrarAsistenciasActionPerformed

    private void tblAsistenciaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAsistenciaMouseClicked
        
        Seleccionar();
        
    }//GEN-LAST:event_tblAsistenciaMouseClicked

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        
        VistaPersonalAdministracion abrirLogin = new VistaPersonalAdministracion();
        abrirLogin.setVisible(true);
        this.setVisible(false);
        
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void txtObservacionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtObservacionKeyTyped
        
        char c = evt.getKeyChar();

        if (!Character.isLetter(c) && c != KeyEvent.VK_BACK_SPACE && c != ' ') {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Solo ingreso de letras", "CARACTER NO VALIDO", JOptionPane.WARNING_MESSAGE);
        }
        if (c != KeyEvent.VK_BACK_SPACE) {

        }
        
    }//GEN-LAST:event_txtObservacionKeyTyped

    private void txtTematicaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTematicaKeyTyped
        
        char c = evt.getKeyChar();

        if (!Character.isLetter(c) && c != KeyEvent.VK_BACK_SPACE && c != ' ') {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Solo ingreso de letras", "CARACTER NO VALIDO", JOptionPane.WARNING_MESSAGE);
        }
        if (c != KeyEvent.VK_BACK_SPACE) {

        }
        
    }//GEN-LAST:event_txtTematicaKeyTyped

    private void btnOrdenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdenarActionPerformed

        try {
            if (cbxTipoOrden.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "No ha seleccionado el campo", "FALTA SELCCIONAR", JOptionPane.WARNING_MESSAGE);
            } 
            else if (cbxOrden.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "No ha seleccionado el orden", "FALTA SELCCIONAR", JOptionPane.WARNING_MESSAGE);
            } 
            else {
                ListaDinamica<Asistencia> lista = AsistenciaControl.all();
                String TipoCampo = cbxTipoOrden.getSelectedItem().toString();

                switch (TipoCampo) {
                    case "Tematica":
                        TipoCampo = "AsistenciaTematica.NombreTematica";
                        break;
                    case "Fecha":
                        TipoCampo = "AsistenciaTematica.FechaTematica";
                        break;
                    case "Estado de asistencia":
                        TipoCampo = "EstadoAsistencia";
                        break;
                    case "Observacion":
                        TipoCampo = "Observacion";
                        break;
                    default:
                        throw new AssertionError();
                }

                Integer orden = OrdenSeleccionado();

                ListaDinamica<Asistencia> resultadoOrdenado = UtilesControlador.QuickSort(lista, orden, TipoCampo);

                mta.setAsistenciaTabla(resultadoOrdenado);
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
            java.util.logging.Logger.getLogger(VistaGestionAsistencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaGestionAsistencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaGestionAsistencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaGestionAsistencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new VistaGestionAsistencia().setVisible(true);
                } 
                catch (ListaVacia ex) {
                    
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DateFechaTematica;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnOrdenar;
    private javax.swing.JButton btnRegistrarAsistencias;
    private javax.swing.JToggleButton btnRegresar;
    private javax.swing.JComboBox<String> cbxEstadoAsistencia;
    private javax.swing.JComboBox<String> cbxHorario;
    private javax.swing.JComboBox<String> cbxOrden;
    private javax.swing.JComboBox<String> cbxTipoBusqueda;
    private javax.swing.JComboBox<String> cbxTipoOrden;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblAsistencia;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtObservacion;
    private javax.swing.JTextField txtTematica;
    // End of variables declaration//GEN-END:variables
}
