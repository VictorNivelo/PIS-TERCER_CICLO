
package Vista;

import Controlador.Dao.Modelo.cursoDao;
import Controlador.Dao.Modelo.horarioDao;
import Controlador.Dao.Modelo.periodoAcademicoDao;
import Controlador.Dao.Modelo.presentacionDao;
import Controlador.TDA.ListaDinamica.Excepcion.ListaVacia;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.ControlAccesoAlumno;
import Modelo.Cursa;
import Modelo.Horario;
import Modelo.Materia;
import Modelo.PeriodoAcademico;
import Modelo.Presentacion;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author Victor
 */
public class VistaAlumnoPrincipal extends javax.swing.JFrame {
    horarioDao horarioControlDao = new horarioDao();;
    cursoDao cursaControlDao = new cursoDao();
    periodoAcademicoDao periodoControlDao = new periodoAcademicoDao();
    presentacionDao presentacionControlDao = new presentacionDao();
    private ListaDinamica<Presentacion> listaPresentacion = presentacionControlDao.all();
    private ListaDinamica<String> imagenes = new ListaDinamica<>();
    
    private int indiceImagenActual = -1;
    private Timer timer;    

    /**
     * Creates new form VistaAlumnoPrincipal
     * @throws Controlador.TDA.ListaDinamica.Excepcion.ListaVacia
     */
    public VistaAlumnoPrincipal() throws ListaVacia {
        initComponents();
        this.setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/Vista/RecursosGraficos/IconoPrograma.png")).getImage());
        cargarMateriasAlumno();
        mostrarNombreAlumno();

        for (int i = 0; i < listaPresentacion.getLongitud(); i++) {
            try {
                String imagen = listaPresentacion.getInfo(i).getImagen();
                imagenes.Agregar(imagen);
                System.out.println(i);
            } 
            catch (ListaVacia ex) {
                
            }
        }

        cambiarImagen();
  
        timer = new Timer(2500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cambiarImagen();
                } 
                catch (ListaVacia ex) {

                }
            }
        });
        timer.start();
    }
    
    private String CargarImagen() throws ListaVacia {
        try {
            String rutaImagen = imagenes.getInfo(indiceImagenActual);
            if (rutaImagen.startsWith("src/")) {
                rutaImagen = rutaImagen.substring(3);
            }
            return rutaImagen;
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            return "";
        }
    }

    private void cambiarImagen() throws ListaVacia {
        int intentos = 0;
        do {
            indiceImagenActual = (indiceImagenActual + 1) % imagenes.getLongitud();
            intentos++;
        } 
        while (!"Activa".equalsIgnoreCase(listaPresentacion.getInfo(indiceImagenActual).getEstadoPresentacion()) && intentos < imagenes.getLongitud());

        panelPrincipal.repaint(); 
    }
    
