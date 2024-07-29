package com.koren.phonebook_api.controller;

import com.koren.phonebook_api.dto.CreateContactDTO;
import com.koren.phonebook_api.exception.CustomException;
import com.koren.phonebook_api.exception.ErrorType;
import com.koren.phonebook_api.model.Contact;
import com.koren.phonebook_api.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController extends BaseController {
    @Autowired
    private ContactService contactService;

    @GetMapping
    public List<Contact> getContacts(@RequestParam int page, @RequestParam int size) {
        return contactService.getContacts(page, size);
    }

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

    @PostMapping
    public ResponseEntity<?> addContact(@Valid @RequestBody CreateContactDTO createContactDTO) {
        try {
            Contact contact = new Contact();
            contact.setFirstName(createContactDTO.getFirstName());
            contact.setLastName(createContactDTO.getLastName());
            contact.setPhone(createContactDTO.getPhone());
            contact.setAddress(createContactDTO.getAddress());

            Contact createdContact = contactService.addContact(contact);
            return ResponseEntity.ok(createdContact);
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
