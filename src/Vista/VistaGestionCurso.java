
package Vista;

import Controlador.Dao.Bridge;
import Controlador.Dao.Modelo.cursoDao;
import Controlador.Dao.Modelo.docenteDao;
import Controlador.Dao.Modelo.materiaDao;
import Controlador.Dao.Modelo.matriculaDao;
import Controlador.TDA.ListaDinamica.Excepcion.ListaVacia;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import Controlador.Utiles.UtilesControlador;
import Modelo.Cursa;
import Modelo.Docente;
import Modelo.Materia;
import Modelo.Matricula;
import Vista.Utiles.UtilVista;
import Vista.ModeloTabla.ModeloTablaCurso;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Victor
 */
public class VistaGestionCurso extends javax.swing.JFrame {
    cursoDao cursoControlDao = new cursoDao();
    ModeloTablaCurso mtc = new ModeloTablaCurso();
    ListaDinamica<Cursa> listaCursos = new ListaDinamica<>();

    /**
     * Creates new form VistaRegistroMateria
     * @throws Controlador.TDA.ListaDinamica.Excepcion.ListaVacia
     */
    public VistaGestionCurso() throws ListaVacia {
        initComponents();
        this.setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/Vista/RecursosGraficos/IconoPrograma.png")).getImage());
        UtilVista.cargarcomboMatricula(cbxMatricula);
        UtilVista.cargarcomboParalelo(cbxParalelo); 
        UtilVista.cargarcomboDocente(cbxDocente); 
        UtilVista.CargarComboMateria(cbxMateria);
        cbxDocente.setMaximumRowCount(cbxDocente.getItemCount());
        cbxMatricula.setMaximumRowCount(cbxMatricula.getItemCount());
        CargarTabla();
    }
    
    private void CargarTabla() {
        mtc.setCursoTabla(cursoControlDao.getListaCursa());
        tblCursos.setModel(mtc);
        tblCursos.updateUI();
        cbxMatricula.setSelectedIndex(-1);
        cbxParalelo.setSelectedIndex(-1);
        cbxDocente.setSelectedIndex(-1);
        cbxMateria.setSelectedIndex(-1);
        cbxTipoOrden.setSelectedIndex(-1);
    }
    
    private void Limpiar() throws ListaVacia {
        txtCodigoCursa.setText("");
        txtMatriculaBusqueda.setText("");
        txtDocenteBusqueda.setText("");
        cbxParalelo.setSelectedIndex(-1);
        cbxMatricula.setSelectedIndex(-1);
        cbxDocente.setSelectedIndex(-1);
        cbxMateria.setSelectedIndex(-1);
        cursoControlDao.setCursos(null);
        CargarTabla();
    }
    
    private void Seleccionar(){
        int fila = tblCursos.getSelectedRow();
        if(fila < 0){
            JOptionPane.showMessageDialog(null, "Escoga un registro");
        }
        else{
            try {
                cursoControlDao.setCursos(mtc.getCursoTabla().getInfo(fila));
                
                cbxParalelo.setSelectedIndex(cursoControlDao.getCursos().getParaleloCursa().getIdParalelo() -1);
                cbxMatricula.setSelectedIndex(cursoControlDao.getCursos().getMatriculaCursa().getIdMatricula() -1);
                txtCodigoCursa.setText(cursoControlDao.getCursos().getCodigoCursa());
                cbxDocente.setSelectedIndex(cursoControlDao.getCursos().getDocenteCursa().getIdDocente() -1);
                cbxMateria.setSelectedIndex(cursoControlDao.getCursos().getMateriaCursa().getIdMateria() -1);

            } 
            catch (Exception e) {
                
            }
        }
    }
    
