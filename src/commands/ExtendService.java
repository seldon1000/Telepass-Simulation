package commands;

import entities.TelepassDevice;
import ui.MyFrame;
import ui.MyPanel;

/**
 * Primo passo per estendere il servizio: sei sicuro di voler procedere?
 */
public class ExtendService implements CommandInterface {
    TelepassDevice device;

    /**
     * Costruttore del comando.
     * 
     * @param device Veicolo che sta richiedendo l'estensione del servizio
     */
    public ExtendService(TelepassDevice device) {
        this.device = device;
    }

    /**
     * Questo comando, una volta eseguito, mostra una piccola finestra contenente
     * una breve informativa riguardo il servizio esteso e due pulsanti, uno
     * confermare e uno per annullare.
     */
    public void execute() {
        if (this.device.getServiceType().equals("Extended")) { // Controllo se sto usando già il servizio esteso
            MyFrame frame = new MyFrame(350, 200, 2, 1, "Warning:");
            MyPanel extend = new MyPanel.MyPanelBuilder(1, 2).addLabel("You already have the extended service!")
                    .build();

            frame.add(extend);
            frame.setVisible(true); // Se ho già il servizio esteso, mostro un messaggio di errore
        } else {
            MyFrame frame = new MyFrame(350, 200, 2, 1,
                    "<html>We offer you assistance on the highway<br/>at a little higher cost.<br/>Are you sure you want to extend your service?");
            MyPanel extend = new MyPanel.MyPanelBuilder(1, 2).addButton("No", new Back(frame))
                    .addButton("Yes", new ConfirmExtendService(frame, device)).build(); // Se ho ancora il servizio
                                                                                        // standard, mostro il menu di
                                                                                        // conferma

            frame.add(extend);
            frame.setVisible(true);
        }
    }
}
