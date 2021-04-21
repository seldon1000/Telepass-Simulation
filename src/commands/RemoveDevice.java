package commands;

import entities.TelepassSystem;
import ui.MyFrame;

/**
 * Rimuovi un dispositivo Telepass
 */
public class RemoveDevice implements CommandInterface {
    private MyFrame frame;
    private String item;

    /**
     * Costruttore del comando.
     * 
     * @param frame Finestra di scelta
     * @param item  Codice del dispositivo da eliminare
     */
    public RemoveDevice(MyFrame frame, String item) {
        this.frame = frame;
        this.item = item;
    }

    /**
     * Questo comando, una volta eseguito, chiede al sistema Telepass di rimuovere
     * un dispositivo e concedergli dunque l'accesso all'autostrada, poi mostra un
     * feedback.
     */
    public void execute() {
        TelepassSystem.getSystem().removeDevice(item); // Chiede al sistema Telepass di approvare il dispositivo

        this.frame.dispose();
        this.frame = new MyFrame(350, 200, 1, 1, "Done!"); // Feedback
        this.frame.setVisible(true);
    }
}
