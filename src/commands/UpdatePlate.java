package commands;

import entities.TelepassDevice;
import ui.MyFrame;
import ui.MyPanel;

/**
 * Primo passo per cambiare targa/veicolo: inserire la nuova targa.
 */
public class UpdatePlate implements CommandInterface {
    private TelepassDevice device;

    /**
     * Costruttore delcomando.
     * 
     * @param device istanza di Vehicle da modificare
     */
    public UpdatePlate(TelepassDevice device) {
        this.device = device;
    }

    /**
     * Questo comando, una volta eseguito, mostrerà una piccola finestra contenente
     * uno spazio di inserimento in cui l'utente dovrà inserire la nuova targa. La
     * finestra è dotata di un pulsante che, una volta premuto, preleverà la nuova
     * targa inserita dall'utente. La modifica non può essere apportata mentre si è
     * in autostrada, viene bensì mostrato un messaggio di errore.
     */
    public void execute() {
        if (device.isRiding()) { // Controllo se sono in autostrada
            MyFrame warning = new MyFrame(350, 200, 2, 1, "Warning:");
            MyPanel message = new MyPanel.MyPanelBuilder(1, 1)
                    .addLabel("You can't change vehicle while on the highway!").build();

            warning.add(message);
            warning.setVisible(true); // Se sono in autostrada, non posso cambiare e mostro un messaggio di errore
        } else {
            MyFrame frame = new MyFrame(350, 200, 3, 1, "Write new plate:");
            MyPanel newPlate = new MyPanel.MyPanelBuilder(1, 1).addInputField().build();
            MyPanel menu = new MyPanel.MyPanelBuilder(1, 1)
                    .addButton("Confirm", new ConfirmUpdatePlate(this.device, frame, newPlate)).build(); // Se non sono
                                                                                                         // in
                                                                                                         // autostrada,
                                                                                                         // posso
                                                                                                         // procedere
                                                                                                         // ad inserire
                                                                                                         // la nuova
                                                                                                         // targa

            frame.add(newPlate);
            frame.add(menu);
            frame.setVisible(true);
        }

    }
}
