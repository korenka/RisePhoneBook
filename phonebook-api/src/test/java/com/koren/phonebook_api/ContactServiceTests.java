package com.koren.phonebook_api;

import com.koren.phonebook_api.dto.CreateContactDTO;
import com.koren.phonebook_api.exception.CustomException;
import com.koren.phonebook_api.exception.ErrorType;
import com.koren.phonebook_api.model.Contact;
import com.koren.phonebook_api.repository.ContactRepository;
import com.koren.phonebook_api.service.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

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
    void testAddContact_DuplicatePhoneNumber() {
        when(contactRepository.existsByPhone(anyString())).thenReturn(true);

        CreateContactDTO createContactDTO = TestUtils.createContactDTO("John", "Doe", "1234567890", "123 Main St");

        CustomException exception = assertThrows(CustomException.class, () -> {
            contactService.addContact(createContactDTO);
        });

        assert exception.getErrorType() == ErrorType.VALIDATION_ERROR;
        assert exception.getMessage().equals("Phone number already exists");
    }

    @Test
    void testUpdateContact_DuplicatePhoneNumber() {
        when(contactRepository.existsByPhoneAndIdNot(anyString(), anyLong())).thenReturn(true);

        Contact contactDetails = TestUtils.createContact("John", "Doe", "1234567890", "123 Main St");

        CustomException exception = assertThrows(CustomException.class, () -> {
            contactService.updateContact(1L, contactDetails);
        });

        assert exception.getErrorType() == ErrorType.VALIDATION_ERROR;
        assert exception.getMessage().equals("Phone number already exists");
    }
}
