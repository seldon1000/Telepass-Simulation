package commands;

import ui.MyFrame;
import ui.MyPanel;

/**
 * Primo passo per generare un nuovo amministratore: immettere la password di
 * accesso
 */
public class CreateSupervisor implements CommandInterface {
    /**
     * Questo comando, una volta eseguito, mostra una piccola finestra contenente
     * uno spazio di inserimento in cui l'utente dovrà inserire la password di
     * accesso da amministratore. La finestra è dotata di un pulsante che, una volta
     * premuto, preleverà il testo inserito e controllerà la sua correttezza.
     */
    public void execute() {
        MyFrame frame = new MyFrame(350, 200, 3, 1, "Submit password: (Hint for testing: 1234)");
        MyPanel plate = new MyPanel.MyPanelBuilder(1, 1).addInputField().build(); // Spazio di inserimento dal quale
                                                                                  // prelevare la password inserita
        MyPanel menu = new MyPanel.MyPanelBuilder(1, 1).addButton("Confirm", new ConfirmPassword(frame, plate)).build(); // Pulsante
                                                                                                                         // di
                                                                                                                         // conferma

        frame.add(plate);
        frame.add(menu);
        frame.setVisible(true);
    }
}
