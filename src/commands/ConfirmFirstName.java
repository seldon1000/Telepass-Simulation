package commands;

import ui.MyFrame;
import ui.MyPanel;

/**
 * Terzo passo per generare un nuovo veicolo: inserire il cognome.
 */
public class ConfirmFirstName implements CommandInterface {
    private MyFrame frame;
    private String plate;
    private MyPanel firstName;

    /**
     * Costruttore del comando.
     * 
     * @param frame     // Finestra di inserimento
     * @param plate     // Stringa che contiene la targa inserita dall'utente
     * @param firstName // Spazio di inserimento dal quale prelevare il nome
     *                  inserito dall'utente
     */
    public ConfirmFirstName(MyFrame frame, String plate, MyPanel firstName) {
        this.frame = frame;
        this.plate = plate;
        this.firstName = firstName;
    }

    /**
     * Questo comando, una volta eseguito, preleverà il nome inserito
     * precedentemente dall'utente e mostrerà una piccola finestra contenente uno
     * spazio di inserimento in cui l'utente dovrà inserire il proprio cognome. La
     * finestra è dotata di un pulsante che, una volta premuto, preleverà il cognome
     * inserito dell'utente.
     */
    public void execute() {
        if (this.firstName.getInputText().isEmpty()) { // Il campo non può essere lasciato vuoto
            MyFrame warningFrame = new MyFrame(350, 250, 2, 1, "Warning:");
            MyPanel warning = new MyPanel.MyPanelBuilder(1, 1).addLabel("Fill the field before confirming!").build();

            warningFrame.add(warning);
            warningFrame.setVisible(true); // Se il campo è vuoto, mostro un messaggio di errore
        } else {
            this.frame.dispose();
            this.frame = new MyFrame(350, 200, 3, 1, "Write last name:"); // Ricostruisco la finestra

            MyPanel lastName = new MyPanel.MyPanelBuilder(1, 1).addInputField().build(); // Spazio di inserimento per il
                                                                                         // cognome
            MyPanel menu = new MyPanel.MyPanelBuilder(1, 1) // Pulsante per confermare l'inserimento
                    .addButton("Confirm",
                            new ConfirmLastName(this.frame, this.plate, this.firstName.getInputText(), lastName))
                    .build();

            this.frame.add(lastName);
            this.frame.add(menu);
            this.frame.setVisible(true);
        }
    }
}
