package com.koren.phonebook_api.service;

import com.koren.phonebook_api.dto.CreateContactDTO;
import com.koren.phonebook_api.exception.CustomException;
import com.koren.phonebook_api.exception.ErrorType;
import com.koren.phonebook_api.model.Contact;
import com.koren.phonebook_api.repository.ContactRepository;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class ContactService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactService.class);
    private static final String CONTACT_LOCK_KEY = "contactLock";

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private RedissonClient redissonClient;

    private List<Contact> getContactsWithPagination(int page, int size) {
        LOGGER.debug("Fetching contacts with pagination - Page: {}, Size: {}", page, size);
        PageRequest pageable = PageRequest.of(page, size);
        List<Contact> contacts = contactRepository.findAll(pageable).getContent();
        LOGGER.debug("Fetched {} contacts", contacts.size());
        return contacts;
    }

    public List<Contact> getAllContacts() {
        LOGGER.info("Fetching all contacts");
        int page = 0;
        int size = 10;

        List<Contact> allContacts = new ArrayList<>();
        List<Contact> contacts;

        do {
            contacts = getContactsWithPagination(page, size);
            allContacts.addAll(contacts);
            page++;
        } while (!contacts.isEmpty());

        LOGGER.info("Total contacts fetched: {}", allContacts.size());
        return allContacts;
    }

    public Optional<Contact> getContactById(Long id) {
        LOGGER.info("Fetching contact by ID: {}", id);
        Optional<Contact> contact = contactRepository.findById(id);
        if (contact.isPresent()) {
            LOGGER.info("Contact found: {}", contact.get());
        } else {
            LOGGER.warn("Contact with ID {} not found", id);
        }
        return contact;
    }

    public Contact addContact(CreateContactDTO createContactDTO) {
        LOGGER.info("Adding new contact: {}", createContactDTO);
        RLock lock = redissonClient.getLock(CONTACT_LOCK_KEY);
        lock.lock(10, TimeUnit.SECONDS); // Lock for 10 seconds
        LOGGER.debug("Acquired lock for adding contact");
        try {
            if (contactRepository.existsByPhone(createContactDTO.getPhone())) {
                LOGGER.warn("Phone number already exists: {}", createContactDTO.getPhone());
                throw new CustomException(ErrorType.VALIDATION_ERROR, "Phone number already exists");
            }

            Contact contact = new Contact();
            contact.setFirstName(createContactDTO.getFirstName());
            contact.setLastName(createContactDTO.getLastName());
            contact.setPhone(createContactDTO.getPhone());
            contact.setAddress(createContactDTO.getAddress());
            Contact savedContact = contactRepository.save(contact);
            LOGGER.info("Contact added successfully: {}", savedContact);
            return savedContact;
        } finally {
            lock.unlock();
            LOGGER.debug("Released lock after adding contact");
        }
    }

    public List<Contact> searchContacts(String firstName, String lastName, String phone) {
        LOGGER.info("Searching contacts - FirstName: {}, LastName: {}, Phone: {}", firstName, lastName, phone);
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

        List<Contact> contacts = contactRepository.findAll(spec);
        LOGGER.info("Found {} contacts", contacts.size());
        return contacts;
    }

    public Optional<Contact> updateContact(Long id, Contact contactDetails) {
        LOGGER.info("Updating contact with ID: {}", id);
        RLock lock = redissonClient.getLock(CONTACT_LOCK_KEY);
        lock.lock(10, TimeUnit.SECONDS); // Lock for 10 seconds
        LOGGER.debug("Acquired lock for updating contact");
        try {
            if (contactRepository.existsByPhoneAndIdNot(contactDetails.getPhone(), id)) {
                LOGGER.warn("Phone number already exists: {}", contactDetails.getPhone());
                throw new CustomException(ErrorType.VALIDATION_ERROR, "Phone number already exists");
            }

            Optional<Contact> updatedContact = contactRepository.findById(id).map(contact -> {
                contact.setFirstName(contactDetails.getFirstName());
                contact.setLastName(contactDetails.getLastName());
                contact.setPhone(contactDetails.getPhone());
                contact.setAddress(contactDetails.getAddress());
                Contact savedContact = contactRepository.save(contact);
                LOGGER.info("Contact updated successfully: {}", savedContact);
                return savedContact;
            });

            if (updatedContact.isEmpty()) {
                LOGGER.warn("Contact with ID {} not found for update", id);
            }
            return updatedContact;
        } finally {
            lock.unlock();
            LOGGER.debug("Released lock after updating contact");
        }
    }

    public void deleteContact(Long id) {
        LOGGER.info("Deleting contact with ID: {}", id);
        RLock lock = redissonClient.getLock(CONTACT_LOCK_KEY);
        lock.lock(10, TimeUnit.SECONDS); // Lock for 10 seconds
        LOGGER.debug("Acquired lock for deleting contact");
        try {
            contactRepository.deleteById(id);
            LOGGER.info("Contact with ID {} deleted successfully", id);
        } finally {
            lock.unlock();
            LOGGER.debug("Released lock after deleting contact");
        }
    }
}
