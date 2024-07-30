package com.koren.phonebook_api;

import com.koren.phonebook_api.dto.CreateContactDTO;
import com.koren.phonebook_api.exception.CustomException;
import com.koren.phonebook_api.exception.ErrorType;
import com.koren.phonebook_api.validate.Validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidatorTests {

    @Test
    void testValidateCreateContactDTO_NullFirstName() {
        CreateContactDTO dto = new CreateContactDTO();
        dto.setLastName("Doe");
        dto.setPhone("1234567890");
        dto.setAddress("123 Main St");

        CustomException exception = assertThrows(CustomException.class, () -> {
            Validator.vlaidateCreateContactDTO(dto);
        });

        assert exception.getErrorType() == ErrorType.VALIDATION_ERROR;
        assert exception.getMessage().equals("First name is mandatory");
    }

    @Test
    void testValidateCreateContactDTO_EmptyFirstName() {
        CreateContactDTO dto = new CreateContactDTO();
        dto.setFirstName("");
        dto.setLastName("Doe");
        dto.setPhone("1234567890");
        dto.setAddress("123 Main St");

        CustomException exception = assertThrows(CustomException.class, () -> {
            Validator.vlaidateCreateContactDTO(dto);
        });

        assert exception.getErrorType() == ErrorType.VALIDATION_ERROR;
        assert exception.getMessage().equals("First name is mandatory");
    }

    @Test
    void testValidateCreateContactDTO_InvalidFirstName() {
        CreateContactDTO dto = new CreateContactDTO();
        dto.setFirstName("John123");
        dto.setLastName("Doe");
        dto.setPhone("1234567890");
        dto.setAddress("123 Main St");

        CustomException exception = assertThrows(CustomException.class, () -> {
            Validator.vlaidateCreateContactDTO(dto);
        });

        assert exception.getErrorType() == ErrorType.VALIDATION_ERROR;
        assert exception.getMessage().equals("First name is mandatory");
    }

    @Test
    void testValidateCreateContactDTO_NullLastName() {
        CreateContactDTO dto = new CreateContactDTO();
        dto.setFirstName("John");
        dto.setPhone("1234567890");
        dto.setAddress("123 Main St");

        CustomException exception = assertThrows(CustomException.class, () -> {
            Validator.vlaidateCreateContactDTO(dto);
        });

        assert exception.getErrorType() == ErrorType.VALIDATION_ERROR;
        assert exception.getMessage().equals("Last name is mandatory");
    }

    @Test
    void testValidateCreateContactDTO_EmptyLastName() {
        CreateContactDTO dto = new CreateContactDTO();
        dto.setFirstName("John");
        dto.setLastName("");
        dto.setPhone("1234567890");
        dto.setAddress("123 Main St");

        CustomException exception = assertThrows(CustomException.class, () -> {
            Validator.vlaidateCreateContactDTO(dto);
        });

        assert exception.getErrorType() == ErrorType.VALIDATION_ERROR;
        assert exception.getMessage().equals("Last name is mandatory");
    }

    @Test
    void testValidateCreateContactDTO_InvalidLastName() {
        CreateContactDTO dto = new CreateContactDTO();
        dto.setFirstName("John");
        dto.setLastName("Doe123");
        dto.setPhone("1234567890");
        dto.setAddress("123 Main St");

        CustomException exception = assertThrows(CustomException.class, () -> {
            Validator.vlaidateCreateContactDTO(dto);
        });

        assert exception.getErrorType() == ErrorType.VALIDATION_ERROR;
        assert exception.getMessage().equals("Last name is mandatory");
    }

    @Test
    void testValidateCreateContactDTO_InvalidPhoneNumber() {
        CreateContactDTO dto = new CreateContactDTO();
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setPhone("12345678");
        dto.setAddress("123 Main St");

        CustomException exception = assertThrows(CustomException.class, () -> {
            Validator.vlaidateCreateContactDTO(dto);
        });

        assert exception.getErrorType() == ErrorType.VALIDATION_ERROR;
        assert exception.getMessage().equals("Phone number must be exactly 10 digits");
    }

    @Test
    void testValidateCreateContactDTO_NullAddress() {
        CreateContactDTO dto = new CreateContactDTO();
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setPhone("1234567890");

        CustomException exception = assertThrows(CustomException.class, () -> {
            Validator.vlaidateCreateContactDTO(dto);
        });

        assert exception.getErrorType() == ErrorType.VALIDATION_ERROR;
        assert exception.getMessage().equals("Address is mandatory");
    }

    @Test
    void testValidateCreateContactDTO_EmptyAddress() {
        CreateContactDTO dto = new CreateContactDTO();
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setPhone("1234567890");
        dto.setAddress("");

        CustomException exception = assertThrows(CustomException.class, () -> {
            Validator.vlaidateCreateContactDTO(dto);
        });

        assert exception.getErrorType() == ErrorType.VALIDATION_ERROR;
        assert exception.getMessage().equals("Address is mandatory");
    }
}
