package commands;

import java.util.ArrayList;

import entities.TelepassSystem;
import ui.MyFrame;
import ui.MyPanel;

/**
 * Primo passo per approvare un nuovo dispositivo Telepass: mostrare i
 * dispositivi non ancora approvati e sceglierne uno
 */
public class ListUnapprovedDevices implements CommandInterface {
    /**
     * Questo comando, una volta eseguito, mostra una finestra contenente una lista
     * di dispositivi Telepass non ancora approvati, prodotta direttamente dal
     * sistema. Ogni elemento della lista possiede un pulsante per la selezione. È
     * presente anche un pulsante per annullare l'operazione e tornare indietro.
     */
    public void execute() {
        MyFrame frame = new MyFrame(400, 800, 20, 1, "Choose the device you want to add:");
        MyPanel back = new MyPanel.MyPanelBuilder(1, 1).addButton("Back", new Back(frame)).build(); // Pulsante per
                                                                                                    // tornare
                                                                                                    // indietro
        MyPanel item;

        ArrayList<String> devices = TelepassSystem.getSystem().getUnapprovedDevices(); // Richiedo la lista dei dispositivi
                                                                                  // non ancora approvati dal sistema
                                                                                  // Telepass

        for (String device : devices) { // Per ogni dispositivo non ancora approvato, genero un pulsante di scelta
            item = new MyPanel.MyPanelBuilder(1, 2).addLabel(device).addButton("Add", new AddDevice(frame, device))
                    .build(); // Codice
                              // del
                              // dispositivo
                              // e
                              // pulsante
                              // di
                              // scelta
            frame.add(item);
        }

        frame.add(back);
        frame.setVisible(true); // Mostro la lista solo quando tutti i dispositivi non ancora approvati sono
                                // stati aggiunti ad essa
    }
}
