package contact.repositories;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

import contact.models.Contact;

// This will be AUTO IMPLEMENTED by Spring into a Bean called contactRepository
public interface ContactRepository extends CrudRepository<Contact, Long> {

  /**
   * These are custom search queries to be used in the ContactService.
   * They read just as they run so:
   * findByEmail(String email);
   * 
   * runs as:
   * select * from contacts where email=email;
   */
  List<Contact> findByEmail(String email);
  List<Contact> findByWorkPhoneOrPersonalPhone(String phone1, String phone2);
  List<Contact> findByState(String state);
  List<Contact> findByCity(String city);
}