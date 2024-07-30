package com.koren.phonebook_api;

import com.koren.phonebook_api.exception.CustomException;
import com.koren.phonebook_api.exception.ErrorType;
import com.koren.phonebook_api.model.Contact;
import com.koren.phonebook_api.validate.Validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidatorTests {
    //region tests
    @Test
    void testValidateCreateContact_NullFirstName() {
        Contact contact = TestUtils.createContact(null, "Doe", "1234567890", "123 Main St");

        CustomException exception = assertThrows(CustomException.class, () -> {
            Validator.vlaidateCreateContact(contact);
        });

        assert exception.getErrorType() == ErrorType.VALIDATION_ERROR;
        assert exception.getMessage().equals("First name is mandatory");
    }

    @Test
    void testValidateCreateContact_EmptyFirstName() {
        Contact contact = TestUtils.createContact("", "Doe", "1234567890", "123 Main St");
        contact.setFirstName("");
        contact.setLastName("Doe");
        contact.setPhone("1234567890");
        contact.setAddress("123 Main St");

        CustomException exception = assertThrows(CustomException.class, () -> {
            Validator.vlaidateCreateContact(contact);
        });

        assert exception.getErrorType() == ErrorType.VALIDATION_ERROR;
        assert exception.getMessage().equals("First name is mandatory");
    }

    @Test
    void testValidateCreateContact_InvalidFirstName() {
        Contact contact = TestUtils.createContact("John123", "Doe", "1234567890", "123 Main St");

        CustomException exception = assertThrows(CustomException.class, () -> {
            Validator.vlaidateCreateContact(contact);
        });

        assert exception.getErrorType() == ErrorType.VALIDATION_ERROR;
        assert exception.getMessage().equals("First name is mandatory");
    }

    @Test
    void testValidateCreateContact_NullLastName() {
        Contact contact = TestUtils.createContact("John", null, "1234567890", "123 Main St");
        contact.setFirstName("John");
        contact.setPhone("1234567890");
        contact.setAddress("123 Main St");

        CustomException exception = assertThrows(CustomException.class, () -> {
            Validator.vlaidateCreateContact(contact);
        });

        assert exception.getErrorType() == ErrorType.VALIDATION_ERROR;
        assert exception.getMessage().equals("Last name is mandatory");
    }

    @Test
    void testValidateCreateContact_EmptyLastName() {
        Contact contact = TestUtils.createContact("John", "", "1234567890", "123 Main St");

        CustomException exception = assertThrows(CustomException.class, () -> {
            Validator.vlaidateCreateContact(contact);
        });

        assert exception.getErrorType() == ErrorType.VALIDATION_ERROR;
        assert exception.getMessage().equals("Last name is mandatory");
    }

    @Test
    void testValidateCreateContact_InvalidLastName() {
        Contact contact = TestUtils.createContact("John", "Doe123", "1234567890", "123 Main St");

        CustomException exception = assertThrows(CustomException.class, () -> {
            Validator.vlaidateCreateContact(contact);
        });

        assert exception.getErrorType() == ErrorType.VALIDATION_ERROR;
        assert exception.getMessage().equals("Last name is mandatory");
    }

    @Test
    void testValidateCreateContact_InvalidPhoneNumber() {
        Contact contact = TestUtils.createContact("John", "Doe", "12345678", "123 Main St");

        CustomException exception = assertThrows(CustomException.class, () -> {
            Validator.vlaidateCreateContact(contact);
        });

        assert exception.getErrorType() == ErrorType.VALIDATION_ERROR;
        assert exception.getMessage().equals("Phone number must be exactly 10 digits");
    }

    @Test
    void testValidateCreateContact_NullAddress() {
        Contact contact = TestUtils.createContact("John", "Doe", "1234567890", null);

        CustomException exception = assertThrows(CustomException.class, () -> {
            Validator.vlaidateCreateContact(contact);
        });

        assert exception.getErrorType() == ErrorType.VALIDATION_ERROR;
        assert exception.getMessage().equals("Address is mandatory");
    }

    @Test
    void testValidateCreateContact_EmptyAddress() {
        Contact contact = TestUtils.createContact("John", "Doe", "1234567890", "");

        CustomException exception = assertThrows(CustomException.class, () -> {
            Validator.vlaidateCreateContact(contact);
        });

        assert exception.getErrorType() == ErrorType.VALIDATION_ERROR;
        assert exception.getMessage().equals("Address is mandatory");
    }
    //endregion
}
