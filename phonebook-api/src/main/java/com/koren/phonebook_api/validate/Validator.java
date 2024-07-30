package com.koren.phonebook_api.validate;

import com.koren.phonebook_api.dto.CreateContactDTO;
import com.koren.phonebook_api.exception.CustomException;
import com.koren.phonebook_api.exception.ErrorType;
import com.koren.phonebook_api.model.Contact;

public class Validator {
    //region static methods
    public static void vlaidateCreateContactDTO(CreateContactDTO createContactDTO) {
        if (createContactDTO.getFirstName() == null || createContactDTO.getFirstName().isEmpty() || !createContactDTO.getFirstName().matches("^[a-zA-Z]+$")) {
            throw new CustomException(ErrorType.VALIDATION_ERROR, "First name is mandatory");
        }
        if (createContactDTO.getLastName() == null || createContactDTO.getLastName().isEmpty() || !createContactDTO.getFirstName().matches("^[a-zA-Z]+$")) {
            throw new CustomException(ErrorType.VALIDATION_ERROR, "Last name is mandatory");
        }
        if (createContactDTO.getPhone() == null || !createContactDTO.getPhone().matches("\\d{10}")) {
            throw new CustomException(ErrorType.VALIDATION_ERROR, "Phone number must be exactly 10 digits");
        }
        if (createContactDTO.getAddress() == null || createContactDTO.getAddress().isEmpty()) {
            throw new CustomException(ErrorType.VALIDATION_ERROR, "Address is mandatory");
        }
    }

    public static void vlaidateContact(Contact contact) {
        if (contact.getFirstName() != null) {
            if (contact.getFirstName().isEmpty()) {
             throw new CustomException(ErrorType.VALIDATION_ERROR, "First name cannot be empty");
            }
            if (!contact.getFirstName().matches("^[a-zA-Z]+$")) {
             throw new CustomException(ErrorType.VALIDATION_ERROR, "First must only contain English letters");
            }
        }
        if (contact.getLastName() != null) {
            if (contact.getLastName().isEmpty()) {
             throw new CustomException(ErrorType.VALIDATION_ERROR, "Last name cannot be empty");
            }
            if (!contact.getFirstName().matches("^[a-zA-Z]+$")) {
             throw new CustomException(ErrorType.VALIDATION_ERROR, "Last must only contain English letters");
            }
        }
        if (contact.getPhone() != null) { 
            if (!contact.getPhone().matches("\\d{10}")) {
                throw new CustomException(ErrorType.VALIDATION_ERROR, "Phone number must be exactly 10 digits");
            }
        }
        if (contact.getAddress() != null) {
            if (contact.getAddress().isEmpty()) {
                throw new CustomException(ErrorType.VALIDATION_ERROR, "Address cannot be empty");
            }
        }
    }
    //endregion
}