    /*
    casi sirve
    if (cbxCodigoCurso.getSelectedIndex() == -1) {
        JOptionPane.showMessageDialog(null, "Falta seleccionar el ciclo", "Error", JOptionPane.ERROR_MESSAGE);
    } else if (cbxParalelo.getSelectedIndex() == -1) {
        JOptionPane.showMessageDialog(null, "Falta seleccionar materia", "Error", JOptionPane.ERROR_MESSAGE);
    } else if (cbxMatricula.getSelectedIndex() == -1) {
        JOptionPane.showMessageDialog(null, "Falta seleccionar materia", "Error", JOptionPane.ERROR_MESSAGE);
    } else {
        Paralelo paralelo = UtilVista.obtenerControlParalelo(cbxParalelo);
        Matricula matricula = UtilVista.obtenerMatriculaControl(cbxMatricula);

        // Obtener el nombre del código del curso
        String nombreCodigoCurso = cbxCodigoCurso.getSelectedItem().toString();

        // Crear un objeto CodigoCurso con el nombre obtenido
        CodigoCurso codigoCurso = new CodigoCurso();
        codigoCurso.setNombreCodigoCurso(nombreCodigoCurso);

        // Buscar si ya existe un curso con el mismo código y paralelo
        boolean cursoExistenteEncontrado = false;
        for (int i = 0; i < listaCursos.getLongitud(); i++) {
            Cursa curso = listaCursos.getInfo(i);
            if (curso.getCodigoCursoCursa().getNombreCodigoCurso().equals(nombreCodigoCurso) && curso.getParaleloCursa().equals(paralelo)) {
                // Si ya existe un curso con el mismo código y paralelo, agregar la matrícula a su lista de matrículas
                curso.getListaMatriculaCursa().Agregar(matricula);
                cursoExistenteEncontrado = true;
                cursoControlDao.setCursos(curso); // Actualizar el curso en el DAO
                break;
            }
        }

        if (!cursoExistenteEncontrado) {
            // Si no existe un curso con el mismo código y paralelo, crear un nuevo curso y agregar la matrícula a su lista de matrículas
            Integer idCiclo = listaCursos.getLongitud() + 1;
            Cursa nuevoCurso = new Cursa();
            nuevoCurso.setIdCurso(idCiclo);
            nuevoCurso.setCodigoCursoCursa(codigoCurso);
            nuevoCurso.setParaleloCursa(paralelo);
            ListaDinamica<Matricula> listaMatriculasAgregar = new ListaDinamica<>();
            listaMatriculasAgregar.Agregar(matricula);
            nuevoCurso.setListaMatriculaCursa(listaMatriculasAgregar);

            // Agregar el nuevo curso a la lista de cursos
            listaCursos.Agregar(nuevoCurso);
        }

        if (cursoControlDao.Persist()) {
            JOptionPane.showMessageDialog(null, "CICLO GUARDADO EXITOSAMENTE", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
            cursoControlDao.setCursos(null);
        } else {
            JOptionPane.showMessageDialog(null, "NO SE PUEDE REGISTRAR", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
        }
        Limpiar();
    }
    */
    
    @SuppressWarnings("unchecked")
    private String generarCodigo() throws ListaVacia {
        int ultimoId = 0;

        String presentacionURL = "Files" + File.separatorChar + "Cursa.json";

        try {
            ListaDinamica<Cursa> listaPresentacion = (ListaDinamica<Cursa>) Bridge.getConection().fromXML(new FileReader(presentacionURL));

            if (!listaPresentacion.EstaVacio()) {
                Cursa ultimaPresentacion = listaPresentacion.getInfo(listaPresentacion.getLongitud() - 1);
                ultimoId = ultimaPresentacion.getIdCurso();
            }
        } 
        catch (FileNotFoundException e) {
        }

        ultimoId++;

        return "C-" + String.format("%04d", ultimoId);
    }
    
