package entities;

import commands.EnterHighway;
import commands.ExitHighway;
import commands.ExtendService;
import commands.Back;
import commands.UpdatePlate;
import ui.MyFrame;
import ui.MyPanel;

/**
 * <b>Classe Vehicle.</b>
 * 
 * Ogni istanza di questa classe rappresenta un veicolo. Ogni veicolo possiede
 * una targa, un dispositivo Telepass che gli consente di accedere ai caselli
 * autostradali, nome e cognome del proprietario e infine il metodo di pagamento
 * scelto per gli addebiti automatici del sistema.
 */
public class Vehicle extends Thread {
    private TelepassDevice device;
    private String plate;
    private String firstName;
    private String lastName;
    private String paymentMethod;
    private MyFrame frame;
    private MyPanel labels;
    private MyPanel buttons;

    /**
     * Costruttore del veicolo.
     * 
     * @param plate         Dispositivo Telepass
     * @param firstName     Nome del proprietario
     * @param lastName      Cognome del proprietario
     * @param paymentMethod Metodo di pagamento per gli addebiti automatici
     */
    public Vehicle(String plate, String firstName, String lastName, String paymentMethod) {
        this.plate = plate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.paymentMethod = paymentMethod;
    }

    /**
     * Ogni veicolo è un thread indipendente. Generato il veicolo, verrà
     * immediatamente mostrata una finestra contenente un menu ricco di informazioni
     * e pulsanti.
     */
    public void run() {
        try {
            this.frame.dispose(); // Il metodo run potrebbe essere chiamato più volte per aggiornare le
                                  // informazioni mostrate nel menu, quindi provo a distruggere la finestra
                                  // precedntemente mostrata, per poi ricostruirla
        } catch (Exception e) {
        }

        this.frame = new MyFrame(500, 800, 3, 1, "Vehicle Mode"); // Finestra del veicolo
        this.labels = new MyPanel.MyPanelBuilder(3, 2).addLabel("Device ID: " + this.device.getID())
                .addLabel("Plate: " + this.plate).addLabel("Service type: " + this.device.getServiceType())
                .addLabel("Payment method: " + this.paymentMethod).addLabel("First name: " + this.firstName)
                .addLabel("Last name: " + this.lastName).build();
        this.buttons = new MyPanel.MyPanelBuilder(5, 1).addButton("Enter Highway", new EnterHighway(this.device))
                .addButton("Exit Highway", new ExitHighway(this.device))
                .addButton("Change Vehicle", new UpdatePlate(this.device))
                .addButton("Extend Service", new ExtendService(this.device))
                .addButton("Exit", new Back(this, this.frame)).build();

        this.frame.add(this.labels);
        this.frame.add(this.buttons);
        this.frame.setVisible(true);
    }

    /**
     * @return Dispotivo Telepass
     */
    public TelepassDevice getDevice() {
        return this.device;
    }

    /**
     * @return Targa
     */
    public String getPlate() {
        return this.plate;
    }

    /**
     * @return Nome
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * @return Cognome
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * @return Metodo di pagamento
     */
    public String getPaymentMethod() {
        return this.paymentMethod;
    }

    /**
     * @param device Dipositivo Telepass associato al veicolo
     */
    public void setDevice(TelepassDevice device) {
        this.device = device;
    }

    /**
     * @param plate Nuova targa da sotituire alla vecchia
     */
    public void setPlate(String plate) {
        this.plate = plate;

        this.run();
    }
}
