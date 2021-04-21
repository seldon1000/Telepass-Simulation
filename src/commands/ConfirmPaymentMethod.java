package commands;

import entities.TelepassDevice;
import entities.TelepassSystem;
import entities.Vehicle;
import ui.MyFrame;
import ui.MyPanel;

/**
 * Generazione del veicolo.
 */
public class ConfirmPaymentMethod implements CommandInterface {
    private MyFrame frame;
    private String plate;
    private String firstName;
    private String lastName;
    private MyPanel paymentMethod;

    /**
     * Costruttore del comando.
     * 
     * @param frame         Finestra di inserimento
     * @param plate         Stringa contenente la targa inserita dall'utente
     * @param firstName     Stringa contenente il nome inserito dall'utente
     * @param lastName      Stringa contenente il cognome inserito dall'utente
     * @param paymentMethod Spazio di inserimento dal quale prelevare il metodo di
     *                      pagamento inserito dall'utente
     */
    public ConfirmPaymentMethod(MyFrame frame, String plate, String firstName, String lastName, MyPanel paymentMethod) {
        this.frame = frame;
        this.plate = plate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.paymentMethod = paymentMethod;
    }

    /**
     * Questo comando, una volta eseguito, preleverà il metodo di pagamento inserito
     * precedentemente dall'utente, genererà un nuovo dispotivo Telepass da
     * associare al nuovo veicolo, invia tutta le informazioni raccolte al
     * costruttore del veicolo e notifica al sistema Telepass l'arrivo di un nuovo
     * dispositivo che potrà essere approvato.
     */
    public void execute() {
        if (this.paymentMethod.getInputText().isEmpty()) { // Il campo non può essere lasciato vuoto
            MyFrame warningFrame = new MyFrame(350, 200, 2, 1, "Warning:");
            MyPanel warning = new MyPanel.MyPanelBuilder(1, 1).addLabel("Fill the field before confirming!").build();

            warningFrame.add(warning);
            warningFrame.setVisible(true); // Se il campo è vuoto, mostro un messaggio di errore
        } else {
            this.frame.dispose();

            TelepassDevice device = new TelepassDevice(); // Genero il dispositivo Telepass per il nuovo
                                                          // veicolo

            Vehicle vehicle = new Vehicle(this.plate, this.firstName, this.lastName, this.paymentMethod.getInputText()); // Genero
            // il
            // veicolo
            // passando
            // tutte
            // le
            // informazioni
            // raccolte
            device.setLinkedVehicle(vehicle);
            vehicle.start();

            TelepassSystem.getSystem().registerNewDevice(device); // Notifico al sistema Telepass l'evento
        }
    }
}
