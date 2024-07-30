package com.koren.phonebook_api.service;

import com.koren.phonebook_api.dto.CreateContactDTO;
import com.koren.phonebook_api.exception.CustomException;
import com.koren.phonebook_api.exception.ErrorType;
import com.koren.phonebook_api.model.Contact;
import com.koren.phonebook_api.repository.ContactRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    
    //region members
    @Autowired
    private ContactRepository contactRepository;
    //endregion

    //region private methods
    private List<Contact> getContactsWithPagination(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return contactRepository.findAll(pageable).getContent();
    }
    //endregion

    //region public methods
    public List<Contact> getAllContacts() {
        int page = 0;
        int size = 10;

        List<Contact> allContacts = new ArrayList<>();
        List<Contact> contacts;

        do {
            contacts = getContactsWithPagination(page, size);
            allContacts.addAll(contacts);
            page++;
        } while (!contacts.isEmpty());

        return allContacts;
    }

    public Optional<Contact> getContactById(Long id) {
        return contactRepository.findById(id);
    }
    public Contact addContact(CreateContactDTO createContactDTO) {
        if (contactRepository.existsByPhone(createContactDTO.getPhone())) {
            throw new CustomException(ErrorType.VALIDATION_ERROR, "Phone number already exists");
        }

        Contact contact = new Contact();
        contact.setFirstName(createContactDTO.getFirstName());
        contact.setLastName(createContactDTO.getLastName());
        contact.setPhone(createContactDTO.getPhone());
        contact.setAddress(createContactDTO.getAddress());
        return contactRepository.save(contact);
    }

    public List<Contact> searchContacts(String firstName, String lastName, String phone) {
        Specification<Contact> spec = Specification.where(null);

        if (firstName != null && !firstName.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> 
                criteriaBuilder.equal(root.get("firstName"), firstName));
        }

        if (lastName != null && !lastName.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> 
                criteriaBuilder.equal(root.get("lastName"), lastName));
        }

        if (phone != null && !phone.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> 
                criteriaBuilder.equal(root.get("phone"), phone));
        }

        return contactRepository.findAll(spec);
    }

    public Optional<Contact> updateContact(Long id, Contact contactDetails) {
        if (contactRepository.existsByPhoneAndIdNot(contactDetails.getPhone(), id)) {
            throw new CustomException(ErrorType.VALIDATION_ERROR, "Phone number already exists");
        }

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
    //endregion
}
