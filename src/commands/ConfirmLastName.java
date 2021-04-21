package commands;

import ui.MyFrame;
import ui.MyPanel;

/**
 * Quarto passo per generare un nuovo veicolo: inserire il metodo di pagamento.
 */
public class ConfirmLastName implements CommandInterface {
    private MyFrame frame;
    private String plate;
    private String firstName;
    private MyPanel lastName;

    /**
     * Costruttore del comando.
     * 
     * @param frame     // Finestra di inserimento
     * @param plate     // Stringa contenete la targa inserita dall'utente
     * @param firstName // Stringa contenente il nome inserito dell'utente
     * @param lastName  // Spazio di inserimento dal quale prelevare il cognome
     *                  inserito dall'utente
     */
    public ConfirmLastName(MyFrame frame, String plate, String firstName, MyPanel lastName) {
        this.frame = frame;
        this.plate = plate;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Questo comando, una volta eseguito, preleverà il conome inserito
     * precedentemente dall'utente e mostrerà una piccola finestra contenente uno
     * spazio di inserimento in cui l'utente dovrà inserire il metodo di pagamento.
     * La finestra è dotata di un pulsante che, una volta premuto, preleverà il
     * metodo di pagamento inserito dell'utente.
     */
    public void execute() {
        if (this.lastName.getInputText().isEmpty()) { // Il campo non può essere lasciato vuoto
            MyFrame warningFrame = new MyFrame(350, 200, 2, 1, "Warning:");
            MyPanel warning = new MyPanel.MyPanelBuilder(1, 1).addLabel("Fill the field before confirming!").build();

            warningFrame.add(warning);
            warningFrame.setVisible(true); // Se il campo è vuoto, mostro un messaggio di errore

        } else {
            this.frame.dispose();
            this.frame = new MyFrame(350, 200, 3, 1, "Write payment method:"); // Ricostruisco la finestra

            MyPanel paymentMethod = new MyPanel.MyPanelBuilder(1, 1).addInputField().build(); // Spazio di inserimento
                                                                                              // per
                                                                                              // il metodo di pagamento
            MyPanel menu = new MyPanel.MyPanelBuilder(1, 1).addButton("Confirm", new ConfirmPaymentMethod(this.frame, // Pulsante
                                                                                                                      // per
                                                                                                                      // confermare
                                                                                                                      // l'inserimento
                    this.plate, this.firstName, this.lastName.getInputText(), paymentMethod)).build();

            this.frame.add(paymentMethod);
            this.frame.add(menu);
            this.frame.setVisible(true);
        }
    }
}
