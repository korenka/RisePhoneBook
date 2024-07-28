package com.koren.phonebook_api;

import com.koren.phonebook_api.model.Contact;
import com.koren.phonebook_api.service.ContactService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PhonebookApiApplicationTests {

    @LocalServerPort
    private int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ContactService contactService;

    @Test
    void contextLoads() {
    }

    @Test
    void testAddContact() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + randomServerPort + "/api/contacts";
        URI uri = new URI(baseUrl);

        Contact contact = new Contact();
        contact.setFirstName("John");
        contact.setLastName("Doe");
        contact.setPhone("1234567890");
        contact.setAddress("123 Main St");

        ResponseEntity<Contact> result = this.restTemplate.postForEntity(uri, contact, Contact.class);

        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        assertNotNull(result.getBody().getId());
    }

    @Test
    void testGetAllContacts() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + randomServerPort + "/api/contacts/all";
        URI uri = new URI(baseUrl);

        ResponseEntity<Contact[]> result = this.restTemplate.getForEntity(uri, Contact[].class);

        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
    }
}
