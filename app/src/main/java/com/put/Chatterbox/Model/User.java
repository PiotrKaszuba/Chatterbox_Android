package com.put.Chatterbox.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Piotr on 2018-03-15.
 */


@IgnoreExtraProperties
public class User implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(username);
        out.writeString(email);
        out.writeLong(timestamp);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    private User(Parcel in) {
        username = in.readString();
        email = in.readString();
        timestamp = in.readLong();
    }
}

