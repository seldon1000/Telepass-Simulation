package ui;

import javax.swing.JButton;

import commands.CommandInterface;

/**
 * <b>Classe PanelButton</b>
 * 
 * Questa classe permette di generare tutti i pulsanti dell'interfaccia, facendo
 * in modo che ognuno di essi esegua delle operazioni complesse indipendenti. Fa
 * parte del pattern "command".
 */
public class PanelButton extends JButton {
    private static final long serialVersionUID = -2147410048192872989L; // Superfluo, aggiunto per motivi tecnici

    /**
     * Costruttore del pulsante.
     * 
     * @param buttonName    Nome del pulsante
     * @param buttonCommand Comando associato al pulsante
     */
    public PanelButton(String buttonName, CommandInterface buttonCommand) {
        this.setText(buttonName);
        this.addActionListener(new ButtonHandler(buttonCommand)); // Faccio in modo che, una volta premuto, il pulsante
                                                                  // esegua il comando associato
    }
}
