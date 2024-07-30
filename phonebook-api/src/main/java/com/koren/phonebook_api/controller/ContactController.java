package com.koren.phonebook_api.controller;

import com.koren.phonebook_api.exception.CustomException;
import com.koren.phonebook_api.exception.ErrorType;
import com.koren.phonebook_api.model.Contact;
import com.koren.phonebook_api.service.ContactService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController extends BaseController {
    //region members
    private static final Logger LOGGER = LoggerFactory.getLogger(ContactController.class);
    
    @Autowired
    private ContactService contactService;
    //endregion

    //region endpoints
    @GetMapping("/all")
    public ResponseEntity<List<Contact>> getAllContacts() {
        LOGGER.debug("Fetching all contacts");
        List<Contact> contacts = contactService.getAllContacts();
        LOGGER.info("Found {} contacts: {}", contacts.size(), contacts);
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getContactById(@PathVariable Long id) {
        LOGGER.debug("Fetching contact by ID: {}", id);
        try {
            Contact contact = contactService.getContactById(id)
                    .orElseThrow(() -> {
                        LOGGER.warn("Contact with ID {} not found", id);
                        return new CustomException(ErrorType.CONTACT_NOT_FOUND, String.format("Contact with id %d not found", id));
                    });
            LOGGER.info("Found contact with {} ID: {}",id, contact);
            return new ResponseEntity<>(contact, HttpStatus.OK);
        } catch (CustomException e) {
            LOGGER.error("Error fetching contact by ID {}: {}", id, e.getMessage());
            return handleCustomException(e);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchContacts(@RequestParam(required = false) String firstName,
                                            @RequestParam(required = false) String lastName,
                                            @RequestParam(required = false) String phone) {
        LOGGER.debug("Searching contacts with firstName: {}, lastName: {}, phone: {}", firstName, lastName, phone);
        try {
            List<Contact> contacts = contactService.searchContacts(firstName, lastName, phone);
            LOGGER.info("Found {} contacts matching search criteria: {}", contacts.size(), contacts);
            return new ResponseEntity<>(contacts, HttpStatus.OK);
        } catch (CustomException e) {
            LOGGER.error("Error searching contacts: {}", e.getMessage());
            return handleCustomException(e);
        }
    }

    @PostMapping("/bulk")
    public ResponseEntity<?> addContactsBulk(@RequestBody @Valid List<Contact> contacts) {
        LOGGER.debug("Adding bulk contacts: {}", contacts);
        try {
            List<Contact> createdContacts = contactService.addContactsBulk(contacts);
            LOGGER.info("Contacts added successfully: {}", createdContacts);
            return new ResponseEntity<>(createdContacts, HttpStatus.CREATED);
        } catch (CustomException e) {
            LOGGER.error("Error adding contacts: {}", e.getMessage());
            return handleCustomException(e);
        }
    }

    @PostMapping
    public ResponseEntity<?> addContact(@RequestBody @Valid Contact contact) {
        LOGGER.debug("Adding new contact: {}", contact);
        try {
            Contact createdContact = contactService.addContact(contact);
            LOGGER.info("Contact added successfully: {}", createdContact);
            return new ResponseEntity<>(createdContact, HttpStatus.CREATED);
        } catch (CustomException e) {
            LOGGER.error("Error adding contact: {}", e.getMessage());
            return handleCustomException(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateContact(@PathVariable Long id, @RequestBody Contact contactDetails) {
        LOGGER.debug("Updating contact with ID: {}", id);
        try {
            Contact updatedContact = contactService.updateContact(id, contactDetails)
                .orElseThrow(() -> {
                    LOGGER.warn("Contact with ID {} not found", id);
                    return new CustomException(ErrorType.CONTACT_NOT_FOUND, String.format("Contact with id %d not found", id));
                });
            LOGGER.info("Contact updated successfully: {}", updatedContact);
            return new ResponseEntity<>(updatedContact, HttpStatus.OK);
        } catch (CustomException e) {
            LOGGER.error("Error updating contact: {}", e.getMessage());
            return handleCustomException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable Long id) {
        LOGGER.debug("Deleting contact with ID: {}", id);
        try {
            Contact contact = contactService.getContactById(id)
                    .orElseThrow(() -> {
                        LOGGER.warn("Contact with ID {} not found", id);
                        return new CustomException(ErrorType.CONTACT_NOT_FOUND, String.format("Contact with id %d not found", id));
                    });
            contactService.deleteContact(id);
            LOGGER.info("Contact deleted successfully: {}", contact);
            return ResponseEntity.ok(contact);
        } catch (CustomException e) {
            LOGGER.error("Error deleting contact: {}", e.getMessage());
            return handleCustomException(e);
        }
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<?> deleteContactsBulk(@RequestBody List<Long> ids) {
        LOGGER.debug("Deleting bulk contacts with IDs: {}", ids);
        try {
            List<Contact> deletedContacts = contactService.deleteContactsBulk(ids);
            LOGGER.info("Contacts deleted successfully for IDs: {}", ids);
            return ResponseEntity.ok(deletedContacts);
        } catch (CustomException e) {
            LOGGER.error("Error deleting contacts: {}", e.getMessage());
            return handleCustomException(e);
        }
    }
    //endregion
}
