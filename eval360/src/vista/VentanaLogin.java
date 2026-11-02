package vista;

import modelo.Estudiante;
import javax.swing.*;
import java.awt.*;

public class VentanaLogin extends JFrame {
    private JTextField txtNombre;
    private JButton btnComenzar;

    public VentanaLogin() {
        setTitle("EVALUA360 - Identificación");
        setSize(350, 180);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));

        JLabel lblTitulo = new JLabel("Ingrese su nombre para comenzar:", SwingConstants.CENTER);
        txtNombre = new JTextField();
        btnComenzar = new JButton("Comenzar Desafío");

        add(lblTitulo);
        add(txtNombre);
        add(btnComenzar);

        btnComenzar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            if (!nombre.isEmpty()) {
                Estudiante estudiante = new Estudiante(nombre);
                
                VentanaPrincipal ventanaQuiz = new VentanaPrincipal(estudiante);
                ventanaQuiz.setVisible(true);
                
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un nombre válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}