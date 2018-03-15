package com.put.Chatterbox.Model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Piotr on 2018-03-15.
 */


@IgnoreExtraProperties
public class User {

    public String username;
    public String email;
    public Long timestamp;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email, Long timestamp) {
        this.username = username;
        this.email = email;
        this.timestamp = timestamp;
    }

}

