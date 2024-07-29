package com.koren.phonebook_api.service;

import com.koren.phonebook_api.exception.CustomException;
import com.koren.phonebook_api.exception.ErrorType;
import com.koren.phonebook_api.model.Contact;
import com.koren.phonebook_api.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public List<Contact> getContacts(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return contactRepository.findAll(pageable).getContent();
    }

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Optional<Contact> getContactById(Long id) {
        return contactRepository.findById(id);
    }

    public Contact addContact(Contact contact) {
        validateContact(contact);
        return contactRepository.save(contact);
    }

    public Optional<Contact> updateContact(Long id, Contact contactDetails) {
        return contactRepository.findById(id).map(contact -> {
            contact.setFirstName(contactDetails.getFirstName());
            contact.setLastName(contactDetails.getLastName());
            contact.setPhone(contactDetails.getPhone());
            contact.setAddress(contactDetails.getAddress());
            return contactRepository.save(contact);
        });
    }

    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }

    private void validateContact(Contact contact) {
        if (contact.getFirstName() == null || contact.getFirstName().isEmpty()) {
            throw new CustomException(ErrorType.VALIDATION_ERROR, "First name is mandatory");
        }
        if (contact.getLastName() == null || contact.getLastName().isEmpty()) {
            throw new CustomException(ErrorType.VALIDATION_ERROR, "Last name is mandatory");
        }
        if (contact.getPhone() == null || !contact.getPhone().matches("\\d{10}")) {
            throw new CustomException(ErrorType.VALIDATION_ERROR, "Phone number must be exactly 10 digits");
        }
        if (contact.getAddress() == null || contact.getAddress().isEmpty()) {
            throw new CustomException(ErrorType.VALIDATION_ERROR, "Address is mandatory");
        }
    }
}
