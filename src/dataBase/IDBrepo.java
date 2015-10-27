package dataBase;

import model.Contact;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Marcin on 2015-10-22.
 */
public interface IDBrepo {
    void putContactToDB(Contact contact);
    ArrayList<Contact> getAllContactFromDB();
    void deleteContactFromDBwithID(Contact contact);
}
