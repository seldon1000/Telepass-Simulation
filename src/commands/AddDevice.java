package commands;

import entities.TelepassSystem;
import ui.MyFrame;

/**
 * Approva un nuovo dispositivo Telepass
 */
public class AddDevice implements CommandInterface {
    private MyFrame frame;
    private String device;

    /**
     * Costruttore del comando.
     * 
     * @param frame  Finestra di scelta
     * @param device Dispotivo Telepass scelto
     */
    public AddDevice(MyFrame frame, String device) {
        this.frame = frame;
        this.device = device;
    }

    /**
     * Questo comando, una volta eseguito, chiede al sistema Telepass di approvare
     * un nuovo dispositivo e concedergli dunque l'accesso all'autostrada, poi
     * mostra un feedback.
     */
    public void execute() {
        TelepassSystem.getSystem().addNewDevice(device); // Chiede al sistema Telepass di approvare il dispositivo

        this.frame.dispose();
        this.frame = new MyFrame(350, 200, 1, 1, "Done!"); // Feedback
        this.frame.setVisible(true);
    }
}
