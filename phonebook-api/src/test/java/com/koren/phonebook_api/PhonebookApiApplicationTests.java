package com.koren.phonebook_api;

import com.koren.phonebook_api.dto.CreateContactDTO;
import com.koren.phonebook_api.model.Contact;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PhonebookApiApplicationTests {

    //region members
    @LocalServerPort
    private int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;
    private Long createdContactId;
    //endregion

    //region tests
    @Test
    void testAddContact() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + randomServerPort + "/api/contacts";
        URI uri = new URI(baseUrl);

        CreateContactDTO createContactDTO = TestUtils.createContactDTO("John", "Doe", TestUtils.generateUniquePhoneNumber(), "123 Main St");

        ResponseEntity<Contact> result = this.restTemplate.postForEntity(uri, createContactDTO, Contact.class);

        assertEquals(201, result.getStatusCodeValue());
        assertNotNull(result.getBody());

        // Store the created contact's ID for later use
        createdContactId = result.getBody().getId();
    }

    @Test
    void testGetAllContacts() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + randomServerPort + "/api/contacts/all";
        URI uri = new URI(baseUrl);

        ResponseEntity<Contact[]> result = this.restTemplate.getForEntity(uri, Contact[].class);

        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
    }

    @Test
    void testGetContactById() throws URISyntaxException {
        testAddContact();

        final String baseUrl = "http://localhost:" + randomServerPort + "/api/contacts/" + createdContactId;
        URI uri = new URI(baseUrl);

        ResponseEntity<Contact> result = this.restTemplate.getForEntity(uri, Contact.class);

        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
    }

    @Test
    void testSearchContacts() throws URISyntaxException {
        testAddContact();

        final String baseUrl = "http://localhost:" + randomServerPort + "/api/contacts/search?firstName=John&lastName=Doe&phone=1234567890";
        URI uri = new URI(baseUrl);

        ResponseEntity<Contact[]> result = this.restTemplate.getForEntity(uri, Contact[].class);

        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
    }

    @Test
    void testUpdateContact() throws URISyntaxException {
        testAddContact();

        final String baseUrl = "http://localhost:" + randomServerPort + "/api/contacts/" + createdContactId;
        URI uri = new URI(baseUrl);

        Contact contactDetails = TestUtils.createContact("John", "Doe", TestUtils.generateUniquePhoneNumber(), "123 Main St");

        HttpEntity<Contact> request = new HttpEntity<>(contactDetails);
        ResponseEntity<Contact> result = this.restTemplate.exchange(uri, HttpMethod.PUT, request, Contact.class);

        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
    }

    @Test
    void testDeleteContact() throws URISyntaxException {
        testAddContact();

        final String baseUrl = "http://localhost:" + randomServerPort + "/api/contacts/" + createdContactId;
        URI uri = new URI(baseUrl);

        ResponseEntity<Contact> result = this.restTemplate.exchange(uri, HttpMethod.DELETE, null, Contact.class);

        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
    }
    //endregion
}
