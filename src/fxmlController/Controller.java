package fxmlController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.Contact;
import model.KsiazkaTelefoniczna;

import javax.swing.JOptionPane;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Created by Marcin on 2015-10-22.
 */
public class Controller implements Initializable {
    private static Logger logger = Logger.getLogger(Controller.class.getName());

    //"slabe" ladowanie modelu na sztywno. Brak konstr.
    private KsiazkaTelefoniczna model = KsiazkaTelefoniczna.getInstance();
    //View mam bezposrednio z fxmla.

    @FXML private Button btnRemoveAddress, btnAddAddress;   //nie uzywane
    @FXML private TextField edName, edSurName, edProvince, edAddress, edPostalCode, edCity;
    @FXML private ListView<Contact> listViewContacts;
    @FXML private Text textNr;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("Initialize");
        /**
         * Ladowanie danych do ListView
         */
        ArrayList<Contact> contacts = new ArrayList<>(model.getAllContactFromDB());
        ObservableList<Contact> data = FXCollections.observableArrayList(contacts);
        listViewContacts.setItems(data);

            if (!contacts.isEmpty()) {
                textNr.setText(String.valueOf(contacts.size()));
            } else {
                textNr.setText(String.valueOf(0));
            }

        listViewContacts.setCellFactory(new Callback<ListView<Contact>, ListCell<Contact>>() {
            @Override
            public ListCell<Contact> call(ListView<Contact> param) {
                ListCell<Contact> cell = new ListCell<Contact>() {
                    @Override
                    protected void updateItem(Contact item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(String.valueOf(contacts.indexOf(item)+1) + ". " + item.getName() + " " + item.getSurname() + ", "
                                    + item.getProvince() + ", " + item.getAddress() + ", "
                                    + item.getPostalCode() + ", " + item.getCity());
                        }
                    }
                };
                return cell;
            }
        });
        //endregion

    }

    /**
     * Metoda w fxml ktora obsluguje btnRemoveAddress.
     */
    @FXML protected void removeAddressFromAB() {
        logger.info("proba buttona remove");
        final int selectedIdx = listViewContacts.getSelectionModel().getSelectedIndex();
        if (selectedIdx != -1) {
            Contact contactToRemove = listViewContacts.getSelectionModel().getSelectedItem();
            final int newSelectedIdx = (selectedIdx == listViewContacts.getItems().size() - 1) ? selectedIdx - 1 : selectedIdx;

            //usun z BD:
            model.deleteContactFromDBwithID(contactToRemove);
            //usun z widoku:
            //listViewContacts.getItems().remove(selectedIdx);
            logger.info("Pousuwane");
            listViewContacts.getSelectionModel().select(newSelectedIdx);
        }
        initialize(null, null); //nie wiem czy tak mozna? :D

    }

    /**
     * Metoda w fxml ktora obsluguje btnAddAddress.
     */
    @FXML public void addAddressToAB() {
        logger.info("proba buttona add");
        ArrayList<String> atributes = new ArrayList<>();
        atributes.add(edName.getText());
        atributes.add(edSurName.getText());
        atributes.add(edProvince.getText());
        atributes.add(edAddress.getText());
        atributes.add(edPostalCode.getText());
        atributes.add(edCity.getText());
        logger.info(atributes.toString());
        Contact contact;
        if(atributes.size() != 0) { //nie wypelnione ZADNE pole.
            for (String string : atributes) {   //sprawdzam czy wypelnione WSZYSTKIE pola
                if (string.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all sections");
                    return;
                }
            }
            contact = new Contact(atributes.get(0), atributes.get(1),atributes.get(2),atributes.get(3),atributes.get(4),atributes.get(5));
            logger.info("Kontakt: " + contact.toString());
            model.putContactToDB(contact);  //BD
            //listViewContacts.getItems().add(contact);   //widok
        } else {
            JOptionPane.showMessageDialog(null, "Please fill all sections");
            return;
        }
        initialize(null, null); //nie wiem czy tak mozna? :D
    }

}
