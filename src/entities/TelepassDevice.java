package entities;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.UUID;

import ui.MyFrame;

/**
 * <b>Classe TelepassDevice</b>
 * 
 * Ogni istanza di questa classe rappresenta un dispositivo Telepass. Ogni
 * dispotivo è associato ad un veicolo e permette a questo l'accesso
 * all'autostrada. Il dispositivo è identificato da un codice univoco, possiede
 * inoltre una variabile che punta al veicolo associato, lo stato del
 * dispositivo e una cronologia dei pagamenti.
 */
public class TelepassDevice {
    private String ID;
    private Vehicle linkedVehicle;
    private Boolean riding;
    private ArrayList<Double> paymentHistory;

    /**
     * Costruttore del dispositivo Telepass.
     */
    public TelepassDevice() {
        this.ID = UUID.randomUUID().toString().substring(0, 7);
        this.riding = false;
        this.paymentHistory = new ArrayList<Double>();
    }

    /**
     * @return Codice identificativo del dispositivo Telepass
     */
    public String getID() {
        return this.ID;
    }

    /**
     * @return Veicolo associato
     */
    public Vehicle getLinkedVehicle() {
        return this.linkedVehicle;
    }

    /**
     * @return Tipo di servizio in uso
     */
    public String getServiceType() {
        return TelepassSystem.getSystem().getDeviceServiceType(this);
    }

    /**
     * @return Stato del dispositivo, true se è inautostrada, false se non è in
     *         autostrada
     */
    public Boolean isRiding() {
        return this.riding;
    }

    /**
     * @param vehicle Veicolo da associare
     */
    public void setLinkedVehicle(Vehicle vehicle) {
        this.linkedVehicle = vehicle;
        this.linkedVehicle.setDevice(this);
    }

    /**
     * @param bool Stato da aggiornare
     */
    public void setRiding(Boolean bool) {
        this.riding = bool;
    }

    /**
     * @param plate Nuova targa
     */
    public void updatePlate(String plate) {
        this.linkedVehicle.setPlate(plate);
    }

    /**
     * @param payment Importo di un nuovo pagamento
     */
    public void addNewPayment(Double payment) {
        this.paymentHistory.add(payment);

        MyFrame frame = new MyFrame(350, 200, 1, 1, "You payed " + new DecimalFormat("###.###").format(payment) + "$");

        frame.setVisible(true); // Mostro una finestra contenente l'importo della corsa appena conclusa
    }
}
