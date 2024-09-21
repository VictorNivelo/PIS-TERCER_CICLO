/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista;

import Controlador.Dao.Modelo.personaDao;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Modelo.Cuenta;
import Modelo.Persona;
import Modelo.Rol;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Victor
 */
public class VistaInicioSesionPersonal extends javax.swing.JFrame {
    personaDao personaControlDao = new personaDao();

    /**
     * Creates new form VistaInicioSecionPersonal
     */
    public VistaInicioSesionPersonal() {
        initComponents();
        this.setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/Vista/RecursosGraficos/IconoPrograma.png")).getImage());
        configurarPlaceholders();
        btnRegresar.requestFocus();
    }
    
    private void configurarPlaceholders() {
        Font placeholderFont = new Font("Segoe UI", Font.ITALIC, 14);

        txtCorreo.setForeground(new Color(119, 119, 119));
        txtCorreo.setText("Ingrese su correo");
        txtCorreo.setFont(placeholderFont);
        txtCorreo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtCorreo.getText().equals("Ingrese su correo")) {
                    txtCorreo.setText("");
                    txtCorreo.setForeground(Color.BLACK);
                    txtCorreo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtCorreo.getText().isEmpty()) {
                    txtCorreo.setForeground(new Color(119, 119, 119));
                    txtCorreo.setText("Ingrese su correo");
                    txtCorreo.setFont(placeholderFont);
                }
            }
        });

        txtContrasenia.setForeground(new Color(119, 119, 119));
        txtContrasenia.setText("Contraseña");
        txtContrasenia.setFont(placeholderFont);
        txtContrasenia.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(txtContrasenia.getPassword()).equals("Contraseña")) {
                    txtContrasenia.setText("");
                    txtContrasenia.setForeground(Color.BLACK);
                    txtContrasenia.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(txtContrasenia.getPassword()).isEmpty()) {
                    txtContrasenia.setForeground(new Color(119, 119, 119));
                    txtContrasenia.setText("Contraseña");
                    txtContrasenia.setFont(placeholderFont);
                }
            }
        });
    }

    private void VerificarUsuario() {
        ListaDinamica<Persona> listaPersonas = personaControlDao.all();

        String usuarioIngresado = txtCorreo.getText();
        char[] c = txtContrasenia.getPassword();
        String contrasenaIngresada = new String(c);

        boolean credencialesCorrectas = false;

        for (Persona persona : listaPersonas.toArray()) {
            Cuenta cuenta = persona.getCuentaPersona();
            
            if (cuenta != null && cuenta.getCorreo().equals(usuarioIngresado) && cuenta.getContrasena().equals(contrasenaIngresada)) {
                credencialesCorrectas = true;

                Rol rolPersona = persona.getRolPersona();
                if (rolPersona != null) {
                    if (rolPersona.getNombreRol().equals("Personal Administrativo")) {
                        procesarPersonalAdministrativo();
                        dispose();
                    } 
                    else {
                        JOptionPane.showMessageDialog(null, "Inicio de sesión fallido. Solo se permite el acceso a personal administrativo.", "PERMISOS INSUFICIENTES", JOptionPane.WARNING_MESSAGE);
                    }
                }
                break;
            }
        }
        if (!credencialesCorrectas) {
            JOptionPane.showMessageDialog(null, "Inicio de sesión fallido. Verifique sus credenciales.", "CREDENCIALES INCORRECTAS", JOptionPane.WARNING_MESSAGE);
            txtCorreo.setText("");
            txtContrasenia.setText("");
        }
    }

    private static void procesarPersonalAdministrativo() {
        VistaPersonalAdministracion abrirAsistencia = new VistaPersonalAdministracion();
        abrirAsistencia.setVisible(true);
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
        btnRegresar = new javax.swing.JButton();
        bordesRedondos1 = new Vista.Utiles.BordesRedondos();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtContrasenia = new javax.swing.JPasswordField();
        btnIniciarSeccion = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SERVICIO DE AUTENTIFICACION DE PERSONAL");

        jPanel1.setBackground(new java.awt.Color(190, 193, 197));

        jPanel2.setBackground(new java.awt.Color(61, 90, 134));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Haettenschweiler", 0, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SERVICIO DE AUTENTIFICACION CENTRALIZADA");
        jLabel1.setToolTipText("");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/LojoUNL.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(148, 148, 148)
                .addComponent(jLabel2)
                .addGap(34, 34, 34)
                .addComponent(jLabel1)
                .addContainerGap(232, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel2)
        );

        btnRegresar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btnRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Regresar.png"))); // NOI18N
        btnRegresar.setText("REGRESAR");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        bordesRedondos1.setBackground(new java.awt.Color(234, 236, 237));
        bordesRedondos1.setRoundBottomLeft(25);
        bordesRedondos1.setRoundBottomRight(25);
        bordesRedondos1.setRoundTopLeft(25);
        bordesRedondos1.setRoundTopRight(25);

        jLabel3.setFont(new java.awt.Font("Segoe UI Emoji", 0, 28)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(228, 60, 92));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("INICIAR SESION");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(119, 119, 119));
        jLabel4.setText("Nombre de ususario");

        txtCorreo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(119, 119, 119));
        jLabel5.setText("Contraseña");

        txtContrasenia.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnIniciarSeccion.setBackground(new java.awt.Color(33, 15, 122));
        btnIniciarSeccion.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        btnIniciarSeccion.setForeground(new java.awt.Color(255, 255, 255));
        btnIniciarSeccion.setText("INICIAR SESION");
        btnIniciarSeccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarSeccionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bordesRedondos1Layout = new javax.swing.GroupLayout(bordesRedondos1);
        bordesRedondos1.setLayout(bordesRedondos1Layout);
        bordesRedondos1Layout.setHorizontalGroup(
            bordesRedondos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bordesRedondos1Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(bordesRedondos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnIniciarSeccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtContrasenia, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        bordesRedondos1Layout.setVerticalGroup(
            bordesRedondos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bordesRedondos1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnIniciarSeccion, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnRegresar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(420, 420, 420)
                        .addComponent(bordesRedondos1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112)
                .addComponent(bordesRedondos1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
                .addComponent(btnRegresar)
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

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed

        try {
            VistaPrincipalSistema newframe = new VistaPrincipalSistema();
            newframe.setVisible(true);
            this.dispose();
        }
        catch (Exception e) {
            
        }

    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnIniciarSeccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarSeccionActionPerformed

        char[] contrasena = txtContrasenia.getPassword();
        String contrasenaIngresada = new String(contrasena);

        if (txtCorreo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese su correo.", "CORREO VACIO", JOptionPane.INFORMATION_MESSAGE);
        }
        else if (contrasenaIngresada.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese su contraseña.", "CONTRESEÑA VACIA", JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            VerificarUsuario();
        }

    }//GEN-LAST:event_btnIniciarSeccionActionPerformed

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
            java.util.logging.Logger.getLogger(VistaInicioSesionPersonal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaInicioSesionPersonal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaInicioSesionPersonal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaInicioSesionPersonal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaInicioSesionPersonal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Vista.Utiles.BordesRedondos bordesRedondos1;
    private javax.swing.JButton btnIniciarSeccion;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField txtContrasenia;
    private javax.swing.JTextField txtCorreo;
    // End of variables declaration//GEN-END:variables
}
