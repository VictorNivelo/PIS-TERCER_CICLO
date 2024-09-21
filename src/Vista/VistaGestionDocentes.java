
package Vista;

import Controlador.Dao.Modelo.docenteDao;
import Controlador.Dao.Modelo.personaDao;
import Controlador.TDA.ListaDinamica.Excepcion.ListaVacia;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Controlador.Utiles.UtilesControlador;
import Modelo.Docente;
import Modelo.Persona;
import Vista.ModeloTabla.ModeloTablaDocente;
import Vista.Utiles.UtilVista;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Victor
 */
public class VistaGestionDocentes extends javax.swing.JFrame {
    docenteDao docenteControlDao = new docenteDao();
    ListaDinamica<Docente> listaDocentes = new ListaDinamica<>();
    ModeloTablaDocente mtd = new ModeloTablaDocente();

    /**
     * Creates new form VistaGestionDocentes
     * @throws Controlador.TDA.ListaDinamica.Excepcion.ListaVacia
     */
    public VistaGestionDocentes() throws ListaVacia {
        initComponents();
        this.setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/Vista/RecursosGraficos/IconoPrograma.png")).getImage());
        UtilVista.cargarcomboPersonaDocentes(cbxDocente);
        cbxDocente.setMaximumRowCount(cbxDocente.getItemCount());
        CargarTabla();
    }
    
    private void CargarTabla() {
        mtd.setDocenteTabla(docenteControlDao.getListaDocentes());
        tblDocentes.setModel(mtd);
        tblDocentes.updateUI();
        cbxDocente.setSelectedIndex(-1);
        cbxTipoBusqueda.setSelectedIndex(-1);
    }
    
    private void Limpiar() throws ListaVacia {
        cbxDocente.setSelectedIndex(-1);
        cbxTipoBusqueda.setSelectedIndex(-1);
        txtEspecialidad.setText("");
        txtAniosExperiencia.setText("");
        txtTitulacion.setText("");
        docenteControlDao.setDocentes(null);
        CargarTabla();
    }
    
    private void Seleccionar(){
        int fila = tblDocentes.getSelectedRow();
        if(fila < 0){
            JOptionPane.showMessageDialog(null, "Escoga un registro");
        }
        else{
            try {
                docenteControlDao.setDocentes(mtd.getDocenteTabla().getInfo(fila));
                
                txtEspecialidad.setText(docenteControlDao.getDocentes().getEspecialidad());
                txtTitulacion.setText(docenteControlDao.getDocentes().getTitulacion());
                txtAniosExperiencia.setText(docenteControlDao.getDocentes().getAniosExperiencia());
                cbxDocente.setSelectedIndex(docenteControlDao.getDocentes().getIdDocente()-1);

            } 
            catch (Exception e) {
                
            }
        }
    }
    
    private boolean docenteExiste(Docente nuevoDocente) {
        ListaDinamica<Docente> docentes = docenteControlDao.getListaDocentes();
        if (docentes.EstaVacio()) {
            return false;
        }
        for (Docente d : docentes.toArray()) {
            if (d.getIdDocente().equals(nuevoDocente.getIdDocente())
                    && d.getEspecialidad().equals(nuevoDocente.getEspecialidad())) {
                return true;
            }
        }
        return false;
    }

