package commands;

import entities.TelepassDevice;
import entities.TelepassSystem;
import ui.MyFrame;
import ui.MyPanel;

/**
 * Cambio targa/veicolo.
 */
public class ConfirmUpdatePlate implements CommandInterface {
    private TelepassDevice device;
    private MyFrame frame;
    private MyPanel newPlate;

    /**
     * Costruttore del comando.
     * 
     * @param device   Istanza di Vehicle da modificare
     * @param frame    Finestra di inserimento
     * @param newPlate Spazio di inserimento dal quale prelevare la nuova targa
     *                 inserita dall'utente
     */
    public ConfirmUpdatePlate(TelepassDevice device, MyFrame frame, MyPanel newPlate) {
        this.device = device;
        this.frame = frame;
        this.newPlate = newPlate;
    }

    /**
     * Questo comando, una volta eseguito, preleverà la nuova targa inserita
     * dall'utente ed effettuerà la modifica al veicolo. L'evento viene notificato
     * al sistema Telepass e il veicolo modificato dovrà essere riapprovato per
     * tornare ad utilizzare l'autostrada
     */
    public void execute() {
        if (this.newPlate.getInputText().isEmpty()) { // Il campo non può essere lasciato vuoto
            frame = new MyFrame(350, 200, 2, 1, "Warning");
            MyPanel warning = new MyPanel.MyPanelBuilder(1, 1).addLabel("Fill the field before confirming!").build();

            frame.add(warning);
            frame.setVisible(true); // Se il campo è vuoto, mostro un messaggio di errore
        } else {
            String plate = newPlate.getInputText(); // Prelevo la nuova targa inserita dall'utente
            this.frame.dispose();

            TelepassSystem.getSystem().updateDevice(device.getID(), plate); // Notifico l'evento al sistema

            this.device.getLinkedVehicle().setPlate(plate); // Effettuo la modifica
        }
    }
}
