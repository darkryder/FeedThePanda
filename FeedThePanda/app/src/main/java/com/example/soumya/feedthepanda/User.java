package com.example.soumya.feedthepanda;

import android.provider.ContactsContract;

/**
 * Created by Soumya on 29-11-2015.
 */
public class User {

    private String name;
    private String email;

    public User(String name) {
        this(name, null);
    }
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
