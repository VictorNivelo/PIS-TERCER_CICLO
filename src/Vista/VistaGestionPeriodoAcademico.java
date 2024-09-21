
package Vista;

import Controlador.Dao.Modelo.periodoAcademicoDao;
import Controlador.TDA.ListaDinamica.Excepcion.ListaVacia;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Controlador.Utiles.UtilesControlador;
import Modelo.PeriodoAcademico;
import Vista.ModeloTabla.ModeloTablaPeriodo;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Victor
 */
public class VistaGestionPeriodoAcademico extends javax.swing.JFrame {
    periodoAcademicoDao periodoControlDao = new periodoAcademicoDao();
    ModeloTablaPeriodo mtp = new ModeloTablaPeriodo();
    ListaDinamica<PeriodoAcademico> listaPeriodos = new ListaDinamica<>();
    SimpleDateFormat Formato = new SimpleDateFormat("dd/MMMM/yyyy");

    /**
     * Creates new form VistaGestionPeriodoAcademico
     */
    public VistaGestionPeriodoAcademico() {
        initComponents();
        this.setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/Vista/RecursosGraficos/IconoPrograma.png")).getImage());
        DateInicio.setDateFormatString("dd/MMMM/yyyy");
        DateFin.setDateFormatString("dd/MMMM/yyyy");
        CargarTabla();
    }
    
    private void CargarTabla(){
        mtp.setPeriodosTabla(periodoControlDao.all());
        tblPeriodos.setModel(mtp);
        tblPeriodos.updateUI();
        cbxEstadoPeriodo.setSelectedIndex(0);
        cbxEstadoPeriodo.setEnabled(false);
    }
    
    public void Limpiar() {
        DateInicio.setDate(null);
        DateFin.setDate(null);
        cbxEstadoPeriodo.setSelectedIndex(-1);
        periodoControlDao.setPeriodo(null);
        CargarTabla();
    }
    
    private void Seleccionar(){
        int fila = tblPeriodos.getSelectedRow();
        if(fila < 0){
            JOptionPane.showMessageDialog(null, "Escoga un registro");
        }
        else{
            try {
                cbxEstadoPeriodo.setEnabled(true);
                periodoControlDao.setPeriodo(mtp.getPeriodosTabla().getInfo(fila));
                Date Inicio = Formato.parse(periodoControlDao.getPeriodo().getFechaInicio());
                Date Fin = Formato.parse(periodoControlDao.getPeriodo().getFechaFin());
                DateInicio.setDate(Inicio);
                DateFin.setDate(Fin);
                cbxEstadoPeriodo.setSelectedItem(periodoControlDao.getPeriodo().getEstadoPeriodoAcedemico());
            } 
            catch (Exception e) {
                
            }
        }
    }
    
    private boolean periodoExiste(PeriodoAcademico nuevoPeriodo) {
        ListaDinamica<PeriodoAcademico> periodos = periodoControlDao.all();
        if (periodos.EstaVacio()) {
            return false;
        }
        for (PeriodoAcademico p : periodos.toArray()) {
            if (p.getFechaInicio().equals(nuevoPeriodo.getFechaInicio())
                    && p.getFechaFin().equals(nuevoPeriodo.getFechaFin())) {
                return true;
            }
        }
        return false;
    }

