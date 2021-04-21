package ui;

import java.awt.event.*;

import commands.CommandInterface;

/**
 * <b>Classe ButtonHandler</b>
 * 
 * Il ButtonHandler implementa l'interfaccia ActionListener e dunque il metodo
 * "actionPerformed()". Questo metodo viene chiamato quando il pulsante viene
 * premuto e non fa altro che chiamare il metodo "execute()" del comando
 * associato al pulsante. Fa parte del pattern "command".
 */
public class ButtonHandler implements ActionListener {
    private CommandInterface buttonCommand; // Comando generico

    /**
     * Costruttore del button handler
     * 
     * @param buttonCommand Comando da associare al pulsante
     */
    public ButtonHandler(CommandInterface buttonCommand) {
        this.buttonCommand = buttonCommand;
    }

    /**
     * Quando il pulsante viene premuto, il comando associato viene lanciato.
     */
    public void actionPerformed(ActionEvent e) {
        this.buttonCommand.execute();
    }
}
