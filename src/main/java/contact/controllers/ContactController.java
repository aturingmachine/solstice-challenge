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
  
  private ContactService contactService;
  
  @Autowired
  public ContactController(ContactService contactService) {
    this.contactService = contactService;
  }

  /* -------------- GET ALL CONTACTS ----------------- */
  @RequestMapping(path = "", method = RequestMethod.GET)
  public Iterable<Contact> getAllContacts() {
    return contactService.getAllContacts();
  }

  /* -------------- CREATE A NEW CONTACT ----------------- */
  @RequestMapping(path = "", method = RequestMethod.POST)
  public ResponseEntity <?> addNewContact(@RequestBody Contact contact) {

    Contact createdContact = contactService.createContact(contact);
    if (createdContact == null) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    } else {
    return new ResponseEntity<Contact>(createdContact, HttpStatus.CREATED);
    }
  }

  /* -------------- GET ONE CONTACT ----------------- */
  @RequestMapping(path = "/{id}", method = RequestMethod.GET)
  public ResponseEntity <?> getContact( @PathVariable("id") Long id) {
    Contact contact = contactService.getContact(id);

    //Check If the contact is null and send a corresponding response
    if (contact != null) {
      return new ResponseEntity<Contact>(contact, HttpStatus.OK);
    } else {
      return new ResponseEntity<Contact>(HttpStatus.NOT_FOUND);
    }
  }

  /* -------------- UPDATE ONE CONTACT ----------------- */
  @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
  public ResponseEntity <?> updateContact( @PathVariable("id") Long id, @RequestBody Contact contact) {
    Contact updatedContact = contactService.updateContact(id, contact);
    
    return new ResponseEntity<Contact>(updatedContact, HttpStatus.OK);

  }

  /* -------------- DELETE ONE CONTACT ----------------- */
  @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
  public ResponseEntity <?> deleteContact (@PathVariable("id") Long id) {
    if(contactService.deleteContact(id)) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    
  }

  /* -------------- SEARCH CONTACTS ----------------- */
  @RequestMapping(path = "/search/{query}", method = RequestMethod.GET)
  public Iterable<Contact> searchContacts(@PathVariable("query")String query, @RequestParam("param") String param) {
    return contactService.searchContacts(query, param);
  }
}