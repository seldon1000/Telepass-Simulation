package commands;

import ui.MyFrame;
import ui.MyPanel;

/**
 * Secondo passo per generare un nuovo veicolo: inserire il nome.
 */
public class ConfirmPlate implements CommandInterface {
    private MyFrame frame;
    private MyPanel plate;

    /**
     * Costruttore del comando.
     * 
     * @param frame Finestra di inserimento, me la porto dietro per assicurarmi che
     *              venga distrutta e/o sostituita quando necessario
     * @param plate Spazio di inserimento dal quale prelevare la targa inserita
     */
    public ConfirmPlate(MyFrame frame, MyPanel plate) {
        this.frame = frame;
        this.plate = plate;
    }

    /**
     * Questo comando, una volta eseguito, preleverà la targa inserita
     * precedentemente dall'utente e mostrerà una piccola finestra contenente uno
     * spazio di inserimento in cui l'utente dovrà inserire il proprio nome. La
     * finestra è dotata di un pulsante che, una volta premuto, preleverà il nome
     * inserito dall'utente.
     */
    public void execute() {
        if (this.plate.getInputText().isEmpty()) { // Il campo non può essere lasciato vuoto
            MyFrame warningFrame = new MyFrame(350, 200, 2, 1, "Warning:");
            MyPanel warning = new MyPanel.MyPanelBuilder(1, 1).addLabel("Fill the field before confirming!").build();

            warningFrame.add(warning);
            warningFrame.setVisible(true); // Se il campo è vuoto, mostro un messaggio di errore
        } else {
            this.frame.dispose();
            this.frame = new MyFrame(350, 200, 3, 1, "Write first name:"); // Ricostruisco la finestra

            MyPanel firstName = new MyPanel.MyPanelBuilder(1, 1).addInputField().build(); // Spazio di inserimento per
                                                                                          // inserire il nome
            MyPanel menu = new MyPanel.MyPanelBuilder(1, 1) // Pulsante per confermare l'inserimento
                    .addButton("Confirm", new ConfirmFirstName(this.frame, this.plate.getInputText(), firstName))
                    .build();

            this.frame.add(firstName);
            this.frame.add(menu);
            this.frame.setVisible(true);
        }
    }
}
