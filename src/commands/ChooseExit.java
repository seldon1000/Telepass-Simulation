package commands;

import entities.TelepassDevice;
import entities.TelepassSystem;
import ui.MyFrame;
import ui.MyPanel;

/**
 * Uscita dall'autostrada.
 */
public class ChooseExit implements CommandInterface {
    private MyFrame frame;
    private TelepassDevice device;
    private int tollbooth;

    /**
     * Costruttore del comando.
     * 
     * @param frame     Finestra di scelta
     * @param device    Dispositivo Telepass del veicolo che vuole uscire
     *                  dall'autostrada
     * @param tollbooth Casello autostradale scelto
     */
    public ChooseExit(MyFrame frame, TelepassDevice device, int tollbooth) {
        this.frame = frame;
        this.device = device;
        this.tollbooth = tollbooth;
    }

    /**
     * Questo comando, una volta eseguito, consentirà l'uscita dall'autostrada
     * attraverso il casello selezionato, ma solo rispettando una condizione. Se il
     * veicolo non è davvero in autostrada, il comando non può essere eseguito e
     * mostra un messaggio di errore. Altrimenti, il comando viene eseguito.
     */
    public void execute() {
        frame.dispose();

        Thread task; // Prima di poter entrare in autostrada, il veicolo deve attendere che il
        // sistema Telepass faccia determinate opezioni.
        // Si è preferito eseguire tali operazioni in un thread separato, per evitare
        // freeze per il thread principale del veicolo

        if (!device.isRiding()) { // Controllo se il veicolo è già in autostrada
            MyFrame warning = new MyFrame(350, 200, 2, 1, "Warning:");
            MyPanel message = new MyPanel.MyPanelBuilder(1, 1).addLabel("You haven't entered the highway yet!").build();

            warning.add(message);
            warning.setVisible(true); // Se il veicolo è già in autostrada, mostro un messaggio di errore
        } else {
            task = new Thread(new Runnable() {
                @Override
                public void run() {
                    TelepassSystem.getSystem().registerExit(device, tollbooth); // Mi avvicino al casello ed esco
                }
            });
            task.run(); // Faccio partire il thread
        }
    }
}
