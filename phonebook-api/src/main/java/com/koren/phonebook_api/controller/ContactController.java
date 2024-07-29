package com.koren.phonebook_api.controller;

import com.koren.phonebook_api.dto.CreateContactDTO;
import com.koren.phonebook_api.exception.CustomException;
import com.koren.phonebook_api.exception.ErrorType;
import com.koren.phonebook_api.model.Contact;
import com.koren.phonebook_api.model.ErrorResponse;
import com.koren.phonebook_api.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController extends BaseController {
    @Autowired
    private ContactService contactService;
    
    //TODO: remove once app is complete
    @GetMapping("/all")
    public List<Contact> getAllContacts() {
        return contactService.getAllContacts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getContactById(@PathVariable Long id) {
        try {
            Contact contact = contactService.getContactById(id)
                    .orElseThrow(() -> new CustomException(ErrorType.CONTACT_NOT_FOUND, String.format("Contact with id %d not found", id)));
            return ResponseEntity.ok(contact);
        } catch (CustomException e) {
            return handleCustomException(e);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchContacts(@RequestParam(required = false) String firstName,
                                            @RequestParam(required = false) String lastName,
                                            @RequestParam(required = false) String phone) {
        try {
            List<Contact> contacts = contactService.searchContacts(firstName, lastName, phone);
            return new ResponseEntity<>(contacts, HttpStatus.OK);
        } catch (CustomException e) {
            return handleCustomException(e);
        }
    }

    @PostMapping
    public ResponseEntity<?> addContact(@RequestBody @Valid CreateContactDTO createContactDTO) {
        try {
            Contact contact = contactService.addContact(createContactDTO);
            return new ResponseEntity<>(contact, HttpStatus.CREATED);
        } catch (CustomException e) {
            return handleCustomException(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateContact(@PathVariable Long id, @RequestBody Contact contactDetails) {
        try {
            Contact updatedContact = contactService.updateContact(id, contactDetails)
                .orElseThrow(() -> new CustomException(ErrorType.CONTACT_NOT_FOUND, String.format("Contact with id %d not found", id)));
            return ResponseEntity.ok(updatedContact);
        } catch (CustomException e) {
            return handleCustomException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
}
