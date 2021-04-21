package entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * <b>Classe TelepassSystem</b>
 * 
 * Questa classe rappresenta l'intero sistema Telepass. Contiene tutti i metodi
 * per effettuare le operazione dei veicoli e degli amministratori. Il sistema
 * ha accessoad un databse locale in SQLite, che tiene traccia dei caselli, dei
 * dispositivi connessi al sistema e delle entrante e uscite dall'autostrada. Il
 * sistema è accessibile attraverso un singleton generato all'avvio del
 * programma.
 */
public class TelepassSystem {
    private static Connection dbConnection;
    private static Statement query;
    private static ResultSet result;
    private static String supervisorPassword;
    private static SimpleDateFormat formatter;

    private static TelepassSystem system = new TelepassSystem(); // Singleton

    public static TelepassSystem getSystem() {
        return system;
    }

    /**
     * Costruttore del sistema. Privato perché il sistema deve essere accessibile
     * solo al singleton.
     */
    private TelepassSystem() {
        supervisorPassword = "1234"; // Password di accesso al sistema
        formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); // Fromato usato dal sistema per riconoscere data e ora

        String dir = null;
        try {
            dir = new File(".").getCanonicalPath();
        } catch (IOException e1) {
        }

        try {
            dbConnection = DriverManager.getConnection("jdbc:sqlite:" + dir + "/sqlite/telepass.db"); // Creo il
                                                                                                      // database
                                                                                                      // all'interno
                                                                                                      // della directory
                                                                                                      // di progetto e
                                                                                                      // genero la
                                                                                                      // connessione
                                                                                                      // attraverso il
                                                                                                      // driver JDBC
            query = dbConnection.createStatement();

            // Azzero il database e creo le tabelle dei caselli autostradali, dei
            // dispositivi Telepass, delle entrate e delle uscite
            query.executeUpdate("DROP TABLE IF EXISTS tollbooths");
            query.executeUpdate("DROP TABLE IF EXISTS devices");
            query.executeUpdate("DROP TABLE IF EXISTS entrances");
            query.executeUpdate("DROP TABLE IF EXISTS exits");
            query.executeUpdate("CREATE TABLE tollbooths (number INTEGER PRIMARY KEY)");
            query.executeUpdate(
                    "CREATE TABLE devices (deviceID STRING PRIMARY KEY, approved INTEGER NOT NULL, serviceType STRING NOT NULL, plate STRING NOT NULL, firstName STRING NOT NULL, lastName STRING NOT NULL, paymentMethod STRING NOT NULL)");
            query.executeUpdate(
                    "CREATE TABLE entrances (deviceID STRING, tollbooth INTEGER NOT NULL, date STRING NOT NULL, riding INTEGER NOT NULL, PRIMARY KEY(deviceID, date))");
            query.executeUpdate(
                    "CREATE TABLE exits (deviceID STRING, tollbooth INTEGER NOT NULL, date STRING NOT NULL, PRIMARY KEY(deviceID, date))");
            addTollbooth();
            addTollbooth();
            addTollbooth();
            addTollbooth();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Verifica la correttezza della password inserita.
     * 
     * @param submittedPassword Password inserita
     * @return True se la password è corrtta, false se non lo è
     */
    public Boolean getSupervisorAccess(String submittedPassword) {
        return supervisorPassword.equals(submittedPassword);
    }

    /**
     * Metodo per rendere disponibile un nuovo casello autostradale.
     */
    public void addTollbooth() {
        int number = -1;

        try { // Numera ogni casello autostradale
            number = query.executeQuery("SELECT COUNT(*) FROM tollbooths").getInt(1);
        } catch (Exception e) {
        }
        try {
            query.executeUpdate("INSERT INTO tollbooths VALUES(" + number + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fornisce la lista di tutti i caselli autostradali dsponibili.
     * 
     * @return Lista dei numeri dei caselli autostradali disponibili.
     */
    public ArrayList<Integer> getTollbooths() {
        try {
            result = query.executeQuery("SELECT number FROM tollbooths");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<Integer> tollbooths = new ArrayList<Integer>();

        try {
            while (result.next()) { // Costruisco la lista in base al risultato della query
                tollbooths.add(result.getInt("number"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tollbooths;
    }

    /**
     * Fornisce la lista dei dispositivi Telepass connessi ed approvati.
     * 
     * @return Lista dei codici identificativi dei dispositivi approvati.
     */
    public ArrayList<String> getDevices() {
        try {
            result = query.executeQuery("SELECT deviceID FROM devices WHERE approved = 1");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<String> devices = new ArrayList<String>();

        try {
            while (result.next()) { // Costruisco la lista in base al risultato della query
                devices.add(result.getString("deviceID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return devices;
    }

    /**
     * Fornisce la lista dei dispositivi Telepass connessi e non ancora approvati.
     * 
     * @return Lista dei codici identificativi dei dispositivi non ancora approvati.
     */
    public ArrayList<String> getUnapprovedDevices() {
        try {
            result = query.executeQuery("SELECT deviceID FROM devices WHERE approved = 0");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<String> devices = new ArrayList<String>();

        try {
            while (result.next()) { // Costruisco la lista in base al risultato della query
                devices.add(result.getString("deviceID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return devices;
    }

    /**
     * Fornisco il servizio utilizzato dal dispositivi Telepass richiesto. Misura di
     * sicurezza: il tipo di servizio è mantenuto solo all'interno del database.
     * 
     * @param device Dispositivo Telepass richiesto
     * @return Tipo del servizio del dispositivo
     */
    public String getDeviceServiceType(TelepassDevice device) {
        try {
            result = query.executeQuery("SELECT serviceType FROM devices WHERE deviceID = '" + device.getID() + "'");

            return result.getString("serviceType"); // Restituisco il tipo
        } catch (SQLException e) {
            e.printStackTrace();

            return "NO DATA AVAILABLE"; // Se qualcosa va storto
        }
    }

    /**
     * Registra la connessione di un nuovo dispositivo Telepass nel database
     * 
     * @param device Dispositivo Telepass appena connesso
     */
    public void registerNewDevice(TelepassDevice device) {
        try { // Inserisci la tupla del dispositivo all'interno della tabella
            query.executeUpdate("INSERT INTO devices VALUES('" + device.getID() + "', 0, 'Standard', '"
                    + device.getLinkedVehicle().getPlate() + "', '" + device.getLinkedVehicle().getFirstName() + "', '"
                    + device.getLinkedVehicle().getLastName() + "', '" + device.getLinkedVehicle().getPaymentMethod()
                    + "')");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Approva un nuovo dispositivo Telepass.
     * 
     * @param device Dispositivo Telepass da approvare
     */
    public void addNewDevice(String device) {
        try {
            query.executeUpdate("UPDATE devices SET approved = 1 WHERE deviceID = '" + device + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Rimuovi un dispositivo Telepass.
     * 
     * @param device Dispositivo da rimuovere
     */
    public void removeDevice(String device) {
        try {
            query.executeUpdate("UPDATE devices SET approved = 0 WHERE deviceID = '" + device + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Modifica la targa associata a un dispositivo Telepass e rimuovilo
     * momentaneamente.
     * 
     * @param device Dispositivo da modificare
     * @param plate  Nuova targa da associare al dispositivo
     */
    public void updateDevice(String device, String plate) {
        try {
            query.executeUpdate(
                    "UPDATE devices SET plate = '" + plate + "', " + "approved = 0 WHERE deviceID = '" + device + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Estendi il servizio di un dispositivo Telepass.
     * 
     * @param device Dispositivo Telepass che richiede l'estensione
     */
    public void extendService(String device) {
        try {
            query.executeUpdate("UPDATE devices SET serviceType = 'Extended' WHERE deviceID = '" + device + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Registra l'entrata in autostrada di un dispositivo Telepass, se questo è
     * approvato.
     * 
     * @param device    Dispositivo che si avvicina al casello
     * @param tollbooth Casello scelto
     * @return True se il dispositivo è entrato, false se non è entrato
     */
    public synchronized Boolean registerEntrance(TelepassDevice device, int tollbooth) {
        try { // Controlla se il dispositivo Telepass è stato approvato
            result = query.executeQuery("SELECT approved FROM devices WHERE deviceID = '" + device.getID() + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (result.getInt(1) == 1) { // Se è stato approvato, lascialo passare
                device.setRiding(true); // Aggiorna il suo stato

                try { // Registra l'entrata nel database
                    query.executeUpdate("INSERT INTO entrances VALUES('" + device.getID() + "', " + tollbooth + ", '"
                            + formatter.format(new Date(System.currentTimeMillis())) + "', 1)"); // Regista ora e data
                                                                                                 // corrente
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }

        return true;
    }

    /**
     * Registra l'uscita dall'autostrada di un dispositivo Telepass.
     * 
     * @param device    Dispositivo che si avvicina al casello
     * @param tollbooth Casello scelto
     */
    public synchronized void registerExit(TelepassDevice device, int tollbooth) {
        device.setRiding(false); // Aggiorna lo stato del dispositivo Telepass

        Date date = new Date(System.currentTimeMillis()); // Registra data e ora dell'uscita immediatamente, per evitare
                                                          // costi ingiustificati

        try {
            query.executeUpdate("INSERT INTO exits VALUES('" + device.getID() + "', " + tollbooth + ", '"
                    + formatter.format(date) + "')");
            query.executeUpdate("UPDATE entrances SET riding = 0 WHERE deviceID = '" + device.getID() + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Double rideCostMultiplier = 0.000005; // Moltiplicatore per determinare il costo del viaggio
        long rideDuration = 0; // Durata del viaggio

        try { // Controlla che tipo di servizio sta usando il dispositivo uscente
            result = query.executeQuery("SELECT serviceType FROM devices WHERE deviceID = '" + device.getID() + "'");

            if (result.getString("serviceType").equals("Extended")) // Se il servizio è esteso, aumenta il
                                                                    // moltiplicatore
                rideCostMultiplier = 0.000008;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try { // Ricava data e ora dell'entrata di questo stesso dispositivo e calcola il
              // tempo effettivo di viaggio
            result = query.executeQuery(
                    "SELECT date FROM entrances WHERE deviceID = '" + device.getID() + "' ORDER BY date DESC LIMIT 1");

            rideDuration = date.getTime() - formatter.parse(result.getString("date")).getTime();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        device.addNewPayment(rideDuration * rideCostMultiplier); // Addebita il costo del viaggio al dispositivo
                                                                 // Telepass
    }

    /**
     * Calcola e fornisce le statistiche relative al sistema Telepass in genrale e
     * ai singoli caselli.
     * 
     * @return Stringa contenente le statistiche
     */
    public String calculateStats() {
        int totalDevices = 0, totalUnapprovedDevices = 0, totalEntrances = 0, totalExits = 0, stillRiding = 0,
                topTollbooth = -1;
        String topUser = "NO DATA AVAILABLE", stats = "";

        try { // Ricava tutte le informazione attraverso delle query
            totalDevices = query.executeQuery("SELECT COUNT(*) FROM devices").getInt(1);
            totalUnapprovedDevices = query.executeQuery("SELECT COUNT(*) FROM devices WHERE approved = 0").getInt(1);
            totalEntrances = query.executeQuery("SELECT COUNT(*) FROM entrances").getInt(1);
            totalExits = query.executeQuery("SELECT COUNT(*) FROM exits").getInt(1);
            stillRiding = query.executeQuery("SELECT COUNT(*) FROM entrances WHERE riding = 1").getInt(1);
            topTollbooth = query.executeQuery(
                    "SELECT tollbooth FROM (SELECT tollbooth, COUNT(*) AS counter FROM entrances GROUP BY tollbooth ORDER BY counter DESC LIMIT 1)")
                    .getInt(1);
            result = query.executeQuery(
                    "SELECT deviceID, plate, firstName, lastName FROM devices WHERE deviceID = (SELECT deviceID FROM (SELECT deviceID, COUNT(*) AS counter FROM entrances GROUP BY deviceID ORDER BY counter DESC LIMIT 1))");

            topUser = result.getString("deviceID");
            topUser += " - " + result.getString("plate");
            topUser += " - " + result.getString("firstName");
            topUser += " " + result.getString("lastName");

            int size = query.executeQuery("SELECT COUNT(*) FROM tollbooths").getInt(1);

            for (int i = 0; i < size; i++) { // Costruisci le statistiche per i singoli caselli
                stats += "<br/>Entrances tollbooth " + i + ": "
                        + query.executeQuery("SELECT COUNT(*) FROM entrances WHERE tollbooth = " + i).getInt(1);
                stats += "<br/>Exits tollbooth " + i + ": "
                        + query.executeQuery("SELECT COUNT(*) FROM exits WHERE tollbooth = " + i).getInt(1);

                result = query.executeQuery(
                        "SELECT deviceID, plate, firstName, lastName FROM devices WHERE deviceID = (SELECT deviceID FROM (SELECT deviceID, COUNT(*) AS counter FROM entrances WHERE tollbooth = "
                                + i + " GROUP BY deviceID ORDER BY counter DESC LIMIT 1))");

                if (result.isBeforeFirst()) {
                    stats += "<br/>Top user tollbooth " + i + ": " + result.getString("deviceID");
                    stats += " - " + result.getString("plate");
                    stats += " - " + result.getString("firstName");
                    stats += " " + result.getString("lastName") + "<br/>";
                } else
                    stats += "<br/>Top user tollbooth " + i + ": NO DATA AVAILABLE<br/>";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "<html>Last updated: " + formatter.format(new Date(System.currentTimeMillis())) + "<br/>Total devices: "
                + totalDevices + "<br/>Total unapproved devices: " + totalUnapprovedDevices + "<br/>Total entrances: "
                + totalEntrances + "<br/>Total exits: " + totalExits + "<br/>Still riding: " + stillRiding
                + "<br/>Top user: " + topUser + "<br/>Top tollbooth: " + topTollbooth + "<br/>" + stats; // Costruisci
                                                                                                         // la stringa
                                                                                                         // in formato
                                                                                                         // HTML per una
                                                                                                         // migliore
                                                                                                         // visualizzazione
    }
}
