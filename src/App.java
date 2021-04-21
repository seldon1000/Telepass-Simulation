import commands.CreateSupervisor;
import commands.CreateVehicle;
import commands.Exit;
import entities.TelepassSystem;
import ui.MyFrame;
import ui.MyPanel;

/**
 * <strong> --- Simulazione Sistema Telepass --- </strong>
 * A.A. 2020/2021
 * 
 * @author Nicolas Mariniello
 */
public class App {
    /**
     * Main, avvio del programma
     * 
     * @param args Eventuali argomenti
     */
    public static void main(String[] args) {
        TelepassSystem system = TelepassSystem.getSystem(); // Mi assicuro che il singleton venga creato immediatamente

        MyFrame frame = new MyFrame(400, 600, 2, 1, "Choose mode:"); // Frame che conterrà la scelta della modalità
        MyPanel menu = new MyPanel.MyPanelBuilder(3, 1).addButton("Vehicle", new CreateVehicle()) // Pulanti: accedere
                                                                                                  // come
                                                                                                  // utente normale,
                                                                                                  // come
                                                                                                  // amministratore o
                                                                                                  // uscire dal
                                                                                                  // programma
                .addButton("Supervisor", new CreateSupervisor()).addButton("Exit", new Exit(frame)).build();

        frame.add(menu);
        frame.setVisible(true); // Mostro il frame generato
    }
}
