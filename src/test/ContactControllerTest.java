package test;

import java.awt.PageAttributes.MediaType;

import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import contact.controllers.ContactController;
import contact.models.Contact;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class ContactControllerTest {

  @InjectMocks
  ContactController controller;

  private MockMvc mvc;
  
  @Before
  public void initTests() {
    MockitoAnnotations.initMocks(this);
    mvc = MockMvcBuilders.webAppContextSetup(context).build();
  }

  @Test
  public void shouldCreateAndUpdateAndDelete() throws Exception {
    Contact contact = mockContact("shouldCreateAndUpdateAndDelete");
    byte[] contactJson = toJson(contact);

    //CREATE
    MvcResult result = mvc.perform(post("/api/contacts")
        .content(contactJson)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andReturn();
    long id = 1;

    //GET
    mvc.perform(get("/api/contacts/1")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is((int) id)))
        .andExpect(jsonPath("$.name", is(contact.getName())))
        .andExpect(jsonPath("$.company", is(contact.getCompany())))
        .andExpect(jsonPath("$.profileImage", is(contact.getProfileImage()))
        .andExpect(jsonPath("$.email", is(contact.getEmail()))))
        .andExpect(jsonPath("$.birthdate", is(contact.getBirthdate())))
        .andExpect(jsonPath("$.workPhone", is(contact.getWorkPhone())))
        .andExpect(jsonPath("$.personalPhone", is(contact.getPersonalPhone())))
        .andExpect(jsonPath("$.address", is(contact.getAddress())))
        .andExpect(jsonPath("$.city", is(contact.getCity())))
        .andExpect(jsonPath("$.state", is(contact.getState())));

    //DELETE
    mvc.perform(delete("/api/contacts/1")
        .andExpect(status().isNoContent()));

    //GET that should FAIL now
    mvc.perform(get("/api/contacts/1")
        .accept(MediaType.APPLICATION_JSON)
        .andExpect(status().isNotFound()));
  }

  private Contact mockContact(String prefix) {
    Contact c = new Contact();
    c.setName(prefix + "_name");
    c.setCompany(prefix + "_company");
    c.setProfileImage(prefix + "_profileImage");
    c.setEmail(prefix + "_email");
    c.setBirthdate(prefix + "_birthdate");
    c.setWorkPhone(prefix + "_workPhone");
    c.setPersonalPhone(prefix + "_personalPhone");
    c.setAddress(prefix + "_address");
    c.setCity(prefix + "_city");
    c.setState(prefix + "_state");

  }
}