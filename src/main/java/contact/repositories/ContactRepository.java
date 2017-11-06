package contact.repositories;

import org.springframework.data.repository.CrudRepository;

import contact.models.Contact;

// This will be AUTO IMPLEMENTED by Spring into a Bean called contactRepository
public interface ContactRepository extends CrudRepository<Contact, Long> {

}