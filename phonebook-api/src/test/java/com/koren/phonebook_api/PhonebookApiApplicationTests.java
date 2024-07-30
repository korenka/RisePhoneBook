package com.koren.phonebook_api;

import com.koren.phonebook_api.dto.CreateContactDTO;
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

    @Test
    void contextLoads() {
    }

    @Test
    void testAddContact() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + randomServerPort + "/api/contacts";
        URI uri = new URI(baseUrl);

        CreateContactDTO createContactDTO = new CreateContactDTO();
        createContactDTO.setFirstName("John");
        createContactDTO.setLastName("Doe");
        createContactDTO.setPhone("1234567890");
        createContactDTO.setAddress("123 Main St");

        ResponseEntity<Contact> result = this.restTemplate.postForEntity(uri, createContactDTO, Contact.class);

        assertEquals(201, result.getStatusCodeValue());
        assertNotNull(result.getBody());
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