    private void Guardar() throws ListaVacia {

        if (cbxParalelo.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Falta seleccionar el paralelo", "Error", JOptionPane.WARNING_MESSAGE);
        }
        else if (cbxMatricula.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Falta seleccionar la matricula", "Error", JOptionPane.WARNING_MESSAGE);
        } 
        else if (cbxDocente.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Falta seleccionar el docebte", "Error", JOptionPane.WARNING_MESSAGE);
        }
        else if (cbxMateria.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar la materia", "Error", JOptionPane.WARNING_MESSAGE);
        }
        else {
            Integer idCiclo = listaCursos.getLongitud() + 1;
            String cc = generarCodigo();

            cursoControlDao.getCursos().setIdCurso(idCiclo);
            cursoControlDao.getCursos().setCodigoCursa(cc);
            cursoControlDao.getCursos().setParaleloCursa(UtilVista.obtenerControlParalelo(cbxParalelo));
            cursoControlDao.getCursos().setMatriculaCursa(UtilVista.obtenerMatriculaControl(cbxMatricula));
            cursoControlDao.getCursos().setDocenteCursa(UtilVista.obtenerDocenteControl(cbxDocente));
            cursoControlDao.getCursos().setMateriaCursa(UtilVista.obtenerComboMateria(cbxMateria));

            if (cursoControlDao.Persist()) {
                JOptionPane.showMessageDialog(null, "CURSO GUARDADA EXISTOSAMENTE", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
                cursoControlDao.setCursos(null);
            }
            else {
                JOptionPane.showMessageDialog(null, "NO SE PUEDE REGISTRAR", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
            }
            Limpiar();
        }
    }

//        if (cbxCodigoCurso.getSelectedIndex() == -1) {
//            JOptionPane.showMessageDialog(null, "Falta seleccionar el ciclo", "Error", JOptionPane.ERROR_MESSAGE);
//        } else if (cbxParalelo.getSelectedIndex() == -1) {
//            JOptionPane.showMessageDialog(null, "Falta seleccionar materia", "Error", JOptionPane.ERROR_MESSAGE);
//        } else if (cbxMatricula.getSelectedIndex() == -1) {
//            JOptionPane.showMessageDialog(null, "Falta seleccionar materia", "Error", JOptionPane.ERROR_MESSAGE);
//        } else {
//            Integer idCiclo = listaCursos.getLongitud() + 1;
//
//            Cursa nuevoCurso = new Cursa();
//            nuevoCurso.setIdCurso(idCiclo);
//            nuevoCurso.setCodigoCursoCursa(UtilVista.obtenerCodigoCursoControl(cbxCodigoCurso));
//            nuevoCurso.setParaleloCursa(UtilVista.obtenerControlParalelo(cbxParalelo));
//            ListaDinamica<Matricula> listaMatriculasAgregar = new ListaDinamica<>();
//            listaMatriculasAgregar.Agregar(UtilVista.obtenerMatriculaControl(cbxMatricula));
//            nuevoCurso.setListaMatriculaCursa(listaMatriculasAgregar);
//            
//
//            cursoControlDao.setCursos(nuevoCurso);
//
//            if (cursoControlDao.Persist()) {
//                JOptionPane.showMessageDialog(null, "CICLO GUARDADO EXITOSAMENTE", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
//                cursoControlDao.setCursos(null);
//            } else {
//                JOptionPane.showMessageDialog(null, "NO SE PUEDE REGISTRAR", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
//            }
//            Limpiar();
//        }

//        if (cbxCodigoCurso.getSelectedIndex() == -1) {
//            JOptionPane.showMessageDialog(null, "Falta seleccionar el ciclo", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//        else if (cbxParalelo.getSelectedIndex() == -1) {
//            JOptionPane.showMessageDialog(null, "Falta seleccionar materia", "Error", JOptionPane.ERROR_MESSAGE);
//        } 
//        else if (cbxMatricula.getSelectedIndex() == -1) {
//            JOptionPane.showMessageDialog(null, "Falta seleccionar materia", "Error", JOptionPane.ERROR_MESSAGE);
//        } 
//        else {
//            Integer idCiclo = listaCursos.getLongitud()+1;
//            
//            cursoControlDao.getCursos().setIdCurso(idCiclo);
//            cursoControlDao.getCursos().setCodigoCursoCursa(UtilVista.obtenerCodigoCursoControl(cbxCodigoCurso));
//            cursoControlDao.getCursos().setParaleloCursa(UtilVista.obtenerControlParalelo(cbxParalelo));
////            ListaDinamica<Matricula> listaMc = new ListaDinamica();
////            listaMc.Agregar(UtilVista.obtenerMatriculaControl(cbxMatricula));
//            cursoControlDao.getCursos().setMatriculaCursa(UtilVista.obtenerMatriculaControl(cbxMatricula));
//            
//            if (cursoControlDao.Persist()) {
//                JOptionPane.showMessageDialog(null, "CICLO GUARDADA EXISTOSAMENTE", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
//                cursoControlDao.setCursos(null);
//            } 
//            else {
//                JOptionPane.showMessageDialog(null, "NO SE PUEDE REGISTRAR", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
//            }
//            Limpiar();
//        }
//    }
    
        
//        if (cbxCodigoCurso.getSelectedIndex() == -1) {
//            JOptionPane.showMessageDialog(null, "Falta seleccionar el ciclo", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//        else if (cbxParalelo.getSelectedIndex() == -1) {
//            JOptionPane.showMessageDialog(null, "Falta seleccionar materia", "Error", JOptionPane.ERROR_MESSAGE);
//        } 
//        else if (cbxMatricula.getSelectedIndex() == -1) {
//            JOptionPane.showMessageDialog(null, "Falta seleccionar materia", "Error", JOptionPane.ERROR_MESSAGE);
//        } 
//        else {
//            Integer idCiclo = listaCursos.getLongitud()+1;
//            
//            cursoControlDao.getCursos().setIdCurso(idCiclo);
//            cursoControlDao.getCursos().setCodigoCursoCursa(UtilVista.obtenerCodigoCursoControl(cbxCodigoCurso));
//            cursoControlDao.getCursos().setParaleloCursa(UtilVista.obtenerControlParalelo(cbxParalelo));
//            ListaDinamica<Matricula> listaMc = new ListaDinamica();
//            listaMc.Agregar(UtilVista.obtenerMatriculaControl(cbxMatricula));
//            cursoControlDao.getCursos().setListaMatriculaCursa(listaMc);
//            
//            
//            if (cursoControlDao.Persist()) {
//                JOptionPane.showMessageDialog(null, "CICLO GUARDADA EXISTOSAMENTE", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
//                cursoControlDao.setCursos(null);
//            } 
//            else {
//                JOptionPane.showMessageDialog(null, "NO SE PUEDE REGISTRAR", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
//            }
//            Limpiar();
//        }
//    }
    
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
        btnAsignarMateria = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbxMatricula = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCursos = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        cbxTipoOrden = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        cbxParalelo = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        cbxOrden = new javax.swing.JComboBox<>();
        btnOrdenar = new javax.swing.JButton();
        cbxTipoBusqueda = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtMatriculaBusqueda = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        txtDocenteBusqueda = new javax.swing.JTextField();
        cbxDocente = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtCodigoCursa = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtMateriaBusqueda = new javax.swing.JTextField();
        cbxMateria = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GESTION DE CURSOS");

        jPanel1.setBackground(new java.awt.Color(191, 192, 197));

        jPanel2.setBackground(new java.awt.Color(61, 90, 134));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/LojoUNL.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Haettenschweiler", 0, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("SERVICIO DE ADMINISTRACION DE CURSO");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82)
                .addComponent(jLabel2)
                .addContainerGap(261, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnAsignarMateria.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btnAsignarMateria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Guardar.png"))); // NOI18N
        btnAsignarMateria.setText("GUARDAR");
        btnAsignarMateria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsignarMateriaActionPerformed(evt);
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
        btnEliminar.setText("ELIMNAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Candara Light", 1, 32)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Informacion");

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Paralelo");

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Matricula");
        jLabel7.setToolTipText("");

        jLabel8.setFont(new java.awt.Font("Candara Light", 1, 32)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Lista de cursos");

        tblCursos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblCursos.setSelectionBackground(new java.awt.Color(200, 222, 180));
        tblCursos.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tblCursos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCursosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCursos);

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Buscar por");

        cbxTipoOrden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Codigo", "Paralelo", "DNI estudiante", "DNI dodente" }));
        cbxTipoOrden.setSelectedIndex(-1);

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Busqueda");

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

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Agregar.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Ordenar");

        cbxOrden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Asendente", "Desendente" }));
        cbxOrden.setSelectedIndex(-1);

        btnOrdenar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Ordenar.png"))); // NOI18N
        btnOrdenar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdenarActionPerformed(evt);
            }
        });

        cbxTipoBusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Codigo", "Paralelo", "DNI estudiante", "DNI dodente" }));
        cbxTipoBusqueda.setSelectedIndex(-1);

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Codigo");

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Matricula");

        jButton6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Buscar.png"))); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Buscar.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Docente");

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Docente");

        txtCodigoCursa.setEditable(false);

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Materia");

        jLabel15.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Materia");

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/RecursosGraficos/Botones/Buscar.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAsignarMateria))
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodigoCursa))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxMatricula, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(63, 63, 63)
                                .addComponent(cbxParalelo, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMatriculaBusqueda)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtDocenteBusqueda)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5))
                            .addComponent(cbxDocente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtMateriaBusqueda)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4))
                            .addComponent(cbxMateria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(5, 5, 5)
                        .addComponent(cbxTipoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxTipoOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOrdenar)))
                .addContainerGap())
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(jLabel8))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel11)
                                .addComponent(cbxTipoOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(btnOrdenar, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(cbxOrden, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxTipoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(txtCodigoCursa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(cbxParalelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel13)
                                        .addComponent(txtMatriculaBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jButton6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(cbxMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(txtDocenteBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jButton5))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(cbxDocente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(txtMateriaBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(cbxMateria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAsignarMateria)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar)
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

    private void btnAsignarMateriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsignarMateriaActionPerformed
        
        try {
            if (cbxParalelo.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar el paralelo", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (cbxMatricula.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar la matricula", "Error", JOptionPane.WARNING_MESSAGE);
            }
            else if (cbxDocente.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar el docebte", "Error", JOptionPane.WARNING_MESSAGE);
            }
            else if (cbxMateria.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar la materia", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else {
                Guardar();
            }
        } 
        catch (Exception e) {

        }
        
    }//GEN-LAST:event_btnAsignarMateriaActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        
        int fila = tblCursos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Escoga un registro");
        } 
        else {
            cursoControlDao.Eliminar(fila);
            CargarTabla();
        }
        
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        
        int fila = tblCursos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Escoga un registro");
        } 
        else {
            if (cbxParalelo.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar el paralelo", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (cbxMatricula.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar la matricula", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (cbxDocente.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar el docebte", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else if (cbxMateria.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta seleccionar la materia", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else {
                Integer IdCurso = cursoControlDao.getCursos().getIdCurso();
                String cc = txtCodigoCursa.getText();

                Cursa cursoModificado = new Cursa();
                cursoModificado.setIdCurso(IdCurso);
                cursoModificado.setParaleloCursa(UtilVista.obtenerControlParalelo(cbxParalelo));
                cursoModificado.setMatriculaCursa(UtilVista.obtenerMatriculaControl(cbxMatricula));
                cursoModificado.setCodigoCursa(cc);
                cursoModificado.setDocenteCursa(UtilVista.obtenerDocenteControl(cbxDocente));
                cursoModificado.setMateriaCursa(UtilVista.obtenerComboMateria(cbxMateria));

//                cursoModificado.setMateriaCurso(UtilVista.obtenerComboMateria(cbxMatricula));
                
                cursoControlDao.Merge(cursoModificado, IdCurso - 1);

                CargarTabla();

                try {
                    Limpiar();
                } 
                catch (ListaVacia ex) {

                }
            }
        }
        
    }//GEN-LAST:event_btnModificarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        try {
            if (cbxTipoBusqueda.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Porfavor seleccione donde quiere buscar", "Error", JOptionPane.WARNING_MESSAGE);
            } 
            else {
                ListaDinamica<Cursa> lista = cursoControlDao.all();

                String Campo = txtBuscar.getText();
                String TipoCampo = cbxTipoBusqueda.getSelectedItem().toString();

                switch (TipoCampo) {
                    case "Codigo":
                        TipoCampo = "CodigoCursa";
                        break;
                    case "Paralelo":
                        TipoCampo = "ParaleloCursa.Nombre";
                        break;
                    case "Matricula":
                        TipoCampo = "matriculaCursa.CodigoMatricula";
                        break;
                    case "DNI estudiante":
                        TipoCampo = "matriculaCursa.alumnoMatricula.DatosAlumno.NumeroCedula";
                        break;
                    case "DNI dodente":
                        TipoCampo = "docenteCursa.DatosDocente.NumeroCedula";
                        break;

                    default:
                        throw new AssertionError();
                }

                ListaDinamica<Cursa> ResultadoBusqueda = UtilesControlador.BusquedaLineal(lista, Campo, TipoCampo);

                mtc.setCursoTabla(ResultadoBusqueda);
                mtc.fireTableDataChanged();
            }
        } 
        catch (Exception e) {

        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblCursosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCursosMouseClicked
        
        Seleccionar();
        
    }//GEN-LAST:event_tblCursosMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        VistaPersonalAdministracion abrirLogin = new VistaPersonalAdministracion();
        abrirLogin.setVisible(true);
        this.setVisible(false);
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
        VistaGestionParalelo vp = new VistaGestionParalelo();
        vp.setVisible(true);
        this.setVisible(false);
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnOrdenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdenarActionPerformed

        try {
            if (cbxTipoOrden.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "No ha seleccionado el campo", "FALTA SELCCIONAR", JOptionPane.WARNING_MESSAGE);
            }
            else if (cbxOrden.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "No ha seleccionado el orden", "FALTA SELCCIONAR", JOptionPane.WARNING_MESSAGE);
            } 
            else {
                ListaDinamica<Cursa> lista = cursoControlDao.all();
                String TipoCampo = cbxTipoOrden.getSelectedItem().toString();

                switch (TipoCampo) {
                    case "Codigo":
                        TipoCampo = "CodigoCursa";
                        break;
                    case "Paralelo":
                        TipoCampo = "ParaleloCursa.Nombre";
                        break;
                    case "Matricula":
                        TipoCampo = "matriculaCursa.CodigoMatricula";
                        break;
                    case "DNI estudiante":
                        TipoCampo = "matriculaCursa.alumnoMatricula.DatosAlumno.NumeroCedula";
                        break;
                    case "DNI dodente":
                        TipoCampo = "docenteCursa.DatosDocente.NumeroCedula";
                        break;

                    default:
                        throw new AssertionError();
                }

                Integer orden = OrdenSeleccionado();

                ListaDinamica<Cursa> resultadoOrdenado = UtilesControlador.QuickSort(lista, orden, TipoCampo);

                mtc.setCursoTabla(resultadoOrdenado);
                mtc.fireTableDataChanged();
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }//GEN-LAST:event_btnOrdenarActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

        try {
            if (txtMatriculaBusqueda.getText().isEmpty()) {
                UtilVista.cargarcomboMatricula(cbxMatricula);
                JOptionPane.showMessageDialog(null, "Ingrese la matricula a buscar", "FALTA LLENAR", JOptionPane.WARNING_MESSAGE);
            } 
            else {
                matriculaDao PD = new matriculaDao();
                ListaDinamica<Matricula> lista = PD.all();

                String Campo = txtMatriculaBusqueda.getText();

                ListaDinamica<Matricula> ResultadoBusqueda = new ListaDinamica<>();

                ListaDinamica<Matricula> ResultadoCodigo = UtilesControlador.BusquedaLineal(lista, Campo, "CodigoMatricula");
                ResultadoBusqueda.concatenar(ResultadoCodigo);

                ListaDinamica<Matricula> ResultadoCedula = UtilesControlador.BusquedaLineal(lista, Campo, "alumnoMatricula.DatosAlumno.NumeroCedula");
                ResultadoBusqueda.concatenar(ResultadoCedula);
                
                ListaDinamica<Matricula> ResultadoN = UtilesControlador.BusquedaLineal(lista, Campo, "alumnoMatricula.DatosAlumno.Nombre");
                ResultadoBusqueda.concatenar(ResultadoN);
                
                ListaDinamica<Matricula> ResultadoA = UtilesControlador.BusquedaLineal(lista, Campo, "alumnoMatricula.DatosAlumno.Apellido");
                ResultadoBusqueda.concatenar(ResultadoA);

                cbxMatricula.removeAllItems();

                for (Matricula matricula : ResultadoBusqueda.toArray()) {
                    cbxMatricula.addItem(matricula);
                }
            }

        } 
        catch (Exception e) {

        }

    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        
        try {
            if (txtDocenteBusqueda.getText().isEmpty()) {
                UtilVista.cargarcomboDocente(cbxDocente); 
                JOptionPane.showMessageDialog(null, "Ingrese el docente a buscar", "FALTA LLENAR", JOptionPane.WARNING_MESSAGE);
            } 
            else {
                docenteDao PD = new docenteDao();
                ListaDinamica<Docente> lista = PD.all();

                String Campo = txtDocenteBusqueda.getText();

                ListaDinamica<Docente> ResultadoBusqueda = new ListaDinamica<>();

                ListaDinamica<Docente> ResultadoCe = UtilesControlador.BusquedaLineal(lista, Campo, "DatosDocente.NumeroCedula");
                ResultadoBusqueda.concatenar(ResultadoCe);

                ListaDinamica<Docente> ResultadoN = UtilesControlador.BusquedaLineal(lista, Campo, "DatosDocente.Nombre");
                ResultadoBusqueda.concatenar(ResultadoN);
                
                ListaDinamica<Docente> ResultadoA = UtilesControlador.BusquedaLineal(lista, Campo, "DatosDocente.Apellido");
                ResultadoBusqueda.concatenar(ResultadoA);

                cbxDocente.removeAllItems();

                for (Docente pb : ResultadoBusqueda.toArray()) {
                    cbxDocente.addItem(pb);
                }
            }

        } 
        catch (Exception e) {

        }
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
        try {
            if (txtMateriaBusqueda.getText().isEmpty()) {
                UtilVista.CargarComboMateria(cbxMateria);
                JOptionPane.showMessageDialog(null, "Ingrese el docente a buscar", "FALTA LLENAR", JOptionPane.WARNING_MESSAGE);
            } 
            else {
            
                materiaDao PD = new materiaDao();
                ListaDinamica<Materia> lista = PD.all();

                String Campo = txtMateriaBusqueda.getText();

                ListaDinamica<Materia> ResultadoBusqueda = UtilesControlador.BusquedaLineal(lista, Campo, "NombreMateria");

                cbxMateria.removeAllItems();

                for (Materia pb : ResultadoBusqueda.toArray()) {
                    cbxMateria.addItem(pb);
                }
            }
        } 
        catch (Exception e) {

        }
        
    }//GEN-LAST:event_jButton4ActionPerformed

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
        } 
        catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VistaGestionCurso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaGestionCurso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaGestionCurso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaGestionCurso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new VistaGestionCurso().setVisible(true);
            } 
            catch (ListaVacia ex) {
                
            }
        }
        );
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAsignarMateria;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnOrdenar;
    private javax.swing.JComboBox<Object> cbxDocente;
    private javax.swing.JComboBox<Object> cbxMateria;
    private javax.swing.JComboBox<Object> cbxMatricula;
    private javax.swing.JComboBox<String> cbxOrden;
    private javax.swing.JComboBox<String> cbxParalelo;
    private javax.swing.JComboBox<String> cbxTipoBusqueda;
    private javax.swing.JComboBox<String> cbxTipoOrden;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JTable tblCursos;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCodigoCursa;
    private javax.swing.JTextField txtDocenteBusqueda;
    private javax.swing.JTextField txtMateriaBusqueda;
    private javax.swing.JTextField txtMatriculaBusqueda;
    // End of variables declaration//GEN-END:variables
}
