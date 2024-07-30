package com.koren.phonebook_api.validate;

import com.koren.phonebook_api.exception.CustomException;
import com.koren.phonebook_api.exception.ErrorType;
import com.koren.phonebook_api.model.Contact;

public class Validator {
    //region static methods
    public static void vlaidateCreateContact(Contact contact) {
        if (contact.getFirstName() == null || contact.getFirstName().isEmpty() || !contact.getFirstName().matches("^[a-zA-Z]+$")) {
            throw new CustomException(ErrorType.VALIDATION_ERROR, "First name is mandatory");
        }
        if (contact.getLastName() == null || contact.getLastName().isEmpty() || !contact.getLastName().matches("^[a-zA-Z]+$")) {
            throw new CustomException(ErrorType.VALIDATION_ERROR, "Last name is mandatory");
        }
        if (contact.getPhone() == null || !contact.getPhone().matches("\\d{10}")) {
            throw new CustomException(ErrorType.VALIDATION_ERROR, "Phone number must be exactly 10 digits");
        }
        if (contact.getAddress() == null || contact.getAddress().isEmpty()) {
            throw new CustomException(ErrorType.VALIDATION_ERROR, "Address is mandatory");
        }
    }

    public static void vlaidateUpdateContact(Contact contact) {
        if (contact.getFirstName() != null) {
            if (contact.getFirstName().isEmpty()) {
             throw new CustomException(ErrorType.VALIDATION_ERROR, "First name cannot be empty");
            }
            if (!contact.getFirstName().matches("^[a-zA-Z]+$")) {
             throw new CustomException(ErrorType.VALIDATION_ERROR, "First name must only contain English letters");
            }
        }
        if (contact.getLastName() != null) {
            if (contact.getLastName().isEmpty()) {
             throw new CustomException(ErrorType.VALIDATION_ERROR, "Last name cannot be empty");
            }
            if (!contact.getLastName().matches("^[a-zA-Z]+$")) {
             throw new CustomException(ErrorType.VALIDATION_ERROR, "Last name must only contain English letters");
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
