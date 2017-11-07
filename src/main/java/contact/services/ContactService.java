package contact.services;


import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import contact.models.Contact;
import contact.repositories.ContactRepository;

/*
 * Service to be used by the Contact Controller
 * Executes simple CRUD operations
 */
@Service
public class ContactService {

  private ContactRepository contactRepository;

  @Autowired
  public ContactService(ContactRepository contactRepository) {
    this.contactRepository = contactRepository;
  }

  /**
   * Creates a new Contact Resource based on the payload of a PUT request
   * Also runs a check to see if the UNIQUE field "email" is already in use,
   * Without the check the server will return a 500 which is in my opinion 
   * the improper response.
   * 
   * @param  contact The Contact resource to be created
   * @return         The created contact resource
   */
  public Contact createContact(Contact contact) {

    //simple check to see if the contact email exists
    if(contactRepository.findByEmail(contact.getEmail()).isEmpty()) {
      return contactRepository.save(contact);
    } else {
      return null;
    }
  }

  /**
   * Will return a single contact matching the give ID
   * If no contact existst the returned object will be null
   * 
   * @param id  The ID of the contact in question
   * @return    The contact object or null
   */
  public Contact getContact(Long id) {
    return contactRepository.findOne(id);
  }

  /**
   * Updates a single contact. A new contact is set up with the data
   * and its id set to that of the contact to update. JPA then realizes
   * this is an update and covers that for us.
   * 
   * @param id      The ID of the contact to be updated.
   * @param contact The new contact data.
   * @return        The updated contact resource.
   */
  public Contact updateContact(Long id, Contact contact) {
    
    //LOGIC:  If the ID exists save over old one
    //ELSEIF: The Email Doesnt exist make a new records
    //ELSE:   The provided data is not an update and not a valid new contact record 
    if(contactRepository.exists(id)) {
      contact.setId(id);
      return contactRepository.save(contact);
    } else {
      return this.createContact(contact);
    }
    
  }

  /**
   * Deletes a single contact based on the given id. If no contact exists by that id
   * the return value is false, to be handled by the controller.
   * 
   * @param id  The id of the contact to delete
   * @return    Boolean of whether or the delete occured.
   */
  public boolean deleteContact(Long id) {
    if (contactRepository.exists(id)) {
      contactRepository.delete(id);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Returns an array of all contact objects
   * 
   * @return    All contact resources.
   */
  public Iterable<Contact> getAllContacts() {
    return contactRepository.findAll();
  }

  /**
   * Will search for a contact by email, phone, state, or city
   * In the event of a state or city search the result may be an array,
   * hence the Iterable<Contact> return type.
   * 
   * Example: searchContacts("email", "example@gmail.com");
   * Will return contacts with the email "example@gmail.com"
   * 
   * @param query The type of search we will be doing
   * @param param The search parameter for the specific query
   * @return      A list of contacts with param matching to the query type
   */
  public Iterable<Contact> searchContacts(String query, String param) {
    switch (query) {
      case "email":
        System.out.println(param);
        return contactRepository.findByEmail(param);
      case "phone":
        return contactRepository.findByWorkPhoneOrPersonalPhone(param, param);
      case "state":
        return contactRepository.findByState(param);
      case "city":
        return contactRepository.findByCity(param);
    }

    return null;
  }
}