    private void Guardar() throws ListaVacia {
        if (cbxDocente.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Falta seleccionar un docente", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else if (txtEspecialidad.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Falta llenar la especialidad", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else if (txtTitulacion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Falta llenar la titulación", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else if (txtAniosExperiencia.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Falta llenar los años de experiencia", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else {
            Integer idDocente = listaDocentes.getLongitud() + 1;
            String especialidad = txtEspecialidad.getText();
            String titulacion = txtTitulacion.getText();
            String aniosExperiencia = txtAniosExperiencia.getText();
            Persona datosDocente = UtilVista.obtenerPersonaDocentesControl(cbxDocente);

            Docente nuevoDocente = new Docente();
            nuevoDocente.setIdDocente(idDocente);
            nuevoDocente.setEspecialidad(especialidad);
            nuevoDocente.setTitulacion(titulacion);
            nuevoDocente.setAniosExperiencia(aniosExperiencia);
            nuevoDocente.setDatosDocente(datosDocente);

            if (docenteExiste(nuevoDocente)) {
                JOptionPane.showMessageDialog(null, "El docente ya existe", "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            docenteControlDao.setDocentes(nuevoDocente);
            try {
                if (docenteControlDao.Persist()) {
                    JOptionPane.showMessageDialog(null, "Docente guardado exitosamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                    docenteControlDao.setDocentes(null);
                } 
                else {
                    JOptionPane.showMessageDialog(null, "No se pudo guardar el docente", "Error", JOptionPane.ERROR_MESSAGE);
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

        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cbxDocente = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtEspecialidad = new javax.swing.JTextField();
        txtTitulacion = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDocentes = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        cbxTipoBusqueda = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtAniosExperiencia = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtDocenteBusqueda = new javax.swing.JTextField();
        btnBuscarDocente = new javax.swing.JButton();
        btnOrdenar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        cbxOrden = new javax.swing.JComboBox<>();
        cbxTipoOrden = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GESTION DE DOCENTES");

        jPanel3.setBackground(new java.awt.Color(191, 192, 197));

        jPanel4.setBackground(new java.awt.Color(61, 90, 134));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/LojoUNL.png"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Haettenschweiler", 0, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("SERVICIO DE GESTION DE DOCENTES");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(104, 104, 104)
                .addComponent(jLabel1)
                .addContainerGap(318, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Docente");

        jLabel4.setFont(new java.awt.Font("Candara Light", 1, 32)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Informacion");

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Especialidad");

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Titulacion");

        txtEspecialidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEspecialidadKeyTyped(evt);
            }
        });

        txtTitulacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTitulacionKeyTyped(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Regresar.png"))); // NOI18N
        jButton1.setText("REGRESAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Guardar.png"))); // NOI18N
        jButton2.setText("GUARDAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Candara Light", 1, 32)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Lista de docentes");

        tblDocentes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblDocentes.setSelectionBackground(new java.awt.Color(200, 222, 180));
        tblDocentes.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tblDocentes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDocentesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDocentes);

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Buscar por");

        cbxTipoBusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Numero de cedula", "Nombre", "Apellido", "Genero", "Especialidad", "Titulacion", "Experiencia" }));
        cbxTipoBusqueda.setSelectedIndex(-1);

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Busqueda");

        jButton3.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Buscar.png"))); // NOI18N
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

        jLabel13.setFont(new java.awt.Font("Candara Light", 1, 32)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Docente");
        jLabel13.setToolTipText("");

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Años de experiencia");

        txtAniosExperiencia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAniosExperienciaKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Docente");

        txtDocenteBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDocenteBusquedaKeyTyped(evt);
            }
        });

        btnBuscarDocente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Buscar.png"))); // NOI18N
        btnBuscarDocente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarDocenteActionPerformed(evt);
            }
        });

        btnOrdenar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Ordenar.png"))); // NOI18N
        btnOrdenar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdenarActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Ordenar");

        cbxOrden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Asendente", "Desendente" }));
        cbxOrden.setSelectedIndex(-1);

        cbxTipoOrden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Numero de cedula", "Nombre", "Apellido", "Genero", "Especialidad", "Titulacion", "Experiencia" }));
        cbxTipoOrden.setSelectedIndex(-1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAniosExperiencia))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTitulacion))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEspecialidad))
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxDocente, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDocenteBusqueda)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscarDocente))
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 633, Short.MAX_VALUE)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4))
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxTipoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(162, 162, 162)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxTipoOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOrdenar)))
                .addContainerGap())
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(jLabel13))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbxOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbxTipoOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9))
                            .addComponent(btnOrdenar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(cbxTipoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnBuscarDocente)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtDocenteBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cbxDocente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtTitulacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtAniosExperiencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        try {
            if (cbxDocente.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar docente", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (txtEspecialidad.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar especialidad", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (txtTitulacion.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar titulacion", "Error", JOptionPane.WARNING_MESSAGE);
            }
            else if (txtAniosExperiencia.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar años de experiencia", "Error", JOptionPane.WARNING_MESSAGE);
            }
            else {
                Guardar();
            }
        } 
        catch (Exception e) {

        }
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        VistaPersonalAdministracion abrirLogin = new VistaPersonalAdministracion();
        abrirLogin.setVisible(true);
        this.setVisible(false);
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblDocentesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDocentesMouseClicked
        
        Seleccionar();
        
    }//GEN-LAST:event_tblDocentesMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
        int fila = tblDocentes.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Escoga un registro");
        } 
        else {
            if (cbxDocente.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar docente", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (txtEspecialidad.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar especialidad", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (txtTitulacion.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar titulacion", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (txtAniosExperiencia.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta llenar años de experiencia", "Error", JOptionPane.WARNING_MESSAGE);
            }
            else {

                Integer IdDocente = docenteControlDao.getDocentes().getIdDocente();
                String Especialidad = txtEspecialidad.getText();
                String Titulacion = txtTitulacion.getText();
                String A = txtAniosExperiencia.getText();
                
                Docente docenteModificado = new Docente();
                docenteModificado.setIdDocente(IdDocente);
                docenteModificado.setEspecialidad(Especialidad);
                docenteModificado.setTitulacion(Titulacion);
                docenteModificado.setAniosExperiencia(A);
                docenteModificado.setDatosDocente(UtilVista.obtenerPersonaDocentesControl(cbxDocente));

                docenteControlDao.Merge(docenteModificado, IdDocente-1);

                CargarTabla();

                try {
                    Limpiar();
                } 
                catch (Exception e) {

                }
            }
        }
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        
        int fila = tblDocentes.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Escoga un registro");
        } 
        else {
            try {
                docenteControlDao.Eliminar(fila);
                CargarTabla();
                Limpiar();
            } 
            catch (Exception e) {
                
            }
        }
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
        try {
            if (cbxTipoBusqueda.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Porfavor seleccione donde quiere buscar", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else {
                ListaDinamica<Docente> lista = docenteControlDao.all();

                String Campo = txtBuscar.getText();
                String TipoCampo = cbxTipoBusqueda.getSelectedItem().toString();

                switch (TipoCampo) {
                    case "Numero de cedula":
                        TipoCampo = "DatosDocente.NumeroCedula";
                        break;
                    case "Nombre":
                        TipoCampo = "DatosDocente.Nombre";
                        break;
                    case "Apellido":
                        TipoCampo = "DatosDocente.Apellido";
                        break;
                    case "Genero":
                        TipoCampo = "DatosDocente.Genero";
                        break;
                    case "Especialidad":
                        TipoCampo = "Especialidad";
                        break;
                    case "Titulacion":
                        TipoCampo = "Titulacion";
                        break;
                    case "Años ecperiencia":
                        TipoCampo = "AniosExperiencia";
                        break;
                    case "Experiencia":
                        TipoCampo = "AniosExperiencia";
                        break;
                    default:
                        throw new AssertionError();
                }

                ListaDinamica<Docente> ResultadoBusqueda = UtilesControlador.BusquedaLineal(lista, Campo, TipoCampo);

                mtd.setDocenteTabla(ResultadoBusqueda);
                mtd.fireTableDataChanged();
            }
        } 
        catch (Exception e) {

        }
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtEspecialidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEspecialidadKeyTyped
        
        char c = evt.getKeyChar();

        if (!Character.isLetter(c) && c != KeyEvent.VK_BACK_SPACE && c != ' ') {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Solo ingreso de letras", "CARACTER NO VALIDO", JOptionPane.WARNING_MESSAGE);
        }
        if (c != KeyEvent.VK_BACK_SPACE) {

        }
        
    }//GEN-LAST:event_txtEspecialidadKeyTyped

    private void txtTitulacionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTitulacionKeyTyped
        
        char c = evt.getKeyChar();

        if (!Character.isLetter(c) && c != KeyEvent.VK_BACK_SPACE && c != ' ') {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Solo ingreso de letras", "CARACTER NO VALIDO", JOptionPane.WARNING_MESSAGE);
        }
        if (c != KeyEvent.VK_BACK_SPACE) {

        }
        
    }//GEN-LAST:event_txtTitulacionKeyTyped

    private void txtAniosExperienciaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAniosExperienciaKeyTyped
        
        Character c = evt.getKeyChar();

        if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Solo ingreso de numeros", "CARACTER NO VALIDO", JOptionPane.WARNING_MESSAGE);
        }
        
    }//GEN-LAST:event_txtAniosExperienciaKeyTyped

    private void btnBuscarDocenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarDocenteActionPerformed

        try {
            if (txtDocenteBusqueda.getText().isEmpty()) {
                UtilVista.cargarcomboPersonaDocentes(cbxDocente);
                JOptionPane.showMessageDialog(null, "Ingrese el docente a buscar", "FALTA LLENAR", JOptionPane.WARNING_MESSAGE);
            } 
            else {
                personaDao PD = new personaDao();
                ListaDinamica<Persona> lista = PD.all();

                String Campo = txtDocenteBusqueda.getText();

                ListaDinamica<Persona> ResultadoBusqueda = new ListaDinamica<>();

                ListaDinamica<Persona> ResultadoCe = UtilesControlador.BusquedaLineal(lista, Campo, "NumeroCedula");
                ResultadoBusqueda.concatenar(ResultadoCe);

                ListaDinamica<Persona> ResultadoN = UtilesControlador.BusquedaLineal(lista, Campo, "Nombre");
                ResultadoBusqueda.concatenar(ResultadoN);
                
                ListaDinamica<Persona> ResultadoA = UtilesControlador.BusquedaLineal(lista, Campo, "Apellido");
                ResultadoBusqueda.concatenar(ResultadoA);

                cbxDocente.removeAllItems();

                for (Persona pb : ResultadoBusqueda.toArray()) {
                    if (pb.getRolPersona().getNombreRol().equals("Docente")) {
                        cbxDocente.addItem(pb);
                    }
                }
            }

        }
        catch (Exception e) {

        }

    }//GEN-LAST:event_btnBuscarDocenteActionPerformed

    private void txtDocenteBusquedaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDocenteBusquedaKeyTyped
        
