package commands;

import entities.TelepassDevice;
import entities.TelepassSystem;
import ui.MyFrame;

/**
 * Estensione del servizio
 */
public class ConfirmExtendService implements CommandInterface {
    private MyFrame frame;
    private TelepassDevice device;

    /**
     * Costruttore del servizio.
     * 
     * @param frame  Finestra di conferma
     * @param device Dispositivo che sta richiedendo l'estensione del servizio
     */
    public ConfirmExtendService(MyFrame frame, TelepassDevice device) {
        this.frame = frame;
        this.device = device;
    }

    /**
     * Questo comando, una volta eseguito, estende il servizio, notifica l'evento al
     * sistema Telepass e mostra un feedback.
     */
    public void execute() {
        TelepassSystem.getSystem().extendService(device.getID()); // Notifico l'evento al sistema Telepass

        this.device.getLinkedVehicle().run(); // Ricostruisci la finestra del veicolo con l'informazione aggiornata

        this.frame.dispose();
        this.frame = new MyFrame(350, 200, 1, 1, "Done!"); // Feedback
        this.frame.setVisible(true);
    }
}