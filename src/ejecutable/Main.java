package ejecutable;

import javax.swing.SwingUtilities;

import vista.viewPrincipal;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
            // Instancia y hace visible la Vista principal de la aplicación
            new viewPrincipal();
        });
	}

}
