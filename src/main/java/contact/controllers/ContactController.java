package contact.controllers;

/* Imports from spring */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/* Importing our own services and exceptions */
import contact.models.Contact;
import contact.services.ContactService;

@RestController
@RequestMapping(path = "/api/contacts") //place everything behind /api for better readability
public class ContactController {
  @Autowired
  private ContactService contactService; //new up a contact service

  /* -------------- GET ALL CONTACTS ----------------- */
  @RequestMapping(path = "", method = RequestMethod.GET)
  public Iterable<Contact> getAllContacts() {
    return contactService.getAllContacts();
  }

  /* -------------- CREATE A NEW CONTACT ----------------- */
  @RequestMapping(path = "", method = RequestMethod.POST)
  public ResponseEntity <?> addNewContact(@RequestBody Contact contact) {

    Contact createdContact = contactService.createContact(contact);
    return new ResponseEntity<Contact>(createdContact, HttpStatus.CREATED);
  }

  /* -------------- GET ONE CONTACT ----------------- */
  @RequestMapping(path = "/{id}", method = RequestMethod.GET)
  public ResponseEntity <?> getContact( @PathVariable("id") Long id) {
    Contact contact = contactService.getContact(id);

    return new ResponseEntity<Contact>(contact, HttpStatus.OK);
  }

  /* -------------- UPDATE ONE CONTACT ----------------- */
  @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
  public ResponseEntity <?> updateContact( @PathVariable("id") Long id, @RequestBody Contact contact) {
    Contact updatedContact = contactService.updateContact(id, contact);
    
    return new ResponseEntity<Contact>(updatedContact, HttpStatus.OK);

  }

  /* -------------- UPDATE ONE CONTACT ----------------- */
  @RequestMapping(path = "/search/{query}", method = RequestMethod.GET)
  public Iterable<Contact> searchContacts(@PathVariable("query")String query, @RequestParam("query") String param) {
    return contactService.searchContacts(query, param);
  }
}