package vista; // Creamos un nuevo paquete 'vista'

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controlador.NotacionPrefijaPostfija; // Importamos tu Controlador

public class viewPrincipal extends JFrame implements ActionListener {

    // --- Componentes de la Interfaz ---
    private final JTextField txtExpresion;
    private final JButton btnConvertirPostfija;
    private final JButton btnConvertirPrefija;
    private final JButton btnEvaluarPostfija;
    private final JTextArea areaResultado;
    private final NotacionPrefijaPostfija controller;

    // --- Constructor de la Vista ---
    public viewPrincipal() {
        // 1. Configuración de la Ventana
        super("Calculadora de Expresiones Aritméticas (MVC)");
        this.controller = new NotacionPrefijaPostfija();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLayout(new BorderLayout(10, 10)); // Usamos BorderLayout para la estructura principal
        setLocationRelativeTo(null); // Centra la ventana
        
        // 2. Inicialización de Componentes
        txtExpresion = new JTextField(40);
        btnConvertirPostfija = new JButton("Convertir a Postfija");
        btnConvertirPrefija = new JButton("Convertir a Prefija");
        btnEvaluarPostfija = new JButton("Evaluar Postfija");
        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Monospaced", Font.PLAIN, 14));
        
        // 3. Paneles de Organización
        
        // Panel superior para entrada de texto
        JPanel panelInput = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelInput.setBorder(BorderFactory.createTitledBorder("Expresión Infija"));
        panelInput.add(new JLabel("Escribe la expresión:"));
        panelInput.add(txtExpresion);
        
        // Panel central para botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.add(btnConvertirPostfija);
        panelBotones.add(btnConvertirPrefija);
        panelBotones.add(btnEvaluarPostfija);
        
        // Panel de Resultados
        JPanel panelResultados = new JPanel(new BorderLayout());
        panelResultados.setBorder(BorderFactory.createTitledBorder("Resultados y Pasos"));
        panelResultados.add(new JScrollPane(areaResultado), BorderLayout.CENTER);
        
        // 4. Conectar Eventos (Listener)
        btnConvertirPostfija.addActionListener(this);
        btnConvertirPrefija.addActionListener(this);
        btnEvaluarPostfija.addActionListener(this);
        
        // 5. Agregar Paneles a la Ventana Principal
        add(panelInput, BorderLayout.NORTH);
        add(panelResultados, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
    // --- Manejo de Eventos (Implementación de ActionListener) ---
    @Override
    public void actionPerformed(ActionEvent e) {
        String expresionInfija = txtExpresion.getText().trim();
        String resultado = "";
        
        // Se determina qué botón fue presionado
        if (e.getSource() == btnConvertirPostfija) {
            resultado = controller.convertToPostfix(expresionInfija);
        } else if (e.getSource() == btnConvertirPrefija) {
            resultado = controller.convertToPrefix(expresionInfija);
        } else if (e.getSource() == btnEvaluarPostfija) {
            // El método evaluatePostfix ya incluye la conversión, evaluación y pasos
            resultado = controller.evaluatePostfix(expresionInfija);
        }
        
        // Muestra el resultado en el JTextArea
        if (!resultado.isEmpty()) {
            areaResultado.setText(resultado);
        } else {
             // Si el controlador mostró un error (JOptionPane), el resultado es vacío. 
             // Limpiamos el área para evitar confusiones.
             areaResultado.setText("");
        }
    }
    
    // --- Método Main para Ejecutar la Aplicación ---
    public static void main(String[] args) {
        // Se ejecuta la interfaz en el hilo de Swing (Thread-safe)
        SwingUtilities.invokeLater(() -> {
            new viewPrincipal();
        });
    }
}
