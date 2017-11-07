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
@RequestMapping(path = "/api/contacts") //place everything behind /api/contacts for better readability
public class ContactController {
  
  private ContactService contactService;
  
  @Autowired
  public ContactController(ContactService contactService) {
    this.contactService = contactService;
  }

  /**
   * METHOD   GET
   * URI      /api/contacts
   * 
   * Will return an array of all contact resources
   * 
   * @return  Array of all contacts and 200 OK
   */
  @RequestMapping(path = "", method = RequestMethod.GET)
  public Iterable<Contact> getAllContacts() {
    return contactService.getAllContacts();
  }

  /**
   * METHOD   POST
   * URI      /api/contacts
   * 
   * Will create a new contact based on the body fo the request
   * 
   * @param contact The  contact object stored in the @RequestBody to be created
   * @return        An @ResponseEntity with the new contact and a 204, or a 409 if the email is in use
   */
  @RequestMapping(path = "", method = RequestMethod.POST)
  public ResponseEntity <?> addNewContact(@RequestBody Contact contact) {

    Contact createdContact = contactService.createContact(contact);
    if (createdContact == null) {
      return new ResponseEntity<>("Email " + contact.getEmail() + " is already in use.", HttpStatus.CONFLICT);
    } else {
    return new ResponseEntity<Contact>(createdContact, HttpStatus.CREATED);
    }
  }

  /**
   * METHOD  GET
   * URI     /api/contacts/{id}
   * 
   * Will return a single contact with a matching id
   * 
   * @param id The @PathVariable id of the contact in question
   * @return   @ResponseEntity with the Contact and a 200 OK, or 404 Not Found if the 
   *           contact cannot be found.
   */
  @RequestMapping(path = "/{id}", method = RequestMethod.GET)
  public ResponseEntity <?> getContact( @PathVariable("id") Long id) {
    Contact contact = contactService.getContact(id);

    //Check If the contact is null and send a corresponding response
    if (contact != null) {
      return new ResponseEntity<Contact>(contact, HttpStatus.OK);
    } else {
      return new ResponseEntity<>("Could Not Find User with id: " + id, HttpStatus.NOT_FOUND);
    }
  }

  /**
   * METHOD   PUT
   * URI      /api/contacts/{id}
   * 
   * Will update a single contact based on the payload of the request
   * 
   * @param id        The @PathVariable of the contact to be updated
   * @param contact   The @RequestBody containing the data. This will overwrite any and all fields in
   *                  the specific contact resource.
   * @return          An @ResponseEntity with the updated Contact
   */
  @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
  public ResponseEntity <?> updateContact( @PathVariable("id") Long id, @RequestBody Contact contact) {
    Contact updatedContact = contactService.updateContact(id, contact);
    if(updatedContact != null) {
      return new ResponseEntity<Contact>(updatedContact, HttpStatus.OK);
    } else {
      return new ResponseEntity<>("Email " + contact.getEmail() + " is in use.", HttpStatus.BAD_REQUEST);
    }

  }

  /**
   * METHOD  DELETE
   * URI     /api/contacts/{id}
   * 
   * Will delete a single contact based on the given id
   * 
   * @param id        The @PathVariable id of the conteact to delete
   * @return          An @ResponseEntity with status 204 No Content on success and 404 Not Found
   *                  if the resource cannot be located.
   */
  @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
  public ResponseEntity <?> deleteContact (@PathVariable("id") Long id) {
    if(contactService.deleteContact(id)) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>("Could Not Find User of ID:" + id, HttpStatus.NOT_FOUND);
    }

    
  }

  /**
   * METHOD  GET
   * URI     /api/contacts/{query}?param=
   * 
   * Will perform a search for a contact. The search will run over "query". Available searches are
   * "email", "phone", "city", "state". All of these searches return an array of Contacts matching the criteria.
   * Although email is unqiue and could not have more than one result, the following allows us to abstract this
   * into a single route.
   * 
   * @param query      The @PathVariable of what kind of search we will be running.
   * @param param      The @RequestParam of what to search for in the designated query.
   */
  @RequestMapping(path = "/search/{query}", method = RequestMethod.GET)
  public ResponseEntity<?> searchContacts(@PathVariable("query")String query, @RequestParam("value") String param) {
    Iterable<Contact> records = contactService.searchContacts(query, param);

    if(records != null) {
      return new ResponseEntity<>(records, HttpStatus.OK);
    } else {
      return new ResponseEntity<>("Search Query Not Supported", HttpStatus.BAD_REQUEST);
    }
  }
}