package commands;

import java.util.ArrayList;

import entities.TelepassDevice;
import entities.TelepassSystem;
import ui.MyFrame;
import ui.MyPanel;

/**
 * Primo passo per uscire dall'autostrada: scegliere il casello.
 */
public class ExitHighway implements CommandInterface {
    TelepassDevice device;

    /**
     * Costruttore del comando.
     * 
     * @param device Dispositivo Telepass del veicolo che vuole uscire
     *               dall'autostrada
     */
    public ExitHighway(TelepassDevice device) {
        this.device = device;
    }

    /**
     * Questo comando, una volta eseguito, mostrerà una finestra contenente la lista
     * di tutti i caselli disponibili, la quale verrà fornita dal sistema Telepass
     * stesso. Ogni elemento della lista è un pulsante che agisce come conferma per
     * il casello.
     */
    public void execute() {
        ArrayList<Integer> tollbooths = TelepassSystem.getSystem().getTollbooths(); // Richiedo la lista dei caselli
                                                                               // disponibili
        MyFrame frame = new MyFrame(400, 800, 20, 1, "Choose tollbooth:"); // Finestra di scelta
        MyPanel back = new MyPanel.MyPanelBuilder(1, 1).addButton("Back", new Back(frame)).build(); // Pulsante per
                                                                                                    // annullare
                                                                                                    // l'operazione e
                                                                                                    // chiudere la
                                                                                                    // finestra
                                                                                                    // di scelta
        MyPanel item;

        for (int tollbooth : tollbooths) { // Genero un pulsante per ogni casello disponibile e lo aggiungo alla
                                           // finestra in costruzione
            item = new MyPanel.MyPanelBuilder(1, 1)
                    .addButton("Tollbooth " + tollbooth, new ChooseExit(frame, this.device, tollbooth)).build();
            frame.add(item);
        }

        frame.add(back);
        frame.setVisible(true);
    }
}
