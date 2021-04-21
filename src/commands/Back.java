package commands;

import ui.MyFrame;

/**
 * Indietro
 */
public class Back implements CommandInterface {
    private Thread entity;
    private MyFrame frame;

    /**
     * Primo costruttore del comando: distruggi semplicemente la finestra.
     * 
     * @param frame Finestra da distruggere
     */
    public Back(MyFrame frame) {
        this.frame = frame;
    }

    /**
     * Secondo costruttore del comando: distruggi la finestra e termina il thread.
     * 
     * @param frame  Finestra da distruggere
     * @param entity Thread da terminare
     */
    public Back(Thread entity, MyFrame frame) {
        this.entity = entity;
        this.frame = frame;
    }

    /**
     * Questo comando, una volta eseguito, distrugge la finestra per simulare
     * l'azione di "tornare indietro". Prova anche a terminare il thread.
     */
    public void execute() {
        this.frame.dispose();

        try { // Se un veicolo o un supervisore vogliono uscire del tutto dal programma,
              // bisogna anche terminare i loro thread, per questo tenta di terminare il
              // thread
            this.entity.interrupt();
        } catch (Exception e) {
        }
    }
}
