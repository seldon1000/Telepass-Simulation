package commands;

import ui.MyFrame;

/**
 * Esci dal programma
 */
public class Exit implements CommandInterface {
    private MyFrame frame;

    /**
     * Costrutore del comando.
     * 
     * @param frame Finestra del programma
     */
    public Exit(MyFrame frame) {
        this.frame = frame;
    }

    /**
     * Questo comando, una volta eseguito, distrugge la finestra e termina l'intero
     * programma.
     */
    public void execute() {
        frame.dispose();
        System.exit(0);
    }
}