    private void Guardar() throws ListaVacia {
        Date fechaInicio = DateInicio.getDate();
        Date fechaFin = DateFin.getDate();

        if (fechaInicio == null) {
            JOptionPane.showMessageDialog(null, "Falta llenar la fecha de inicio", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else if (fechaFin == null) {
            JOptionPane.showMessageDialog(null, "Falta llenar la fecha de fin", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else if (fechaInicio.compareTo(fechaFin) >= 0) {
            JOptionPane.showMessageDialog(null, "La fecha de inicio no puede ser igual a la fecha de fin", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else if (fechaFin.before(fechaInicio)) {
            JOptionPane.showMessageDialog(null, "La fecha de fin no puede ser antes de la fecha de inicio", "Error", JOptionPane.WARNING_MESSAGE);
        }
        else if (fechaInicio.after(fechaFin)) {
            JOptionPane.showMessageDialog(null, "La fecha de inicio no puede ser después de la fecha de fin", "Error", JOptionPane.WARNING_MESSAGE);
        }
        else if (cbxEstadoPeriodo.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Falta seleccionar el estado del período", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else {
            String estado = cbxEstadoPeriodo.getSelectedItem().toString();
            PeriodoAcademico nuevoPeriodo = new PeriodoAcademico();
            nuevoPeriodo.setFechaInicio(Formato.format(fechaInicio));
            nuevoPeriodo.setFechaFin(Formato.format(fechaFin));
            nuevoPeriodo.setEstadoPeriodoAcedemico(estado);

            if (periodoExiste(nuevoPeriodo)) {
                JOptionPane.showMessageDialog(null, "El período ya existe", "Error", JOptionPane.INFORMATION_MESSAGE);
            } else {
                Integer idPeriodo = listaPeriodos.getLongitud() + 1;
                nuevoPeriodo.setIdPeriodoAcademino(idPeriodo);

                periodoControlDao.setPeriodo(nuevoPeriodo);

                try {
                    if (periodoControlDao.persist()) {
                        JOptionPane.showMessageDialog(null, "Período guardado exitosamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                        periodoControlDao.setPeriodo(null);
                    } 
                    else {
                        JOptionPane.showMessageDialog(null, "No se pudo guardar el período", "Información", JOptionPane.INFORMATION_MESSAGE);
                    }
                } 
                catch (Exception e) {
                    e.printStackTrace();
                }
                cbxEstadoPeriodo.setEnabled(false);
                Limpiar();
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

        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPeriodos = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        cbxTipoBusqueda = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        DateInicio = new com.toedter.calendar.JDateChooser();
        DateFin = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        cbxEstadoPeriodo = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        cbxOrden = new javax.swing.JComboBox<>();
        btnOrdenar = new javax.swing.JButton();
        cbxTipoOrden = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GESTION DE PERIODOS ACADEMICOS");

        jPanel2.setBackground(new java.awt.Color(190, 193, 197));

        jPanel3.setBackground(new java.awt.Color(61, 90, 134));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/LojoUNL.png"))); // NOI18N

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Haettenschweiler", 0, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("SERVICIO DE ADMINISTRACION DE PERIODOS ACADEMICOS");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addContainerGap(96, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel3.setFont(new java.awt.Font("Candara Light", 1, 32)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Informacion");

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Fecha inicio");

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Fecha fin");

        jLabel6.setFont(new java.awt.Font("Candara Light", 1, 32)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Lista de periodos academicos");

        tblPeriodos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblPeriodos.setSelectionBackground(new java.awt.Color(200, 222, 180));
        tblPeriodos.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tblPeriodos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPeriodosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPeriodos);

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Buscar por");

        cbxTipoBusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fecha de inicio", "Fecha fin", "Estado" }));
        cbxTipoBusqueda.setSelectedIndex(-1);

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Busqueda");
        jLabel8.setToolTipText("");

        jButton1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Buscar.png"))); // NOI18N
        jButton1.setToolTipText("");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

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
        jLabel9.setText("Estado");

        cbxEstadoPeriodo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));
        cbxEstadoPeriodo.setSelectedIndex(-1);

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Ordenar");

        cbxOrden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Asendente", "Desendente" }));
        cbxOrden.setSelectedIndex(-1);

        btnOrdenar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Ordenar.png"))); // NOI18N
        btnOrdenar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdenarActionPerformed(evt);
            }
        });

        cbxTipoOrden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fecha de inicio", "Fecha fin", "Estado" }));
        cbxTipoOrden.setSelectedIndex(-1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(DateFin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(DateInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxEstadoPeriodo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxTipoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxTipoOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOrdenar)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel6))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbxOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbxTipoOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10))
                            .addComponent(btnOrdenar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(cbxTipoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(DateInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(DateFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(cbxEstadoPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblPeriodosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPeriodosMouseClicked
        
        Seleccionar();
        
    }//GEN-LAST:event_tblPeriodosMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        try {
            if (cbxTipoBusqueda.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Porfavor seleccione donde quiere buscar", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else {
                ListaDinamica<PeriodoAcademico> lista = periodoControlDao.all();

                String Campo = txtBuscar.getText();
                String TipoCampo = cbxTipoBusqueda.getSelectedItem().toString();

                switch (TipoCampo) {
                    case "Fecha de inicio":
                        TipoCampo = "FechaInicio";
                        break;
                    case "Fecha fin":
                        TipoCampo = "FechaFin";
                        break;
                    case "Estado":
                        TipoCampo = "EstadoPeriodoAcademico";
                        break;
                    default:
                        throw new AssertionError();
                }

                ListaDinamica<PeriodoAcademico> ResultadoBusqueda = UtilesControlador.BusquedaLineal(lista, Campo, TipoCampo);

                mtp.setPeriodosTabla(ResultadoBusqueda);
                mtp.fireTableDataChanged();
            }
        } 
        catch (Exception e) {

        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        int fila = tblPeriodos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Escoga un registro");
        } else {

            if (DateInicio.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Falta llenar decha de inicio", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (DateFin.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Falta llenar fecha fin", "Error", JOptionPane.WARNING_MESSAGE);
            }
            else if (cbxEstadoPeriodo.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar el periodo", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else {
                Integer IdPeriodo = periodoControlDao.getPeriodo().getIdPeriodoAcademino();
                Date InicioD = DateInicio.getDate();
                Date FinD = DateFin.getDate();
                String Inicio = Formato.format(InicioD);
                String Fin = Formato.format(FinD);
                String Estado = cbxEstadoPeriodo.getSelectedItem().toString();

                PeriodoAcademico periodoModificado = new PeriodoAcademico();
                periodoModificado.setIdPeriodoAcademino(IdPeriodo);
                periodoModificado.setFechaInicio(Inicio);
                periodoModificado.setFechaFin(Fin);
                periodoModificado.setEstadoPeriodoAcedemico(Estado);
                
                periodoControlDao.Merge(periodoModificado, IdPeriodo - 1);

                CargarTabla();
                
                cbxEstadoPeriodo.setEnabled(false);

                Limpiar();
            }

        }
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
        int fila = tblPeriodos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Escoga un registro");
        }
        else {
            periodoControlDao.Eliminar(fila);
            CargarTabla();
            cbxEstadoPeriodo.setEnabled(false);
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
        VistaPersonalAdministracion abrirLogin = new VistaPersonalAdministracion();
        abrirLogin.setVisible(true);
        this.setVisible(false);
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        
        try {
            if (DateInicio.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Falta llenar decha de inicio", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (DateFin.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Falta llenar fecha fin", "Error", JOptionPane.WARNING_MESSAGE);
            }
            else if (cbxEstadoPeriodo.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar el periodo", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else {
                Guardar();
            }
        }
        catch (Exception e) {
            
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
                ListaDinamica<PeriodoAcademico> lista = periodoControlDao.all();
                String TipoCampo = cbxTipoOrden.getSelectedItem().toString();

                switch (TipoCampo) {
                    case "Fecha de inicio":
                        TipoCampo = "FechaInicio";
                        break;
                    case "Fecha fin":
                        TipoCampo = "FechaFin";
                        break;
                    case "Estado":
                        TipoCampo = "EstadoPeriodoAcademico";
                        break;
                    default:
                        throw new AssertionError();
                }

                Integer orden = OrdenSeleccionado();

                ListaDinamica<PeriodoAcademico> resultadoOrdenado = UtilesControlador.QuickSort(lista, orden, TipoCampo);

                mtp.setPeriodosTabla(resultadoOrdenado);
                mtp.fireTableDataChanged();
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
            java.util.logging.Logger.getLogger(VistaGestionPeriodoAcademico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaGestionPeriodoAcademico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaGestionPeriodoAcademico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaGestionPeriodoAcademico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VistaGestionPeriodoAcademico().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DateFin;
    private com.toedter.calendar.JDateChooser DateInicio;
    private javax.swing.JButton btnOrdenar;
    private javax.swing.JComboBox<String> cbxEstadoPeriodo;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPeriodos;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
