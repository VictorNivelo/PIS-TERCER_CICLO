
package Vista;

import Controlador.Dao.Bridge;
import Controlador.Dao.Modelo.horarioDao;
import Controlador.TDA.ListaDinamica.Excepcion.ListaVacia;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Controlador.Utiles.UtilesControlador;
import Modelo.Horario;
import Modelo.Materia;
import Vista.ModeloTabla.ModeloTablaHorario;
import Vista.Utiles.UtilVista;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Victor
 */
public class VistaGestionHorario extends javax.swing.JFrame {
    horarioDao horarioControlDao = new horarioDao();
    ListaDinamica<Horario> listaHorarios = new ListaDinamica<>();
    ModeloTablaHorario mth = new ModeloTablaHorario();
    
    /**
     * Creates new form GestionDeHorario
     * @throws Controlador.TDA.ListaDinamica.Excepcion.ListaVacia
     */
    public VistaGestionHorario() throws ListaVacia {
        initComponents();
        this.setLocationRelativeTo(null);
        UtilVista.CargarComboMateria(cbxMateria);
        setIconImage(new ImageIcon(getClass().getResource("/Vista/RecursosGraficos/IconoPrograma.png")).getImage());
        CargarTabla();
    }
    
    private void CargarTabla() {
        mth.setHorarioTabla(horarioControlDao.getListaHorario());
        tblHorario.setModel(mth);
        tblHorario.updateUI();
        cbxMateria.setSelectedIndex(-1);
        cbxTipoOrden.setSelectedIndex(-1);
        cbxDiaHorario.setSelectedIndex(-1);
    }
    
    private void Limpiar() throws ListaVacia {
        txtHoraInicio.setText("");
        txtHoraFin.setText("");
        txtCodigoHorario.setText("");
        cbxMateria.setSelectedIndex(-1);
        cbxTipoOrden.setSelectedIndex(-1);
        cbxDiaHorario.setSelectedIndex(-1);
        horarioControlDao.setHorarios(null);
        CargarTabla();
    }
    
