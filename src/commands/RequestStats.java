package commands;

import javax.swing.JScrollPane;

import entities.TelepassSystem;
import ui.MyFrame;
import ui.MyPanel;

/**
 * Produci statistiche riguardo il sistema Telepass e suoi singoli caselli
 */
public class RequestStats implements CommandInterface {
    private MyFrame frame;

    /**
     * Costruttore del comando.
     * 
     * @param frame Finestra delle statistiche
     */
    public RequestStats(MyFrame frame) {
        this.frame = frame;
    }

    /**
     * Questo comando, una volta eseguito, mostra una finestra contenente delle
     * statistiche relative al sistema Telepass e ai suoi singoli caselli. Le
     * statistiche sono prodotte direttamente dal sistema Telepass e comprondono: -
     * Data e ora dell'ultimo aggiornamento - Numero dei dispositivi connessi al
     * sistema; - Numero dei dispositivi non ancora approvati; - Numeri entrate in
     * autostrada; - Numero di uscite dall'autostrada; - Numero di dispositivi
     * ancora in viaggio in autostrada; - Utente che ha effettuato il maggior numero
     * di operazioni; - Casello più utilizzato; - Altre statistiche speficihe per i
     * singoli caselli, calcolate separatamente.
     * 
     * La finestra contiene anche un pulsante per tornare indietro e un per
     * aggiornare le statistiche.
     */
    public void execute() {
        try { // Poiché la finestra può essere aggiornata, prova prima a distruggerla e solo
              // dopo procedere alla sua costruzione
            this.frame.dispose();
        } catch (Exception e) {
        }

        this.frame = new MyFrame(500, 800, 3, 1, "Statistics");
        MyPanel stats = new MyPanel.MyPanelBuilder(1, 1).addLabel(TelepassSystem.getSystem().calculateStats()).build(); // Richiedi
                                                                                                                   // le
                                                                                                                   // statistiche
                                                                                                                   // al
                                                                                                                   // sistema
                                                                                                                   // Telepass
                                                                                                                   // in
                                                                                                                   // forma
                                                                                                                   // di
                                                                                                                   // stringa

        MyPanel buttons = new MyPanel.MyPanelBuilder(2, 1).addButton("Update", new RequestStats(this.frame))
                .addButton("Back", new Back(frame)).build(); // Pulsanti per tornare indietro e aggiornare le
                                                             // statistiche
        JScrollPane scroll = new JScrollPane(stats); // Ci sono molte statistiche, quindi inseriscile prima in un
                                                     // pannelo che possa scorrerle

        frame.add(scroll);
        frame.add(buttons);
        frame.setVisible(true);
    }
}
