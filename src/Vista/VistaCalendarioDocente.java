
package Vista;

import Controlador.TDA.ListaDinamica.Excepcion.ListaVacia;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class VistaCalendarioDocente extends JFrame {

    private JLabel EtiquetaMes;
    private JPanel PanelCalendario;
    private JComboBox<Integer> ComboAnio;
    private int MesActual, AnioActual;
    private Map<String, ArrayList<String>> MapaRecordatorios;
    SimpleDateFormat Formato = new SimpleDateFormat("dd/MMMM/yyyy");

    public VistaCalendarioDocente() {
        setTitle("CALENDARIO DOCENTE");
        setIconImage(new ImageIcon(getClass().getResource("/Vista/RecursosGraficos/IconoPrograma.png")).getImage());
        setSize(1225, 725);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MesActual = Calendar.getInstance().get(Calendar.MONTH);
        AnioActual = Calendar.getInstance().get(Calendar.YEAR);
        MapaRecordatorios = new HashMap<>();

        createGUI();
        ActualizarCalendario();

        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public void AgregarEvento(String nombreDia, String descripcion) {
        for (int mes = 0; mes < 12; mes++) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, AnioActual);
            cal.set(Calendar.MONTH, mes);
            cal.set(Calendar.DAY_OF_MONTH, 1);

            while (cal.get(Calendar.YEAR) == AnioActual && cal.get(Calendar.MONTH) == mes) {
                String nombreDiaActual = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.forLanguageTag("es-ES"));

                if (nombreDiaActual.equalsIgnoreCase(nombreDia)) {
                    String fecha = Formato.format(cal.getTime());

                    ArrayList<String> recordatorios = MapaRecordatorios.getOrDefault(fecha, new ArrayList<>());
                    recordatorios.add(descripcion);
                    MapaRecordatorios.put(fecha, recordatorios);
                }
                cal.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
        ActualizarCalendario();
    }

//    public void AgregarEvento(String fecha, String descripcion) {
//
//        ArrayList<String> recordatorios = MapaRecordatorios.getOrDefault(fecha, new ArrayList<>());
//        recordatorios.add(descripcion);
//        MapaRecordatorios.put(fecha, recordatorios);
//
//        ActualizarCalendario();
//    }

    @SuppressWarnings("unused")
    private void imprimirFechaActual(String fecha) {
        System.out.println("Fecha: " + fecha);
    }

    private void actualizarAnioComboBox(int nuevoAnio) {
        ComboAnio.setSelectedItem(nuevoAnio);
        AnioActual = nuevoAnio;
    }

//    private void imprimirFechaActual() {
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
//        String fechaActual = sdf.format(new Date());
//        System.out.println("Fecha actual: " + fechaActual);
//    }
    private void createGUI() {
        Container Contenedor = getContentPane();
        Contenedor.setLayout(new BorderLayout());

        JPanel PanelPrincipal = new JPanel(new BorderLayout());
        PanelPrincipal.setBackground(new Color(61, 90, 134));

        JButton BotonAnterior = new JButton("ANTERIOR");
        BotonAnterior.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Anterior.png")));
        
        JButton BotonSiguiente = new JButton("SIGUIENTE");
        BotonSiguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Siguiente.png")));
        BotonSiguiente.setHorizontalTextPosition(SwingConstants.LEFT);
    
        BotonAnterior.setBackground(new Color(83, 109, 136));
        BotonAnterior.setFont(new Font("SansSerif", Font.PLAIN, 16));
        BotonAnterior.setForeground(Color.WHITE);
        BotonSiguiente.setBackground(new Color(83, 109, 136));
        BotonSiguiente.setFont(new Font("SansSerif", Font.PLAIN, 16));
        BotonSiguiente.setForeground(Color.WHITE);

        EtiquetaMes = new JLabel();
        EtiquetaMes.setHorizontalAlignment(SwingConstants.CENTER);
        EtiquetaMes.setBackground(new Color(100, 100, 100));
        EtiquetaMes.setForeground(Color.WHITE);
        EtiquetaMes.setFont(new Font("SansSerif", Font.PLAIN, 16));

        PanelPrincipal.setBackground(new Color(61, 90, 134));

        JButton botonRegresar = new JButton("REGRESAR");
        botonRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Regresar.png")));
        botonRegresar.addActionListener(e -> {
            VistaDocentePrincipal otraInterfaz;
            try {
                otraInterfaz = new VistaDocentePrincipal();
                otraInterfaz.setLocationRelativeTo(this);
                otraInterfaz.setVisible(true);
            } 
            catch (ListaVacia ex) {
                
            }
            dispose();
        });

        PanelPrincipal.add(botonRegresar, BorderLayout.WEST);
//BotonImprimir fecha
//        JButton botonImprimirFecha = new JButton("Imprimir Fecha");
//        botonImprimirFecha.addActionListener(e -> imprimirFechaActual());
//        PanelPrincipal.add(botonImprimirFecha, BorderLayout.EAST);

        BotonAnterior.addActionListener(e -> {
            if (MesActual == 0) {
                MesActual = 11;
//                AnioActual--;
                actualizarAnioComboBox(AnioActual - 1);
            } 
            else {
                MesActual--;
            }
            ActualizarCalendario();
        });

        BotonSiguiente.addActionListener(e -> {
            if (MesActual == 11) {
                MesActual = 0;
//                AnioActual++;
                actualizarAnioComboBox(AnioActual + 1);
            } 
            else {
                MesActual++;
            }
            ActualizarCalendario();
        });

        PanelPrincipal.add(BotonAnterior);
        PanelPrincipal.add(EtiquetaMes);
        PanelPrincipal.add(BotonSiguiente);

        ComboAnio = new JComboBox<>(generateYearRange());
        ComboAnio.setSelectedItem(AnioActual);

        ComboAnio.addActionListener(e -> {
            AnioActual = (int) ComboAnio.getSelectedItem();
            ActualizarCalendario();
        });

        JPanel PanelCentral = new JPanel(new FlowLayout());
        PanelCentral.add(BotonAnterior);
        PanelCentral.add(EtiquetaMes);
        PanelCentral.add(BotonSiguiente);
        JLabel Ano = new JLabel("     Año:      ");
        Ano.setForeground(Color.WHITE);
        Ano.setFont(new Font("SansSerif", Font.PLAIN, 16));
        PanelCentral.add(Ano);
        PanelCentral.add(ComboAnio);

        PanelCentral.setBackground(new Color(61, 90, 134));
        PanelPrincipal.add(PanelCentral, BorderLayout.CENTER);
        Contenedor.add(PanelPrincipal, BorderLayout.NORTH);

        PanelCalendario = new JPanel(new GridLayout(0, 7));
        Contenedor.add(PanelCalendario, BorderLayout.CENTER);
    }

    @SuppressWarnings("unused")
    private void ActualizarCalendario() {
        EtiquetaMes.setText(new SimpleDateFormat("          MMMM          ").format(new GregorianCalendar(AnioActual, MesActual, 1).getTime()));
        PanelCalendario.removeAll();
        String[] DiaMes = {"Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};

        for (String Dia : DiaMes) {
            JLabel EtiquetaDias = new JLabel(Dia, JLabel.CENTER);
            EtiquetaDias.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            EtiquetaDias.setPreferredSize(new Dimension(50, 50));
            EtiquetaDias.setForeground(Color.WHITE);
            EtiquetaDias.setBackground(new Color(84, 82, 100));
            EtiquetaDias.setFont(new Font("SansSerif", Font.PLAIN, 16));
            EtiquetaDias.setOpaque(true);
            PanelCalendario.add(EtiquetaDias);
        }

        Calendar Calendario = new GregorianCalendar(AnioActual, MesActual, 1);
        int InicioSemana = Calendario.get(Calendar.DAY_OF_WEEK);
        int DiasMes = Calendario.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 1; i < InicioSemana; i++) {
            PanelCalendario.add(new JLabel(""));
        }

        for (int i = 1; i <= DiasMes; i++) {
            JButton BotonDias = new JButton(String.valueOf(i));
            int finalI = i;

            BotonDias.setPreferredSize(new Dimension(50, 50));
            BotonDias.setFont(new Font("Arial", Font.PLAIN, 20));
            BotonDias.setBackground(new Color(200, 200, 200));
            BotonDias.setForeground(Color.BLACK);

//            String FechaAux = String.format("%02d/%02d/%d", i, MesActual + 1, AnioActual);
//                    AnioActual + "-" + MesActual + "-" + i;
//ArrayList<String> Recordatorios = MapaRecordatorios.getOrDefault(FechaAux, new ArrayList<>());
            String FechaAux = Formato.format(new GregorianCalendar(AnioActual, MesActual, i).getTime());
            ArrayList<String> Recordatorios = MapaRecordatorios.getOrDefault(FechaAux, new ArrayList<>());

            if (!Recordatorios.isEmpty()) {
                BotonDias.setForeground(Color.RED);
                StringBuilder TextoAgregado = new StringBuilder("<html><center><font color='red'>" + i + "<br>");

                for (String reminder : Recordatorios) {
                    TextoAgregado.append("<font size='-2' color='black'>").append(reminder).append("<br></font>");
                }

                TextoAgregado.append("</font></center></html>");
                BotonDias.setText(TextoAgregado.toString());
            }

//            BotonDias.addActionListener(e -> {
//                imprimirFechaActual(FechaAux);
////                System.out.println("Fecha: " + FechaAux);
//                String Evento = JOptionPane.showInputDialog(null, "Agregar horario en " + finalI + " de " + new SimpleDateFormat("MMMM yyyy").format(new GregorianCalendar(AnioActual, MesActual, finalI).getTime()), "INGRESAR EVENTO", JOptionPane.PLAIN_MESSAGE);
//
//                if (Evento != null && !Evento.isEmpty()) {
//                    BotonDias.setForeground(Color.RED);
//                    Recordatorios.add(Evento);
//                    MapaRecordatorios.put(FechaAux, Recordatorios);
//                    ActualizarCalendario();
//                }
//            });
            PanelCalendario.add(BotonDias);
            //172, 174, 185
            //225, 233, 225
            PanelCalendario.setBackground(new Color(172, 174, 185));
        }

        revalidate();
        repaint();
    }

    private Integer[] generateYearRange() {
        int AnioInicio = 1950;
        int AnioFinal = 2100;
        Integer[] Anios = new Integer[AnioFinal - AnioInicio + 1];

        for (int i = 0; i < Anios.length; i++) {
            Anios[i] = AnioInicio + i;
        }
        return Anios;
    }

    public static void main(String[] args) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } 
        catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VistaDocenteCalendario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 
        catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaDocenteCalendario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 
        catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaDocenteCalendario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 
        catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaDocenteCalendario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        SwingUtilities.invokeLater(() -> new VistaCalendarioDocente());
    }
}
