package com.koren.phonebook_api;

import java.util.Random;
import com.koren.phonebook_api.model.Contact;

public class TestUtils {
    //region static methods
    public static Contact createContact(String firstName, String lastName, String phone, String address) {
        Contact contactDetails = new Contact();
        contactDetails.setFirstName(firstName);
        contactDetails.setLastName(lastName);
        contactDetails.setPhone(phone);
        contactDetails.setAddress(address);
        return contactDetails;
    }

    public static String generateUniquePhoneNumber() {
        Random random = new Random();
        StringBuilder phoneNumber = new StringBuilder("1");
        for (int i = 0; i < 9; i++) {
            phoneNumber.append(random.nextInt(10));
        }
        return phoneNumber.toString();
    }
    //endregion
}