//    private void cambiarImagen() {
//        indiceImagenActual = (indiceImagenActual + 1) % imagenes.length;
//        panelPrincipal.repaint();
//    }

    private void cargarMateriasAlumno() {
        int idAlumnoLogeado = ControlAccesoAlumno.getIdAlumnoLogeado();
        cbxMateriaAlumno.removeAllItems();

        try {
            ListaDinamica<Cursa> listaCursas = obtenerCursasPorAlumno(idAlumnoLogeado);

            for (Cursa cursa : listaCursas.toArray()) {
                Materia materia = cursa.getMateriaCursa();
                if (materia != null) {
                    cbxMateriaAlumno.addItem(materia.getNombreMateria());
                }
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ListaDinamica<Cursa> obtenerCursasPorAlumno(int idAlumno) {
        ListaDinamica<Cursa> cursasPorAlumno = new ListaDinamica<>();
        ListaDinamica<Cursa> listaMaterias = cursaControlDao.all();

        for (Cursa cursa : listaMaterias.toArray()) {
            if (cursa.getMatriculaCursa().getAlumnoMatricula().getIdAlumno() == idAlumno) {
                cursasPorAlumno.Agregar(cursa);
            }
        }
        return cursasPorAlumno;
    }
    
    private void mostrarNombreAlumno() {
        String nombreAlumno = ControlAccesoAlumno.getNombreAlumnoLogeado();
        lblNombreAlumnoIngresado.setText(nombreAlumno);
    }
    
    @SuppressWarnings("unused")
    private boolean esDiaLaborable(String diaSemana, Date fechaInicioPeriodo, Date fechaFinPeriodo) {
        Date fechaActual = new Date();
        if (fechaActual.compareTo(fechaInicioPeriodo) >= 0 && fechaActual.compareTo(fechaFinPeriodo) <= 0) {
            String[] diasLaborables = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes"};

            for (String dia : diasLaborables) {
                if (dia.equalsIgnoreCase(diaSemana)) {
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressWarnings("unused")
    private Date convertirStringADate(String fechaString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.parse(fechaString);
        } 
        catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    @SuppressWarnings("unused")
    public static PeriodoAcademico obtenerPeriodoAcademicoActivo(ListaDinamica<PeriodoAcademico> periodos) {
        Date fechaActual = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaActualString = sdf.format(fechaActual);

        for (PeriodoAcademico periodo : periodos.toArray()) {
            try {
                Date fechaInicio = sdf.parse(periodo.getFechaInicio());
                Date fechaFin = sdf.parse(periodo.getFechaFin());

                if (fechaActual.after(fechaInicio) && fechaActual.before(fechaFin)) {
                    return periodo;
                }
            } 
            catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static boolean fechaEnPeriodoAcademico(Date fecha, PeriodoAcademico periodo) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date fechaInicio = sdf.parse(periodo.getFechaInicio());
            Date fechaFin = sdf.parse(periodo.getFechaFin());

            return fecha.after(fechaInicio) && fecha.before(fechaFin);
        } 
        catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    ImageIcon icon = new ImageIcon(getClass().getResource(CargarImagen()));
                    g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
                } 
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblNombreAlumnoIngresado = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();
        btnMostrarCalendario = new javax.swing.JButton();
        cbxMateriaAlumno = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        btnVerMateria = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("VISTA GENERAL ALUMNO");

        panelPrincipal.setBackground(new java.awt.Color(190, 193, 197));

        jPanel3.setBackground(new java.awt.Color(61, 90, 134));
        jPanel3.setPreferredSize(new java.awt.Dimension(0, 140));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/LojoUNL.png"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe Script", 1, 34)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Bienvenido");

        lblNombreAlumnoIngresado.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        lblNombreAlumnoIngresado.setForeground(new java.awt.Color(255, 255, 255));
        lblNombreAlumnoIngresado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNombreAlumnoIngresado.setText(" ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jLabel3)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(338, 338, 338)
                        .addComponent(jLabel1))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(175, 175, 175)
                        .addComponent(lblNombreAlumnoIngresado, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(318, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNombreAlumnoIngresado)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnRegresar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btnRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/CerraSesion.png"))); // NOI18N
        btnRegresar.setText("CERRAR SESION");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        btnMostrarCalendario.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btnMostrarCalendario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Calendario.png"))); // NOI18N
        btnMostrarCalendario.setText("CALENDARIO");
        btnMostrarCalendario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarCalendarioActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Century", 1, 32)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("MATERIA");

        btnVerMateria.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btnVerMateria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/PorcentajeAsistencia.png"))); // NOI18N
        btnVerMateria.setText("VER MATERIA");
        btnVerMateria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerMateriaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1200, Short.MAX_VALUE)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelPrincipalLayout.createSequentialGroup()
                                .addComponent(btnRegresar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnMostrarCalendario))
                            .addGroup(panelPrincipalLayout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbxMateriaAlumno, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnVerMateria, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxMateriaAlumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVerMateria)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 411, Short.MAX_VALUE)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegresar)
                    .addComponent(btnMostrarCalendario))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed

        try {
            int result = JOptionPane.showConfirmDialog(null, "Estas seguro que quieres cerrar sesion?", "CONFIRMAR CIERRE DE SESION", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (result == JOptionPane.YES_OPTION) {
                VistaPrincipalSistema abrirLogin = new VistaPrincipalSistema();
                abrirLogin.setVisible(true);
                this.setVisible(false);
            }
        }
        catch (Exception e) {

        }

    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnMostrarCalendarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarCalendarioActionPerformed
        
        try {
            VistaCalendarioAlumno vistaCalendarioAlumno = new VistaCalendarioAlumno();

            Integer idAlumnoLogeado = ControlAccesoAlumno.getIdAlumnoLogeado();
            if (idAlumnoLogeado != null) {
                ListaDinamica<Horario> listaHorarios = horarioControlDao.all();
                ListaDinamica<Cursa> listaCursas = cursaControlDao.all();

                for (int i = 0; i < listaHorarios.getLongitud(); i++) {
                    Horario horario = listaHorarios.getInfo(i);
                    int idMateriaHorario = horario.getMateriaHorario().getIdMateria();

                    for (int j = 0; j < listaCursas.getLongitud(); j++) {
                        Cursa cursa = listaCursas.getInfo(j);
                        int idAlumnoCursa = cursa.getMatriculaCursa().getAlumnoMatricula().getIdAlumno();

                        if (idAlumnoCursa == idAlumnoLogeado && idMateriaHorario == cursa.getMateriaCursa().getIdMateria()) {
                            String diaSemana = horario.getDiaSemana();
                            String nombreMateria = cursa.getMateriaCursa().getNombreMateria();
                            String horaInicio = horario.getHoraIncio();
                            String horaFin = horario.getHoraFin();
                            String mensaje = nombreMateria + " - " + horaInicio + " - " + horaFin;

                            vistaCalendarioAlumno.AgregarEvento(diaSemana, mensaje);
                        }
                    }
                }

                vistaCalendarioAlumno.setVisible(true);
                this.setVisible(false);
            } 
            else {
                System.out.println("No hay alumno logeado en este momento.");
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }

        
//        try {
//            VistaCalendarioAlumno vistaCalendarioAlumno = new VistaCalendarioAlumno();
//
//            Integer idAlumnoLogeado = ControlAccesoAlumno.getIdAlumnoLogeado();
//            if (idAlumnoLogeado != null) {
//                ListaDinamica<Horario> listaHorarios = horarioControlDao.all();
//                ListaDinamica<Cursa> listaCursas = cursaControlDao.all();
//
//                for (int i = 0; i < listaHorarios.getLongitud(); i++) {
//                    Horario horario = listaHorarios.getInfo(i);
//                    int idMateriaHorario = horario.getMateriaHorario().getIdMateria();
//
//                    for (int j = 0; j < listaCursas.getLongitud(); j++) {
//                        Cursa cursa = listaCursas.getInfo(j);
//                        int idAlumnoCursa = cursa.getMatriculaCursa().getAlumnoMatricula().getIdAlumno();
//
//                        if (idAlumnoCursa == idAlumnoLogeado && idMateriaHorario == cursa.getMateriaCursa().getIdMateria()) {
//                            String diaSemana = horario.getDiaSemana();
//                            String nombreMateria = cursa.getMateriaCursa().getNombreMateria();
//                            String horaInicio = horario.getHoraIncio();
//                            String horaFin = horario.getHoraFin();
//                            String mensaje = nombreMateria + " - " + horaInicio + " - " + horaFin;
//
//                            vistaCalendarioAlumno.AgregarEvento(diaSemana, mensaje);
//                        }
//                    }
//                }
//                vistaCalendarioAlumno.setVisible(true);
//                this.setVisible(false);
//            } 
//            else {
//                System.out.println("No hay alumno logeado en este momento.");
//            }
//        } 
//        catch (Exception e) {
//            e.printStackTrace();
//        }
        
    }//GEN-LAST:event_btnMostrarCalendarioActionPerformed

    private void btnVerMateriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerMateriaActionPerformed

        VistaAlumnoMaterias vm = new VistaAlumnoMaterias();
        vm.setVisible(true);
        this.setVisible(false);

    }//GEN-LAST:event_btnVerMateriaActionPerformed

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
            java.util.logging.Logger.getLogger(VistaAlumnoPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaAlumnoPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaAlumnoPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaAlumnoPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new VistaAlumnoPrincipal().setVisible(true);
                } 
                catch (ListaVacia ex) {
                    
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMostrarCalendario;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JButton btnVerMateria;
    private javax.swing.JComboBox<Object> cbxMateriaAlumno;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel3;
    public static javax.swing.JLabel lblNombreAlumnoIngresado;
    private javax.swing.JPanel panelPrincipal;
    // End of variables declaration//GEN-END:variables
}
