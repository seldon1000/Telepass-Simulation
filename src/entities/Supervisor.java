package entities;

import commands.ListDevices;
import commands.ListUnapprovedDevices;
import commands.RequestStats;
import commands.Back;
import ui.MyFrame;
import ui.MyPanel;

/**
 * <b>Classe Supervisor</b>
 * 
 * Ogni instanza di questa classe rappresenta un amministratore del sistema
 * Telepass. Ogni amministratore potrà accedere al sistema immettendo una
 * password e sarà in grado di effettuare delle operazioni privilegiate.
 */
public class Supervisor extends Thread {
    /**
     * Ogni amministratore è un thread indipendente. Generato l'amministratore,
     * mostra immediatamente una finestra contenente tutte le operazioni che un
     * amministratore può effettuare.
     */
    public void run() {
        MyFrame frame = new MyFrame(400, 600, 2, 1, "Supervisor Mode");
        MyPanel panel = new MyPanel.MyPanelBuilder(4, 1).addButton("Add Device", new ListUnapprovedDevices())
                .addButton("Remove Device", new ListDevices()).addButton("Stats", new RequestStats(null))
                .addButton("Exit", new Back(this, frame)).build(); // Pulsanti per effettuare le operazione di:
                                                                   // approvazione nuovo dispositivo, rimozione
                                                                   // dispositivo e richiesta di statistiche

        frame.add(panel);
        frame.setVisible(true);
    }
}
