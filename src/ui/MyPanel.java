package ui;

import java.util.ArrayList;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import commands.CommandInterface;

/**
 * <b>Classe MyPanel</b>
 * 
 * Questa classe viene utilizzata per la costruzione dell'interfaccia durante
 * l'esecuzione del programma. Ogni istanza di questa classe rappresenta un
 * pannello che deve essere aggiunto ad un frame per essere mostrato. Ogni
 * pannello può contenere degli spazi di input, dei, pulsanti, del semplice
 * testo e così via. Data tale varietà, si è scelto di implementare il pattern
 * "builder". Il pattern "builder" garantisce l'immutabilità ad instanze che in
 * realtà sono molto diverse tra loro. Non tutte le istanze vengono costruite
 * ugualmente, potrebbero esserci campi obbligaori e campi falcoltativi, quindi,
 * invece di scrivere tanti costruttori diversi, si generalizza la costruzione
 * dell'istanza attraverso una classe builder interna alla classe stessa. Il
 * builder permette di aggiungere ciò che serve e solo alla fine chiama il reale
 * costruttore della classe.
 */
public class MyPanel extends JPanel {
    private static final long serialVersionUID = -2682609828854113275L; // Superfluo, aggiunto per motivi tecnici

    private int rows;
    private int cols;
    private ArrayList<JLabel> labels;
    private JTextArea inputField;
    private ArrayList<PanelButton> buttons;

    /**
     * Costruttore del pannello. Il costruttore è privato, perché può essere
     * chiamato esclusivamente dal builder interno.
     * 
     * @param builder Istanza del builder
     */
    private MyPanel(MyPanelBuilder builder) {
        this.rows = builder.rows;
        this.cols = builder.cols;
        this.labels = builder.labels;
        this.inputField = builder.inputField;
        this.buttons = builder.buttons;

        this.setLayout(new GridLayout(this.rows, this.cols));

        try { // Provo ad inserire degli spazi di inserimento
            this.add(this.inputField);
        } catch (Exception e) {
        }

        for (JLabel label : this.labels) // Inserisco le label
            this.add(label);

        for (PanelButton button : this.buttons) // Inserisco i pulsanti
            this.add(button);
    }

    /**
     * @return Test inserito nello spazio di inserimento
     */
    public String getInputText() {
        return inputField.getText();
    }

    /**
     * <b>Classe MyPanelBuilder</b>
     * 
     * Questa è la classe del builder, l'unica in grado di costruire i pannelli.
     */
    public static class MyPanelBuilder {
        private int rows;
        private int cols;
        private ArrayList<JLabel> labels;
        private JTextArea inputField;
        private ArrayList<PanelButton> buttons;

        /**
         * Costruttore del builder. Qui vengono caricati i campi obbligatori della
         * classe MyPanel.
         * 
         * @param rows Righe
         * @param cols Colonne
         */
        public MyPanelBuilder(int rows, int cols) {
            this.rows = rows;
            this.cols = cols;
            this.labels = new ArrayList<JLabel>();
            this.buttons = new ArrayList<PanelButton>();
        }

        /**
         * Primo campo facoltativo: spazio di inserimento.
         * 
         * @return Appendi builder
         */
        public MyPanelBuilder addInputField() {
            this.inputField = new JTextArea();

            return this;
        }

        /**
         * Secondo campo facoltativo: testo.
         * 
         * @param label Nuovo testo da aggiungere
         * @return Appendi builder
         */
        public MyPanelBuilder addLabel(String label) {
            this.labels.add(new JLabel(label));

            return this;
        }

        /**
         * Terzo campo facoltativo: pulsante.
         * 
         * @param commandName Nome del pulsante
         * @param command     Comando da associare al pulsante
         * @return Appendi builder
         */
        public MyPanelBuilder addButton(String commandName, CommandInterface command) {
            this.buttons.add(new PanelButton(commandName, command));

            return this;
        }

        /**
         * Chiamo il costruttore di MyPanel.
         * 
         * @return Appendi builder
         */
        public MyPanel build() {
            return new MyPanel(this);
        }
    }
}
