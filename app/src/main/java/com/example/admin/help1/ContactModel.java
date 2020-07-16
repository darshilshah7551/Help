package com.example.admin.help1;

public class ContactModel {

    private String name, number;

    ContactModel(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}