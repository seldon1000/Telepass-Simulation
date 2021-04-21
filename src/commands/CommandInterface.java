package commands;

/**
 * <b>Interfaccia CommandInterface.</b>
 * 
 * Questa interfaccia è alla base dell'implementazione del pattern "command". Il
 * pattern "command" viene utilizzato per incapsulare tutte le informazioni
 * necessarie per effettuare determinate operazioni. Nella pratica, permette di
 * generare una sorta di telecomando, dotato di tanti pulsanti molto simili fra
 * loro, ma che compiono operazioni profondamente diverse, il tutto in maniera
 * sicura e centralizzata. Ogni classe che implementa questa interfaccia
 * rappresenta l'operazione di un pulsante e deve implementare il metodo
 * "execute()", il quale verrà chiamato quando il pulsante viene effettivamente
 * premuto.
 * 
 * Di fatti, l'interfaccia del programma può essere intesa come un telecomando,
 * e ogni suo pulsante esegue determinate operazioni.
 */
public interface CommandInterface {
    /**
     * Esegui il comando, metodo da implementare
     */
    public void execute();
}
