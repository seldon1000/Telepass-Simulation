package commands;

import ui.MyFrame;
import ui.MyPanel;

/**
 * Primo passo per generare un nuovo veicolo: inserire la targa.
 */
public class CreateVehicle implements CommandInterface {
    /**
     * Questo comando, una volta eseguito, mostrerà una piccola finestra contenente
     * uno spazio di inserimento in cui l'utente dovrà inserire la targa. La
     * finestra è dotata di un pulsante di conferma che, una volta premuto,
     * preleverà la targa inserita dall'utente.
     */
    public void execute() {
        MyFrame frame = new MyFrame(350, 200, 3, 1, "Write plate:");
        MyPanel plate = new MyPanel.MyPanelBuilder(1, 1).addInputField().build(); // Spazio di inserimento per inserire
                                                                                  // la
                                                                                  // targa
        MyPanel menu = new MyPanel.MyPanelBuilder(1, 1).addButton("Confirm", new ConfirmPlate(frame, plate)).build(); // Pulsante
                                                                                                                      // per
                                                                                                                      // confermare
                                                                                                                      // l'inserimento

        frame.add(plate);
        frame.add(menu);
        frame.setVisible(true);
    }
}
