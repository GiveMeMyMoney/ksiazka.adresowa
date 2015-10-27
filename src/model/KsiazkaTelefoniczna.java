package model;

import dataBase.DBrepo;
import dataBase.IDBrepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by Marcin on 2015-10-22.
 */
public class KsiazkaTelefoniczna implements IDBrepo {
    private static Logger logger = Logger.getLogger(KsiazkaTelefoniczna.class.getName());

    ArrayList<Contact> contacts = new ArrayList<>();

    private static volatile KsiazkaTelefoniczna instance = null;
    //private static DBrepo dBrepo = null;

    public static KsiazkaTelefoniczna getInstance() {
        if (instance == null) {
            synchronized (KsiazkaTelefoniczna.class) {
                if (instance == null) {
                    logger.info("Tworze instancje KsiazkiTelefonicznej");
                    instance = new KsiazkaTelefoniczna();
                    DBrepo.getInstance();
                }
            }
        }
        return instance;
    }

    /**
     * Konstruktor.
     */
    private KsiazkaTelefoniczna() {
    }

    /**
     * metoda sluzaca do ladowania kontaktu do BD.
     */
    @Override
    public void putContactToDB(Contact contact) {
        logger.info("putContactToDB: " + contact.toString());
        DBrepo.getInstance().putContactToDB(contact);
    }

    /**
     * metoda sluzaca do pobierania kontaktow z BD.
     */
    @Override
    public ArrayList<Contact> getAllContactFromDB() {
        contacts = DBrepo.getInstance().getAllContactFromDB();
        if(contacts.size() == 0)
            logger.info("Pusta BD?");
        else
            logger.info("Rozmiar BD: " + contacts.size());
        return contacts;
    }

    /**
     * metoda sluzaca do usuwania kontaktu z BD o okreslonym ID.
     */
    @Override
    public void deleteContactFromDBwithID(Contact contact) {
        logger.info("deleteContactFromDBwithID: " + contact.toString());
        DBrepo.getInstance().deleteContactFromDBwithID(contact);
    }
}
