package commands;

import entities.TelepassDevice;
import entities.TelepassSystem;
import ui.MyFrame;
import ui.MyPanel;

/**
 * Entrata in autostrada.
 */
public class ChooseEntrance implements CommandInterface {
    private MyFrame frame;
    private TelepassDevice device;
    private int tollbooth;

    /**
     * Costruttore del comando.
     * 
     * @param frame     Finestra di scelta
     * @param device    Dispositivo Telepass del veicolo che vuole entrare in
     *                  autostrada
     * @param tollbooth Casello autostrale scelto
     */
    public ChooseEntrance(MyFrame frame, TelepassDevice device, int tollbooth) {
        this.frame = frame;
        this.device = device;
        this.tollbooth = tollbooth;
    }

    /**
     * Questo comando, una volta eseguito, consentirà l'accesso all'autostrada
     * attraverso il casello selezionato, ma solo rispettando alcune condizioni. Se
     * il veicolo è già in autostrada, il comando non può essere eseguito e mostra
     * un messaggio di errore. Se il dispositivo del veicolo che vuole entrare in
     * autostrada non è ancora stato approvato dal sistema Telepass, il comando non
     * può essere eseguito e mostra un messaggio di errore. Se non ci si trova in
     * nessuno dei due casi precedenti, il comando viene eseguito.
     */
    public void execute() {
        frame.dispose();

        Thread task; // Prima di poter entrare in autostrada, il veicolo deve attendere che il
                     // sistema Telepass faccia determinate opezioni.
                     // Si è preferito eseguire tali operazioni in un thread separato, per evitare
                     // freeze per il thread principale del veicolo

        if (device.isRiding()) { // Controllo se il veicolo è già in autostrada
            MyFrame warning = new MyFrame(350, 200, 2, 1, "Warning:");
            MyPanel message = new MyPanel.MyPanelBuilder(1, 1).addLabel("You already are on the highway!").build();

            warning.add(message);
            warning.setVisible(true); // Se il veicolo è già in autostrada, mostro un messaggio di errore
        } else {
            task = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (!TelepassSystem.getSystem().registerEntrance(device, tollbooth)) { // Mi avvicino al casello e tento
                                                                                      // di entrare
                        MyFrame warning = new MyFrame(350, 200, 2, 1, "Warning:");
                        MyPanel message = new MyPanel.MyPanelBuilder(1, 1)
                                .addLabel("Your device has not been approved!").build();

                        warning.add(message);
                        warning.setVisible(true); // Se il mio dispositivo non è stato ancora approvato, mostro un
                                                  // messaggio di errore
                    } else {
                        MyFrame frame = new MyFrame(350, 200, 1, 1, "Done!");

                        frame.setVisible(true); // Se il mio dispositivo è già stato approvato, entro in autostrada e
                                                // mostro un feedback
                    }
                }
            });
            task.run(); // Faccio partire il thread
        }
    }
}
