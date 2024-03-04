package com.example.serialtest.serial;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.twitter.serial.serializer.ObjectSerializer;
import com.twitter.serial.serializer.SerializationContext;
import com.twitter.serial.stream.SerializerInput;
import com.twitter.serial.stream.SerializerOutput;

import java.io.IOException;

public class User implements Parcelable {
    public static final ObjectSerializer<User> SERIALIZER = new UserObjectSerializer();
    private String name;
    private String uuid;

    public User(String name, String uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    private static final class UserObjectSerializer extends ObjectSerializer<User> {
        @Override
        protected void serializeObject(@NonNull SerializationContext context,
                                       @NonNull SerializerOutput output,
                                       @NonNull User object) throws IOException {
            output.writeString(object.name);
            output.writeString(object.uuid);
        }

        @Nullable
        @Override
        protected User deserializeObject(@NonNull SerializationContext context,
                                         @NonNull SerializerInput input,
                                         int versionNumber) throws IOException, ClassNotFoundException {
            final String name = input.readString();
            final String uuid = input.readString();
            return new User(name,uuid);
        }
    }

    protected User(Parcel in) {
        name = in.readString();
        uuid = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(uuid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
