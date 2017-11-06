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

  @Autowired
  private ContactRepository contactRepository;

  public ContactService() {

  }

  //Create a new Contact resource
  public Contact createContact(Contact contact) {
    Contact contactToSave = new Contact();
    contactToSave = contact;
    contactToSave.setProfileImage(Base64.Encoder(contact.getProfileImage());
    return contactRepository.save(contactToSave);
  }

  //Return a single contact resource
  public Contact getContact(Long id) {
    return contactRepository.findOne(id);
  }

  //Update a contact resource
  public Contact updateContact(Long id, Contact contact) {
    Contact contactToUpdate = new Contact();
    contactToUpdate = contact;
    contactToUpdate.setId(id);
    return contactRepository.save(contactToUpdate);
  }

  //Delete a single contact resource
  public void deleteContact(Long id) {
    contactRepository.delete(id);
  }

  //Return all Contact resources
  public Iterable<Contact> getAllContacts() {
    return contactRepository.findAll();
  }

  //Search
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