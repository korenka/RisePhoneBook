package com.koren.phonebook_api.dto;

import org.springframework.lang.NonNull;

public class CreateContactDTO {

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String phone;

    @NonNull
    private String address;

    // Getters and setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
