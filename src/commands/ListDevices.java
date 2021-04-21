package commands;

import java.util.ArrayList;

import entities.TelepassSystem;
import ui.MyFrame;
import ui.MyPanel;

/**
 * Primo passo per rimuovere un nuovo dispositivo Telepass: mostrare i
 * dispositivi già approvati e sceglierne uno
 */
public class ListDevices implements CommandInterface {
    /**
     * Questo comando, una volta eseguito, mostra una finestra contenente una lista
     * di dispositivi Telepass già approvati, prodotta direttamente dal sistema.
     * Ogni elemento della lista possiede un pulsante per la selezione. È presente
     * anche un pulsante per annullare l'operazione e tornare indietro.
     */
    public void execute() {
        MyFrame frame = new MyFrame(400, 800, 20, 1, "Choose the device you want to remove:");
        MyPanel back = new MyPanel.MyPanelBuilder(1, 1).addButton("Back", new Back(frame)).build(); // Pulsante per
                                                                                                    // tornare
        // indietro
        MyPanel item;

        ArrayList<String> devices = TelepassSystem.getSystem().getDevices(); // Richiedo la lista dei dispositivi
        // già approvati dal sistema
        // Telepass

        for (String device : devices) { // Per ogni dispositivo già approvato, genero un pulsante di scelta
            item = new MyPanel.MyPanelBuilder(1, 2).addLabel(device)
                    .addButton("Remove", new RemoveDevice(frame, device)).build(); // Codice
            // del
            // dispositivo
            // e
            // pulsante
            // di
            // scelta
            frame.add(item);
        }

        frame.add(back);
        frame.setVisible(true); // Mostro la lista solo quando tutti i dispositivi già approvati sono
        // stati aggiunti ad essa
    }
}
