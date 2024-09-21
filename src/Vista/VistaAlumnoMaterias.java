
package Vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author FA506ICB-HN114W
 */
public class VistaAlumnoMaterias extends javax.swing.JFrame {

    @SuppressWarnings("unused")
    private String nombreMateria;
    private JLabel nuevoLabel;

    public VistaAlumnoMaterias() {
        initComponents();
        addListenersToButtons();
        this.setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/Vista/RecursosGraficos/IconoPrograma.png")).getImage());

    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
        txtnombreMateria.setText(nombreMateria);
    }

    private void addListenersToButtons() {
        btnUnidad1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUnidadButtonClick(1);
            }
        });

        btnUnidad2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUnidadButtonClick(2);
            }
        });

        btnUnidad3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUnidadButtonClick(3);
            }
        });
    }

    private void handleUnidadButtonClick(int unidadNumber) {
        // Ocultar los componentes relacionados con la unidad actual
        btnSilabo1.setVisible(false);
        txtSilabo.setVisible(false);
        btnDocente.setVisible(false);
        txtDocente.setVisible(false);
        nuevoLabel.setVisible(false);

        // Cambiar el texto del componente txtSeccion
        txtSeccion.setText("Actividades");

        // Agregar/ajustar otros cambios según sea necesario
        // Mostrar/ajustar componentes específicos de la unidad
        if (unidadNumber == 1) {
            // Mostrar/ajustar componentes para la Unidad 1
        } else if (unidadNumber == 2) {
            // Mostrar/ajustar componentes para la Unidad 2
        } else if (unidadNumber == 3) {
            // Mostrar/ajustar componentes para la Unidad 3
        }
    }

    private void u1() {
        handleUnidadButtonClick(1);
    }

    private void u2() {
        handleUnidadButtonClick(2);
    }

    private void u3() {
        handleUnidadButtonClick(3);
    }
    
    private void Calificacion(){
        btnSilabo1.setVisible(false);
        txtSilabo.setVisible(false);
        btnDocente.setVisible(false);
        txtDocente.setVisible(false);
        
        txtSeccion.setText("CALIFICACIONES");
        
        nuevoLabel = new JLabel();
        nuevoLabel.setSize(200,50);
        nuevoLabel.setForeground(Color.WHITE);
        nuevoLabel.setFont(new Font("ROCKSTAR EXTRA BOLD", Font.PLAIN,28));
        jPanel4.add(nuevoLabel);
        
        String texto = "USUARIO";
        nuevoLabel.setText(texto);
        nuevoLabel.setVisible(true);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtnombreMateria = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        txtCalificaciones = new javax.swing.JLabel();
        btnCalificaciones = new javax.swing.JButton();
        btnUnidad1 = new javax.swing.JButton();
        btnUnidad2 = new javax.swing.JButton();
        btnUnidad3 = new javax.swing.JButton();
        btnInicio = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        txtSeccion = new javax.swing.JLabel();
        txtDocente = new javax.swing.JLabel();
        txtSilabo = new javax.swing.JLabel();
        btnDocente = new javax.swing.JButton();
        btnSilabo1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(190, 193, 197));
        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(16, 35, 105));

        btnSalir.setBackground(new java.awt.Color(16, 35, 105));
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/x.png"))); // NOI18N
        btnSalir.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Rockstar Extra Bold", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Nombre de la persona");

        txtnombreMateria.setFont(new java.awt.Font("Rockstar Extra Bold", 0, 24)); // NOI18N
        txtnombreMateria.setForeground(new java.awt.Color(255, 255, 255));
        txtnombreMateria.setText("nombremateria");

        btnRegresar.setBackground(new java.awt.Color(16, 35, 105));
        btnRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/cerrar-sesion.png"))); // NOI18N
        btnRegresar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(btnSalir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 483, Short.MAX_VALUE)
                .addComponent(txtnombreMateria)
                .addGap(309, 309, 309)
                .addComponent(jLabel1)
                .addGap(36, 36, 36)
                .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtnombreMateria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel1.add(jPanel2);
        jPanel2.setBounds(-5, 0, 1210, 45);

        jPanel3.setBackground(new java.awt.Color(232, 241, 242));

        txtCalificaciones.setFont(new java.awt.Font("Rockstar Extra Bold", 0, 12)); // NOI18N
        txtCalificaciones.setText("Calificaciones");

        btnCalificaciones.setBackground(new java.awt.Color(232, 241, 242));
        btnCalificaciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/los-grados.png"))); // NOI18N
        btnCalificaciones.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCalificaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalificacionesActionPerformed(evt);
            }
        });

        btnUnidad1.setBackground(new java.awt.Color(232, 241, 242));
        btnUnidad1.setFont(new java.awt.Font("Rockstar Extra Bold", 0, 12)); // NOI18N
        btnUnidad1.setText("Unidad 1");
        btnUnidad1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnidad1ActionPerformed(evt);
            }
        });

        btnUnidad2.setBackground(new java.awt.Color(232, 241, 242));
        btnUnidad2.setFont(new java.awt.Font("Rockstar Extra Bold", 0, 12)); // NOI18N
        btnUnidad2.setText("Unidad 2");
        btnUnidad2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnidad2ActionPerformed(evt);
            }
        });

        btnUnidad3.setBackground(new java.awt.Color(232, 241, 242));
        btnUnidad3.setFont(new java.awt.Font("Rockstar Extra Bold", 0, 12)); // NOI18N
        btnUnidad3.setText("unidad 3");
        btnUnidad3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnidad3ActionPerformed(evt);
            }
        });

        btnInicio.setBackground(new java.awt.Color(232, 241, 242));
        btnInicio.setFont(new java.awt.Font("Rockstar Extra Bold", 0, 12)); // NOI18N
        btnInicio.setForeground(new java.awt.Color(204, 0, 0));
        btnInicio.setText("INICIO");
        btnInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCalificaciones)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(btnCalificaciones))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnUnidad1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnUnidad3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnUnidad2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addGap(40, 40, 40))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(txtCalificaciones)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCalificaciones)
                .addGap(18, 18, 18)
                .addComponent(btnInicio)
                .addGap(18, 18, 18)
                .addComponent(btnUnidad1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUnidad2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUnidad3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(303, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3);
        jPanel3.setBounds(0, 40, 160, 660);

        jPanel4.setBackground(new java.awt.Color(16, 35, 105));

        txtSeccion.setFont(new java.awt.Font("Rockstar Extra Bold", 0, 24)); // NOI18N
        txtSeccion.setForeground(new java.awt.Color(255, 255, 255));
        txtSeccion.setText("Seccion de informacion");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(376, 376, 376)
                .addComponent(txtSeccion)
                .addContainerGap(390, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtSeccion)
                .addContainerGap())
        );

        jPanel1.add(jPanel4);
        jPanel4.setBounds(180, 50, 1020, 40);

        txtDocente.setFont(new java.awt.Font("Rockstar Extra Bold", 0, 12)); // NOI18N
        txtDocente.setText("COnoce al docente");
        jPanel1.add(txtDocente);
        txtDocente.setBounds(230, 190, 110, 16);

        txtSilabo.setFont(new java.awt.Font("Rockstar Extra Bold", 0, 12)); // NOI18N
        txtSilabo.setText("SILABO");
        jPanel1.add(txtSilabo);
        txtSilabo.setBounds(240, 130, 40, 16);

        btnDocente.setBackground(new java.awt.Color(190, 193, 197));
        btnDocente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/documentos.png"))); // NOI18N
        btnDocente.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(btnDocente);
        btnDocente.setBounds(180, 180, 30, 30);

        btnSilabo1.setBackground(new java.awt.Color(190, 193, 197));
        btnSilabo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/pdf.png"))); // NOI18N
        btnSilabo1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(btnSilabo1);
        btnSilabo1.setBounds(180, 120, 30, 30);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1200, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        
        dispose();
        
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed

        try {
            VistaAlumnoPrincipal vpa = new VistaAlumnoPrincipal();
            vpa.setVisible(true);
            this.setVisible(false);
        } 
        catch (Exception e) {
            
        }

    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnUnidad1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnidad1ActionPerformed
       
        u1();
        
    }//GEN-LAST:event_btnUnidad1ActionPerformed

    private void btnUnidad2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnidad2ActionPerformed
       
        u2();
        
    }//GEN-LAST:event_btnUnidad2ActionPerformed

    private void btnUnidad3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnidad3ActionPerformed
        
        u3();
        
    }//GEN-LAST:event_btnUnidad3ActionPerformed

    private void btnInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioActionPerformed

        
        
    }//GEN-LAST:event_btnInicioActionPerformed

    private void btnCalificacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalificacionesActionPerformed
        
        Calificacion();
        
    }//GEN-LAST:event_btnCalificacionesActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaAlumnoMaterias().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCalificaciones;
    private javax.swing.JButton btnDocente;
    private javax.swing.JButton btnInicio;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSilabo1;
    private javax.swing.JButton btnUnidad1;
    private javax.swing.JButton btnUnidad2;
    private javax.swing.JButton btnUnidad3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel txtCalificaciones;
    private javax.swing.JLabel txtDocente;
    private javax.swing.JLabel txtSeccion;
    private javax.swing.JLabel txtSilabo;
    private javax.swing.JLabel txtnombreMateria;
    // End of variables declaration//GEN-END:variables
}
