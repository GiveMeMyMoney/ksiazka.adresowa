package dataBase;

import model.Contact;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by Marcin on 2015-10-21.
 */
public class DBrepo {
    private static Logger logger = Logger.getLogger(DBrepo.class.getName());
    //TODO poprawic logi jak prawowity czlowiek
    private Connection conn = null;
    private PreparedStatement prepStmt;

    private static volatile DBrepo instance = null;

    /**
     * Singleton ktory tworzy tylko 1 instancje klasy na wszystkich watkach(synchronized).
     */
    public static DBrepo getInstance() {
        if (instance == null) {
            synchronized (DBrepo.class) {
                if (instance == null) {
                    logger.info("Tworze instancje DBrepo");
                    instance = new DBrepo();
                }
            }
        }
        return instance;
    }

    public DBrepo() {
        try
        {
            logger.info("DBrepo construct");
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:to1.sqlite");
            logger.info("Success, connection with DB!");
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
            e.printStackTrace();
        }
    }

    /**
     * metoda sluzaca do ladowania kontaktu do BD.
     */
    public void putContactToDB(Contact contact) {
        logger.info("putContactToDB");
        try {
            prepStmt = conn.prepareStatement("insert into contact values (null, ?, ?, ?, ?, ?, ?);");
            //****//
            int i=0;
            prepStmt.setString(++i, contact.getName());
            prepStmt.setString(++i, contact.getSurname());
            prepStmt.setString(++i, contact.getProvince());
            prepStmt.setString(++i, contact.getAddress());
            prepStmt.setString(++i, contact.getPostalCode());
            prepStmt.setString(++i, contact.getCity());
            //****//
            prepStmt.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            e.printStackTrace();
        }

        //TODO zle autoincrementuje ID gdy sie usunie inkrementuje wyzej...

    }

    /**
     * metoda sluzaca do pobierania kontaktow z BD.
     */
    public ArrayList<Contact> getAllContactFromDB() {
        logger.info("getAllContactFromDB");
        ResultSet result = null;
        ArrayList<Contact> contacts = new ArrayList<>();
        try {
            prepStmt = conn.prepareStatement("SELECT * FROM contact");
            result = prepStmt.executeQuery();
            int id;
            String name, surname, province, address, postalCode, city;

            for(int i=0; result.next(); i++) {
                id = result.getInt("con_id");
                name = result.getString("con_name");
                surname = result.getString("con_surname");
                province = result.getString("con_province");
                address = result.getString("con_address");
                postalCode = result.getString("con_postal_code");
                city = result.getString("con_city");
                contacts.add(new Contact(id, name,surname,province,address,postalCode,city));
                logger.info(contacts.get(i).toString());
            }

            if(contacts.size() == 0) {
                logger.info("Pusta BD?");
            }
            return contacts;
        } catch (SQLException e1) {
            JOptionPane.showMessageDialog(null, e1);
            e1.printStackTrace();
            return null;
        }
    }

    /**
     * metoda sluzaca do usuwania kontaktu z BD o okreslonym ID.
     */
    public void deleteContactFromDBwithID(Contact contact) {
        logger.info("deleteContactFromDBwithID contact: " + contact.toString());
        ///nie moge wziac ID poniewaz przy tworzeniu kontaktu nie tworze ID - ktore jest AutoIncrementowane w BD.
        String name = contact.getName(), surname = contact.getSurname(), postalCode = contact.getPostalCode();
        try {
            prepStmt = conn.prepareStatement("DELETE from contact " +
                    "where con_name = '" + name + "' AND con_surname = '" + surname + "' AND con_postal_code = '" + postalCode + "'");
                    //dodawanie "'" zeby dla SQLa wygladalo to jako String.
            prepStmt.execute();
        } catch (SQLException e1) {
            JOptionPane.showMessageDialog(null, e1);
            e1.printStackTrace();
        }
    }


}
