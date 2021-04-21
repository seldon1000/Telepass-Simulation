package commands;

import entities.Supervisor;
import entities.TelepassSystem;
import ui.MyFrame;
import ui.MyPanel;

/**
 * Accedi alla console da amministratore.
 */
public class ConfirmPassword implements CommandInterface {
    private MyFrame frame;
    private MyPanel password;

    /**
     * Costruttore del comando.
     * 
     * @param frame    Finestra di inserimento
     * @param password Spazio di inserimento dal quale prelevare la password
     *                 inserita
     */
    public ConfirmPassword(MyFrame frame, MyPanel password) {
        this.frame = frame;
        this.password = password;
    }

    /**
     * Questo comando, una volta eseguito, preleverà la password inserita e chiederà
     * direttamente al sistema Telepass di verificare la sua correttezza. Se la
     * password è corretta, l'amministratore viene generato e si accede alla
     * console. Se la password non è corretta, mostra un messaggio di errore.
     */
    public void execute() {
        if (this.password.getInputText().isEmpty()) { // Il campo non può essere lasciato vuoto
            MyFrame warningFrame = new MyFrame(350, 200, 2, 1, "Warning:");
            MyPanel warning = new MyPanel.MyPanelBuilder(1, 1).addLabel("Fill the field before confirming!").build();

            warningFrame.add(warning);
            warningFrame.setVisible(true); // Se il campo è vuoto, mostro un messaggio di errore
        } else {
            if (TelepassSystem.getSystem().getSupervisorAccess(this.password.getInputText())) { // Se il campo non è vuoto,
                                                                                           // controlla la correttezza
                                                                                           // della passoword inserita
                this.frame.dispose();

                Supervisor supervisor = new Supervisor(); // Se è corretta, genero l'amministratore
                supervisor.start(); // Avvio l'amministratore
            } else {
                MyFrame warningFrame = new MyFrame(350, 200, 2, 1, "Warning:");
                MyPanel warning = new MyPanel.MyPanelBuilder(1, 1).addLabel("The password is incorrect!").build(); // Se
                                                                                                                   // non
                                                                                                                   // è
                                                                                                                   // corretta,
                                                                                                                   // mostro
                                                                                                                   // un
                                                                                                                   // messaggio
                                                                                                                   // di
                                                                                                                   // errore

                warningFrame.add(warning);
                warningFrame.setVisible(true);
            }
        }
    }
}