//        Character c = evt.getKeyChar();
//
//        if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
//            evt.consume();
//            JOptionPane.showMessageDialog(null, "Solo ingreso de numeros", "CARACTER NO VALIDO", JOptionPane.WARNING_MESSAGE);
//        }
//        if (txtDocenteBusqueda.getText().length() >= 10 && c != KeyEvent.VK_BACK_SPACE) {
//            evt.consume();
//        }
        
    }//GEN-LAST:event_txtDocenteBusquedaKeyTyped

    private void btnOrdenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdenarActionPerformed

        try {
            if (cbxTipoOrden.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "No ha seleccionado el campo", "FALTA SELCCIONAR", JOptionPane.WARNING_MESSAGE);
            } 
            else if (cbxOrden.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "No ha seleccionado el orden", "FALTA SELCCIONAR", JOptionPane.WARNING_MESSAGE);
            }
            else {
                ListaDinamica<Docente> lista = docenteControlDao.all();
                String TipoCampo = cbxTipoOrden.getSelectedItem().toString();

                switch (TipoCampo) {
                    case "Numero de cedula":
                        TipoCampo = "DatosDocente.NumeroCedula";
                        break;
                    case "Nombre":
                        TipoCampo = "DatosDocente.Nombre";
                        break;
                    case "Apellido":
                        TipoCampo = "DatosDocente.Apellido";
                        break;
                    case "Genero":
                        TipoCampo = "DatosDocente.Genero";
                        break;
                    case "Especialidad":
                        TipoCampo = "Especialidad";
                        break;
                    case "Titulacion":
                        TipoCampo = "Titulacion";
                        break;
                    case "Años ecperiencia":
                        TipoCampo = "AniosExperiencia";
                        break;
                    case "Experiencia":
                        TipoCampo = "AniosExperiencia";
                        break;
                    default:
                        throw new AssertionError();
                }

                Integer orden = OrdenSeleccionado();

                ListaDinamica<Docente> resultadoOrdenado = UtilesControlador.QuickSort(lista, orden, TipoCampo);

                mtd.setDocenteTabla(resultadoOrdenado);
                mtd.fireTableDataChanged();
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
            java.util.logging.Logger.getLogger(VistaGestionDocentes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaGestionDocentes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaGestionDocentes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaGestionDocentes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new VistaGestionDocentes().setVisible(true);
                } 
                catch (ListaVacia ex) {
                    
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarDocente;
    private javax.swing.JButton btnOrdenar;
    private javax.swing.JComboBox<Persona> cbxDocente;
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
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JTable tblDocentes;
    private javax.swing.JTextField txtAniosExperiencia;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtDocenteBusqueda;
    private javax.swing.JTextField txtEspecialidad;
    private javax.swing.JTextField txtTitulacion;
    // End of variables declaration//GEN-END:variables
}
