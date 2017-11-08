package contact.controller.test;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import contact.controllers.ContactController;
import contact.models.Contact;
import contact.Application;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class ContactControllerTest {

  @InjectMocks
  ContactController controller;

  @Autowired
  WebApplicationContext context;

  private MockMvc mvc;

  public ObjectMapper mapper = new ObjectMapper();
  
  @Before
  public void initTests() {
    MockitoAnnotations.initMocks(this);
    mvc = MockMvcBuilders.webAppContextSetup(context).build();
  }

  @Test
  public void shouldCreateAndUpdateAndDelete() throws Exception {
    Contact contact = mockContact("shouldCreateAndUpdateAndDelete");
    byte[] contactJson = mapper.writeValueAsBytes(contact);

    //CREATE
    MvcResult result = mvc.perform(post("/api/contacts")
        .content(contactJson)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andReturn();
        long id = 1;

    //GET
    mvc.perform(get("/api/contacts/" + id)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is((int) id)))
        .andExpect(jsonPath("$.name", is(contact.getName())))
        .andExpect(jsonPath("$.company", is(contact.getCompany())))
        .andExpect(jsonPath("$.profileImage", is(contact.getProfileImage())))
        .andExpect(jsonPath("$.email", is(contact.getEmail())))
        .andExpect(jsonPath("$.birthdate", is(contact.getBirthdate())))
        .andExpect(jsonPath("$.workPhone", is(contact.getWorkPhone())))
        .andExpect(jsonPath("$.personalPhone", is(contact.getPersonalPhone())))
        .andExpect(jsonPath("$.address", is(contact.getAddress())))
        .andExpect(jsonPath("$.city", is(contact.getCity())))
        .andExpect(jsonPath("$.state", is(contact.getState())));

    //DELETE
    mvc.perform(delete("/api/contacts/" + id))
        .andExpect(status().isNoContent());

    //GET that should FAIL now
    mvc.perform(get("/api/contacts/" + id)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
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

    return c;

  }
}