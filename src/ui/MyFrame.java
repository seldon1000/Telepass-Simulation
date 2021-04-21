package ui;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.GridLayout;

/**
 * Semplice frame personalizzato.
 */
public class MyFrame extends JFrame {
    private static final long serialVersionUID = -7032993829099839459L; // Superfluo, aggiunto per motivi tecnici

    /**
     * Costruttore del frame.
     * 
     * @param width  Spessore
     * @param height Altezza
     * @param rows   Righe
     * @param cols   Colonne
     * @param title  Titolo
     */
    public MyFrame(int width, int height, int rows, int cols, String title) {
        this.setLayout(new GridLayout(rows, cols)); // Utilizzo un GridLayout perché comodo e ordinato
        this.setSize(width, height); // Imposto le dimensione del frame

        JLabel label = new JLabel(title); // Titolo in forma di label
        label.setHorizontalAlignment(JLabel.CENTER); // Mostro il titolo centrato rispetto allo spazio disponibile

        this.add(label);
    }
}
