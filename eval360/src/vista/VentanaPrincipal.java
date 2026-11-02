package vista;

import controlador.QuizController;
import modelo.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaPrincipal extends JFrame {
    private QuizController controller;
    private int indiceActual = 0;
    private int puntaje = 0;
    private Estudiante estudianteActual;

    private JPanel panelPrincipal;

    private JLabel lblCategoria, lblPregunta, lblPuntaje;
    private JPanel panelOpcionesJuego;
    private ButtonGroup grupoJuego;
    private JRadioButton[] rbOpcionesJuego;
    private JRadioButton rbVF_V, rbVF_F;

    public VentanaPrincipal() {
        controller = new QuizController();

        setTitle("EVALUA360 - Sistema de Cuestionarios");
        setSize(550, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mostrarMenuPrincipal();
    }

    public VentanaPrincipal(Estudiante estudiante) {
        this();
        this.estudianteActual = estudiante;
        iniciarModoJuego();
    }

    private void mostrarMenuPrincipal() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        panelPrincipal = new JPanel(new GridLayout(3, 1, 15, 15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        JLabel lblTitulo = new JLabel("EVALUA360: DESAFÍO & CONOCIMIENTO", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));

        JButton btnJugar = new JButton("Modo Estudiante (Responder Quiz)");
        JButton btnAdministrar = new JButton("Modo Administrador (Cargar Preguntas)");

        // Eventos limpios y corregidos
        btnJugar.addActionListener(e -> {
            VentanaLogin login = new VentanaLogin();
            login.setVisible(true);
            this.dispose();
        });

        btnAdministrar.addActionListener(e -> mostrarModoAdmin());

        panelPrincipal.add(lblTitulo);
        panelPrincipal.add(btnJugar);
        panelPrincipal.add(btnAdministrar);

        add(panelPrincipal, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void iniciarModoJuego() {
        List<Pregunta> preguntas = controller.obtenerPreguntas();
        if (preguntas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay preguntas cargadas en el sistema.");
            mostrarMenuPrincipal();
            return;
        }

        indiceActual = 0;
        puntaje = 0;

        getContentPane().removeAll();
        setLayout(new BorderLayout(10, 10));

        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        lblCategoria = new JLabel("Categoría: ");
        String nombreEstudiante = (estudianteActual != null) ? estudianteActual.getNombre() : "Invitado";
        lblPuntaje = new JLabel("Estudiante: " + nombreEstudiante + " | Puntaje: 0");

        top.add(lblCategoria, BorderLayout.WEST);
        top.add(lblPuntaje, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(10, 10));
        center.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        lblPregunta = new JLabel("Pregunta aquí");
        lblPregunta.setFont(new Font("Arial", Font.BOLD, 14));
        center.add(lblPregunta, BorderLayout.NORTH);

        panelOpcionesJuego = new JPanel();
        center.add(panelOpcionesJuego, BorderLayout.CENTER);

        JPanel bot = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnResponder = new JButton("Siguiente / Responder");
        JButton btnSalir = new JButton("Menú Principal");

        btnResponder.addActionListener(e -> procesarRespuesta());
        btnSalir.addActionListener(e -> mostrarMenuPrincipal());

        bot.add(btnResponder);
        bot.add(btnSalir);
        add(bot, BorderLayout.SOUTH);

        add(center, BorderLayout.CENTER);

        cargarPreguntaEnJuego();
        revalidate();
        repaint();
    }

    private void cargarPreguntaEnJuego() {
        List<Pregunta> lista = controller.obtenerPreguntas();
        if (indiceActual >= lista.size()) {
            JOptionPane.showMessageDialog(this, "¡Has completado todas las preguntas!\nPuntaje Final: " + puntaje);
            
            // Guardar el resultado en el archivo de texto
            if (estudianteActual != null) {
                persistencia.PersistenciaArchivo.guardarResultado(estudianteActual.getNombre(), puntaje);
            }

            mostrarMenuPrincipal();
            return;
        }

        Pregunta p = lista.get(indiceActual);
        lblCategoria.setText("Categoría: " + p.getCategoria());
        lblPregunta.setText((indiceActual + 1) + ". " + p.getEnunciado());

        panelOpcionesJuego.removeAll();
        grupoJuego = new ButtonGroup();

        if (p instanceof PreguntaOpcionMultiple) {
            PreguntaOpcionMultiple pom = (PreguntaOpcionMultiple) p;
            panelOpcionesJuego.setLayout(new GridLayout(4, 1, 5, 5));
            rbOpcionesJuego = new JRadioButton[4];
            String[] ops = pom.getOpciones();

            for (int i = 0; i < 4; i++) {
                rbOpcionesJuego[i] = new JRadioButton(ops[i]);
                grupoJuego.add(rbOpcionesJuego[i]);
                panelOpcionesJuego.add(rbOpcionesJuego[i]);
            }
        } else if (p instanceof PreguntaVerdaderoFalso) {
            panelOpcionesJuego.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
            rbVF_V = new JRadioButton("Verdadero");
            rbVF_F = new JRadioButton("Falso");

            grupoJuego.add(rbVF_V);
            grupoJuego.add(rbVF_F);

            panelOpcionesJuego.add(rbVF_V);
            panelOpcionesJuego.add(rbVF_F);
        }

        panelOpcionesJuego.revalidate();
        panelOpcionesJuego.repaint();
    }

    private void procesarRespuesta() {
        Pregunta p = controller.obtenerPreguntas().get(indiceActual);
        boolean acierto = false;

        if (p instanceof PreguntaOpcionMultiple) {
            int sel = -1;
            for (int i = 0; i < 4; i++) {
                if (rbOpcionesJuego[i].isSelected()) {
                    sel = i;
                    break;
                }
            }

            if (sel == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona una opción.");
                return;
            }
            acierto = controller.evaluar(p, sel);

        } else if (p instanceof PreguntaVerdaderoFalso) {
            if (!rbVF_V.isSelected() && !rbVF_F.isSelected()) {
                JOptionPane.showMessageDialog(this, "Selecciona Verdadero o Falso.");
                return;
            }
            boolean res = rbVF_V.isSelected();
            acierto = controller.evaluar(p, res);
        }

        if (acierto) {
            puntaje += 10;
            JOptionPane.showMessageDialog(this, "¡Correcto! (+10 pts)");
        } else {
            JOptionPane.showMessageDialog(this, "Incorrecto.");
        }

        String nombreEstudiante = (estudianteActual != null) ? estudianteActual.getNombre() : "Invitado";
        lblPuntaje.setText("Estudiante: " + nombreEstudiante + " | Puntaje: " + puntaje);
        indiceActual++;
        cargarPreguntaEnJuego();
    }

    private void mostrarModoAdmin() {
        getContentPane().removeAll();
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Panel de Administración de Preguntas", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 15));
        add(title, BorderLayout.NORTH);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        actualizarListaAdmin(listModel);

        JList<String> list = new JList<>(listModel);
        add(new JScrollPane(list), BorderLayout.CENTER);

        JPanel bot = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnAgregar = new JButton("Agregar Pregunta");
        JButton btnEliminar = new JButton("Eliminar Seleccionada");
        JButton btnVolver = new JButton("Volver al Menú");

        btnAgregar.addActionListener(e -> {
            FormularioPreguntaDialog dialog = new FormularioPreguntaDialog(this, controller);
            dialog.setVisible(true);
            actualizarListaAdmin(listModel);
        });

        btnEliminar.addActionListener(e -> {
            int sel = list.getSelectedIndex();
            if (sel != -1) {
                controller.eliminarPregunta(sel);
                actualizarListaAdmin(listModel);
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona una pregunta para eliminar.");
            }
        });

        btnVolver.addActionListener(e -> mostrarMenuPrincipal());

        bot.add(btnAgregar);
        bot.add(btnEliminar);
        bot.add(btnVolver);

        add(bot, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    private void actualizarListaAdmin(DefaultListModel<String> model) {
        model.clear();
        List<Pregunta> lista = controller.obtenerPreguntas();
        for (Pregunta p : lista) {
            model.addElement("[" + p.getTipo() + "] " + "(" + p.getCategoria() + ") " + p.getEnunciado());
        }
    }
}