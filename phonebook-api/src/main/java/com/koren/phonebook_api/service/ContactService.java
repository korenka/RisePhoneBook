package com.koren.phonebook_api.service;

import com.koren.phonebook_api.model.Contact;
import com.koren.phonebook_api.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    public List<Contact> getContacts(int page, int size) {
        return contactRepository.findAll(page, size);
    }

    public List<Contact> getAllContacts() {
        int page = 0;
        int size = 10;
        List<Contact> allContacts = new ArrayList<>();
        List<Contact> contactPage;

        do {
            contactPage = getContacts(page, size);
            allContacts.addAll(contactPage);
            page++;
        } while (contactPage.size() == size);

        return allContacts;
    }

    public Optional<Contact> getContactById(Long id) {
        return contactRepository.findById(id);
    }

    public Contact addContact(Contact contact) {
        contactRepository.save(contact);
        return contact;
    }

    public Optional<Contact> updateContact(Long id, Contact contactDetails) {
        return contactRepository.findById(id).map(contact -> {
            contact.setFirstName(contactDetails.getFirstName());
            contact.setLastName(contactDetails.getLastName());
            contact.setPhone(contactDetails.getPhone());
            contact.setAddress(contactDetails.getAddress());
            contactRepository.update(id, contact);
            return contact;
        });
    }

    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }
}
