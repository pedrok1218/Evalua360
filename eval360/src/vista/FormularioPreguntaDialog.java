package vista;

import controlador.QuizController;
import javax.swing.*;
import java.awt.*;

public class FormularioPreguntaDialog extends JDialog {
    private QuizController controller;

    private JComboBox<String> cmbTipo;
    private JTextField txtEnunciado, txtCategoria;
    private JPanel panelDinamico;

    private JTextField[] txtOpciones;
    private JComboBox<Integer> cmbCorrectaOM;

    private JRadioButton rbVerdadero, rbFalso;

    public FormularioPreguntaDialog(Frame parent, QuizController controller) {
        super(parent, "Agregar Nueva Pregunta", true);
        this.controller = controller;

        setSize(450, 420);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        initUI();
    }

    private void initUI() {
        JPanel topPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        topPanel.add(new JLabel("Tipo de Pregunta:"));
        cmbTipo = new JComboBox<>(new String[]{"Opción Múltiple", "Verdadero / Falso"});
        cmbTipo.addActionListener(e -> cambiarFormulario());
        topPanel.add(cmbTipo);

        topPanel.add(new JLabel("Categoría:"));
        txtCategoria = new JTextField();
        topPanel.add(txtCategoria);

        topPanel.add(new JLabel("Enunciado:"));
        txtEnunciado = new JTextField();
        topPanel.add(txtEnunciado);

        add(topPanel, BorderLayout.NORTH);

        panelDinamico = new JPanel();
        add(panelDinamico, BorderLayout.CENTER);

        JPanel botPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> guardar());
        btnCancelar.addActionListener(e -> dispose());

        botPanel.add(btnGuardar);
        botPanel.add(btnCancelar);
        add(botPanel, BorderLayout.SOUTH);

        cambiarFormulario();
    }

    private void cambiarFormulario() {
        panelDinamico.removeAll();
        if (cmbTipo.getSelectedIndex() == 0) {
            panelDinamico.setLayout(new GridLayout(5, 2, 5, 5));
            panelDinamico.setBorder(BorderFactory.createTitledBorder("Opciones y Respuesta Correcta"));

            txtOpciones = new JTextField[4];
            for (int i = 0; i < 4; i++) {
                panelDinamico.add(new JLabel("Opción " + (i + 1) + ":"));
                txtOpciones[i] = new JTextField();
                panelDinamico.add(txtOpciones[i]);
            }

            panelDinamico.add(new JLabel("Opción Correcta:"));
            cmbCorrectaOM = new JComboBox<>(new Integer[]{1, 2, 3, 4});
            panelDinamico.add(cmbCorrectaOM);
        } else {
            panelDinamico.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
            panelDinamico.setBorder(BorderFactory.createTitledBorder("Respuesta Correcta"));

            rbVerdadero = new JRadioButton("Verdadero", true);
            rbFalso = new JRadioButton("Falso");
            ButtonGroup bg = new ButtonGroup();
            bg.add(rbVerdadero);
            bg.add(rbFalso);

            panelDinamico.add(rbVerdadero);
            panelDinamico.add(rbFalso);
        }
        panelDinamico.revalidate();
        panelDinamico.repaint();
    }

    private void guardar() {
        String cat = txtCategoria.getText().trim();
        String enun = txtEnunciado.getText().trim();

        if (cat.isEmpty() || enun.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe completar la categoría y el enunciado.");
            return;
        }

        if (cmbTipo.getSelectedIndex() == 0) {
            String[] ops = new String[4];
            for (int i = 0; i < 4; i++) {
                ops[i] = txtOpciones[i].getText().trim();
                if (ops[i].isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Complete todas las opciones.");
                    return;
                }
            }
            int correctaIndex = cmbCorrectaOM.getSelectedIndex();
            controller.agregarOpcionMultiple(enun, cat, ops, correctaIndex);
        } else {
            boolean val = rbVerdadero.isSelected();
            controller.agregarVerdaderoFalso(enun, cat, val);
        }

        JOptionPane.showMessageDialog(this, "Pregunta agregada con éxito.");
        dispose();
    }
}