    private void Seleccionar(){
        int fila = tblHorario.getSelectedRow();
        if(fila < 0){
            JOptionPane.showMessageDialog(null, "Escoga un registro");
        }
        else{
            try {
                horarioControlDao.setHorarios(mth.getHorarioTabla().getInfo(fila));
                
                txtHoraInicio.setText(horarioControlDao.getHorarios().getHoraIncio());
                txtHoraFin.setText(horarioControlDao.getHorarios().getHoraFin());
                txtCodigoHorario.setText(horarioControlDao.getHorarios().getCodigoHorario());
                cbxMateria.setSelectedIndex(horarioControlDao.getHorarios().getMateriaHorario().getIdMateria() -1);
                cbxDiaHorario.setSelectedItem(horarioControlDao.getHorarios().getDiaSemana());
//                Date FechaNacimiento = Formato.parse(horarioControlDao.getHorarios().getDiaSemana());
//                DateHorario.setDate(FechaNacimiento);
                
            } 
            catch (Exception e) {
                
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    private String generarCodigo() throws ListaVacia {
        int ultimoId = 0;

        String presentacionURL = "Files" + File.separatorChar + "Horario.json";

        try {
            ListaDinamica<Horario> listaPresentacion = (ListaDinamica<Horario>) Bridge.getConection().fromXML(new FileReader(presentacionURL));
            if (!listaPresentacion.EstaVacio()) {
                Horario ultimaPresentacion = listaPresentacion.getInfo(listaPresentacion.getLongitud() - 1);
                ultimoId = ultimaPresentacion.getIdHorario();
            }
        } 
        catch (FileNotFoundException e) {
            
        }

        ultimoId++;
        return "H-" + String.format("%04d", ultimoId);
    }
    
    private boolean horarioExiste(Horario nuevoHorario) {
        ListaDinamica<Horario> horarios = horarioControlDao.getListaHorario();
        if (horarios.EstaVacio()) {
            return false;
        }
        for (Horario h : horarios.toArray()) {
            if (h.getDiaSemana().equals(nuevoHorario.getDiaSemana())
                    && h.getHoraIncio().equals(nuevoHorario.getHoraIncio())
                    && h.getHoraFin().equals(nuevoHorario.getHoraFin())
                    /*&& h.getMateriaHorario().getIdMateria().equals(nuevoHorario.getMateriaHorario().getIdMateria())*/) {
                return true;
            }
        }
        return false;
    }

    private void Guardar() throws ListaVacia {
//        if (DateHorario.getDate() == null) {
//            JOptionPane.showMessageDialog(null, "Falta seleccionar el día", "Error", JOptionPane.WARNING_MESSAGE);
//        } 
        if(cbxDiaHorario.getSelectedIndex() == -1){
            JOptionPane.showMessageDialog(null, "Falta seleccionar el día", "Error", JOptionPane.WARNING_MESSAGE);
        }
        else if (txtHoraInicio.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Falta llenar la hora de inicio", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else if (txtHoraFin.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Falta llenar la hora de fin", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else if (cbxMateria.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Falta seleccionar la materia", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else {
            // Obtener datos del horario
//            Date dia = DateHorario.getDate();
//            String diaHorario = Formato.format(dia);
            String diaHorario = cbxDiaHorario.getSelectedItem().toString();
            String horaInicio = txtHoraInicio.getText();
            String horaFin = txtHoraFin.getText();
            Materia materia = UtilVista.obtenerComboMateria(cbxMateria);
            
            Horario nuevoHorario = new Horario();
            nuevoHorario.setDiaSemana(diaHorario);
            nuevoHorario.setHoraIncio(horaInicio);
            nuevoHorario.setHoraFin(horaFin);
            nuevoHorario.setMateriaHorario(materia);

            if (horarioExiste(nuevoHorario)) {
                JOptionPane.showMessageDialog(null, "El horario ya existe en este día y horario", "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            Integer idHorario = listaHorarios.getLongitud() + 1;
            String codigo = generarCodigo();

            nuevoHorario.setIdHorario(idHorario);
            nuevoHorario.setCodigoHorario(codigo);

            horarioControlDao.setHorarios(nuevoHorario);
            try {
                if (horarioControlDao.Persist()) {
                    JOptionPane.showMessageDialog(null, "Horario guardado exitosamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                    horarioControlDao.setHorarios(null);
                } 
                else {
                    JOptionPane.showMessageDialog(null, "No se pudo guardar el horario", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
            Limpiar();
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
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtHoraInicio = new javax.swing.JTextField();
        txtHoraFin = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHorario = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        cbxTipoOrden = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        cbxMateria = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txtCodigoHorario = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        cbxOrden = new javax.swing.JComboBox<>();
        btnOrdenar = new javax.swing.JButton();
        cbxTipoBusqueda = new javax.swing.JComboBox<>();
        cbxDiaHorario = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GESTION DE HORARIOS");

        jPanel1.setBackground(new java.awt.Color(190, 193, 197));

        jPanel2.setBackground(new java.awt.Color(61, 90, 134));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/LojoUNL.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Haettenschweiler", 0, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("CENTRO DE CONTROL DE HORARIOS");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94)
                .addComponent(jLabel2)
                .addContainerGap(339, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Dia");

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Hora inicio");

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Hora fin");

        txtHoraInicio.setToolTipText("");
        txtHoraInicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHoraInicioKeyTyped(evt);
            }
        });

        txtHoraFin.setToolTipText("");
        txtHoraFin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHoraFinKeyTyped(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Candara Light", 1, 32)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Informacion");

        jLabel8.setFont(new java.awt.Font("Candara Light", 1, 32)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Lista de horarios");

        tblHorario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblHorario.setSelectionBackground(new java.awt.Color(200, 222, 180));
        tblHorario.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tblHorario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHorarioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHorario);

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
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Regresar.png"))); // NOI18N
        jButton4.setText("REGRESAR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Guardar.png"))); // NOI18N
        jButton5.setText("GUARDAR");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Buscar por");

        cbxTipoOrden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dia", "Codigo", "Hora de inicio", "Hora de fin", "Materia" }));
        cbxTipoOrden.setSelectedIndex(-1);

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Busqueda");

        txtBuscar.setToolTipText("");

        jButton1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Buscar.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Materia");

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Codigo");

        txtCodigoHorario.setEditable(false);
        txtCodigoHorario.setEnabled(false);

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

        cbxTipoBusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dia", "Codigo", "Hora de inicio", "Hora de fin", "Materia" }));
        cbxTipoBusqueda.setSelectedIndex(-1);

        cbxDiaHorario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lunes", "Martes", "Miercoles", "Jueves", "VIernes" }));
        cbxDiaHorario.setSelectedIndex(-1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton4)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCodigoHorario)
                            .addComponent(txtHoraFin, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                            .addComponent(txtHoraInicio)
                            .addComponent(cbxMateria, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxDiaHorario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxTipoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
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
                        .addComponent(jLabel7)
                        .addComponent(jLabel8))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbxOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbxTipoOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel12))
                            .addComponent(btnOrdenar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(cbxTipoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxDiaHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtCodigoHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtHoraInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtHoraFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(cbxMateria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
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

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        
        try {
//            if (DateHorario.getDate() == null) {
//                JOptionPane.showMessageDialog(null, "Falta seleccionar el dia", "Error", JOptionPane.WARNING_MESSAGE);
//            } 
            if (cbxDiaHorario.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar el día", "Error", JOptionPane.WARNING_MESSAGE);
            }
            else if (txtHoraInicio.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar hora inicio", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (txtHoraFin.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar hora fin", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (cbxMateria.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar asistencia", "Error", JOptionPane.WARNING_MESSAGE);
            }
            else {
                Guardar();
            }
        } 
        catch (Exception e) {

        }
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
        VistaPersonalAdministracion abrirLogin = new VistaPersonalAdministracion();
        abrirLogin.setVisible(true);
        this.setVisible(false);
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        int fila = tblHorario.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Escoga un registro");
        } 
        else {
//            if (DateHorario.getDate() == null) {
//                JOptionPane.showMessageDialog(null, "Falta seleccionar el dia", "Error", JOptionPane.WARNING_MESSAGE);
//            } 
            if (cbxDiaHorario.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar el día", "Error", JOptionPane.WARNING_MESSAGE);
            }
            else if (txtHoraInicio.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar hora inicio", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (txtHoraFin.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar hora fin", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (cbxMateria.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar asistencia", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else {

                Integer IdHorario = horarioControlDao.getHorarios().getIdHorario();
//                Date Dia = DateHorario.getDate();
//                String diaHorario = Formato.format(Dia);
                String diaHorario = cbxDiaHorario.getSelectedItem().toString();
                String Codigo = txtCodigoHorario.getText();
                String Inicio = txtHoraInicio.getText();
                String Fin = txtHoraFin.getText();

                Horario horarioModiPersona = new Horario();
                horarioModiPersona.setIdHorario(IdHorario);
                horarioModiPersona.setDiaSemana(diaHorario);
                horarioModiPersona.setCodigoHorario(Codigo);
                horarioModiPersona.setHoraIncio(Inicio);
                horarioModiPersona.setHoraFin(Fin);
                horarioModiPersona.setMateriaHorario(UtilVista.obtenerComboMateria(cbxMateria));

                horarioControlDao.Merge(horarioModiPersona, IdHorario - 1);

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
        
        int fila = tblHorario.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Escoga un registro");
        } 
        else {
            horarioControlDao.Eliminar(fila);
            CargarTabla();
        }
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tblHorarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHorarioMouseClicked
        
        Seleccionar();
        
    }//GEN-LAST:event_tblHorarioMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        try {
            if (cbxTipoBusqueda.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Porfavor seleccione donde quiere buscar", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else {
                ListaDinamica<Horario> lista = horarioControlDao.all();

                String Campo = txtBuscar.getText();
                String TipoCampo = cbxTipoOrden.getSelectedItem().toString();

                switch (TipoCampo) {
                    case "Dia":
                        TipoCampo = "DiaSemana";
                        break;
                    case "Hora de inicio":
                        TipoCampo = "HoraIncio";
                        break;
                    case "Hora de fin":
                        TipoCampo = "HoraFin";
                        break;
                    case "Materia":
                        TipoCampo = "materiaHorario.NombreMateria";
                        break;
                    case "Codigo":
                        TipoCampo = "CodigoHorario";
                        break;
                    default:
                        throw new AssertionError();
                }

                ListaDinamica<Horario> ResultadoBusqueda = UtilesControlador.BusquedaLineal(lista, Campo, TipoCampo);

                mth.setHorarioTabla(ResultadoBusqueda);
                mth.fireTableDataChanged();
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
                ListaDinamica<Horario> lista = horarioControlDao.all();
                String TipoCampo = cbxTipoOrden.getSelectedItem().toString();

                switch (TipoCampo) {
                    case "Dia":
                        TipoCampo = "DiaSemana";
                        break;
                    case "Hora de inicio":
                        TipoCampo = "HoraIncio";
                        break;
                    case "Hora de fin":
                        TipoCampo = "HoraFin";
                        break;
                    case "Materia":
                        TipoCampo = "materiaHorario.NombreMateria";
                        break;
                    case "Codigo":
                        TipoCampo = "CodigoHorario";
                        break;
                    default:
                        throw new AssertionError();
                }

                Integer orden = OrdenSeleccionado();

                ListaDinamica<Horario> resultadoOrdenado = UtilesControlador.QuickSort(lista, orden, TipoCampo);

                mth.setHorarioTabla(resultadoOrdenado);
                mth.fireTableDataChanged();
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }//GEN-LAST:event_btnOrdenarActionPerformed

    private void txtHoraInicioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHoraInicioKeyTyped
        
        Character c = evt.getKeyChar();

        if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != ':') {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Solo ingreso de números y ':'", "CARACTER NO VALIDO", JOptionPane.WARNING_MESSAGE);
        }
        if (txtHoraInicio.getText().length() > 5 && c != KeyEvent.VK_BACK_SPACE) {
            evt.consume();
        }
        
    }//GEN-LAST:event_txtHoraInicioKeyTyped

    private void txtHoraFinKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHoraFinKeyTyped
        
        Character c = evt.getKeyChar();

        if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != ':') {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Solo ingreso de números y ':'", "CARACTER NO VALIDO", JOptionPane.WARNING_MESSAGE);
        }
        if (txtHoraInicio.getText().length() > 5 && c != KeyEvent.VK_BACK_SPACE) {
            evt.consume();
        }
        
    }//GEN-LAST:event_txtHoraFinKeyTyped

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
            java.util.logging.Logger.getLogger(VistaGestionHorario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaGestionHorario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaGestionHorario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaGestionHorario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new VistaGestionHorario().setVisible(true);
                } 
                catch (ListaVacia ex) {

                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOrdenar;
    private javax.swing.JComboBox<String> cbxDiaHorario;
    private javax.swing.JComboBox<String> cbxMateria;
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
    private javax.swing.JTable tblHorario;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCodigoHorario;
    private javax.swing.JTextField txtHoraFin;
    private javax.swing.JTextField txtHoraInicio;
    // End of variables declaration//GEN-END:variables
}
