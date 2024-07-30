package com.koren.phonebook_api;

import com.koren.phonebook_api.model.Contact;
import com.koren.phonebook_api.service.ContactService;
import com.koren.phonebook_api.dto.CreateContactDTO;
import com.koren.phonebook_api.exception.CustomException;
import com.koren.phonebook_api.exception.ErrorType;
import com.koren.phonebook_api.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


public class ContactServiceTests {
    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactService contactService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddContact_whenDuplicatePhoneNumber_thenThrowCustomException() {
        when(contactRepository.existsByPhone(anyString())).thenReturn(true);

        CreateContactDTO createContactDTO = TestUtils.createContactDTO("John", "Doe", "1234567890", "123 Main St");

        CustomException exception = assertThrows(CustomException.class, () -> {
            contactService.addContact(createContactDTO);
        });

        assertEquals(ErrorType.VALIDATION_ERROR, exception.getErrorType());
        assertEquals("Phone number already exists", exception.getMessage());
    }

    @Test
    void testAddContact_whenUniquePhoneNumber_thenSuccess() {
        when(contactRepository.existsByPhone(anyString())).thenReturn(false);
        when(contactRepository.save(any(Contact.class))).thenReturn(TestUtils.createContact("John", "Doe", "1234567890", "123 Main St"));

        CreateContactDTO createContactDTO = TestUtils.createContactDTO("John", "Doe", "1234567890", "123 Main St");

        Contact savedContact = contactService.addContact(createContactDTO);

        assertNotNull(savedContact);
        assertEquals("John", savedContact.getFirstName());
        assertEquals("Doe", savedContact.getLastName());
        assertEquals("1234567890", savedContact.getPhone());
        assertEquals("123 Main St", savedContact.getAddress());
    }

    @Test
    void testUpdateContact_whenDuplicatePhoneNumber_thenThrowCustomException() {
        when(contactRepository.existsByPhoneAndIdNot(anyString(), anyLong())).thenReturn(true);

        Contact contactDetails = TestUtils.createContact("John", "Doe", "1234567890", "123 Main St");

        CustomException exception = assertThrows(CustomException.class, () -> {
            contactService.updateContact(1L, contactDetails);
        });

        assertEquals(ErrorType.VALIDATION_ERROR, exception.getErrorType());
        assertEquals("Phone number already exists", exception.getMessage());
    }

    @Test
    void testUpdateContact_whenUniquePhoneNumber_thenSuccess() {
        Contact existingContact = TestUtils.createContact("John", "Doe", "1234567890", "123 Main St");
        when(contactRepository.existsByPhoneAndIdNot(anyString(), anyLong())).thenReturn(false);
        when(contactRepository.findById(anyLong())).thenReturn(Optional.of(existingContact));
        when(contactRepository.save(any(Contact.class))).thenReturn(existingContact);

        Contact contactDetails = TestUtils.createContact("Jane", "Doe", "0987654321", "456 Elm St");

        Optional<Contact> updatedContact = contactService.updateContact(1L, contactDetails);

        assertTrue(updatedContact.isPresent());
        assertEquals("Jane", updatedContact.get().getFirstName());
        assertEquals("Doe", updatedContact.get().getLastName());
        assertEquals("0987654321", updatedContact.get().getPhone());
        assertEquals("456 Elm St", updatedContact.get().getAddress());
    }

    @Test
    void testDeleteContact_whenValidId_thenSuccess() {
        doNothing().when(contactRepository).deleteById(anyLong());

        contactService.deleteContact(1L);

        verify(contactRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetContactById_whenValidId_thenSuccess() {
        Contact contact = TestUtils.createContact("John", "Doe", "1234567890", "123 Main St");
        when(contactRepository.findById(anyLong())).thenReturn(Optional.of(contact));

        Optional<Contact> result = contactService.getContactById(1L);

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getFirstName());
        assertEquals("Doe", result.get().getLastName());
        assertEquals("1234567890", result.get().getPhone());
        assertEquals("123 Main St", result.get().getAddress());
    }

    @Test
    void testSearchContacts_whenCriteriaMatch_thenSuccess() {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(TestUtils.createContact("John", "Doe", "1234567890", "123 Main St"));
        when(contactRepository.findAll(any(Specification.class))).thenReturn(contacts);

        List<Contact> result = contactService.searchContacts("John", null, null);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Doe", result.get(0).getLastName());
        assertEquals("1234567890", result.get(0).getPhone());
        assertEquals("123 Main St", result.get(0).getAddress());
    }

    @Test
    void testGetAllContacts_whenCalled_thenSuccess() {
        List<Contact> contactsPage1 = new ArrayList<>();
        contactsPage1.add(TestUtils.createContact("John", "Doe", "1234567890", "123 Main St"));
        List<Contact> contactsPage2 = new ArrayList<>();
        contactsPage2.add(TestUtils.createContact("Jane", "Doe", "0987654321", "456 Elm St"));

        when(contactRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(contactsPage1)).thenReturn(new PageImpl<>(contactsPage2)).thenReturn(Page.empty());

        List<Contact> result = contactService.getAllContacts();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Jane", result.get(1).getFirstName());
    }
}
