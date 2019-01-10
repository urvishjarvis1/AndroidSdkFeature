package com.example.volansys.roomdatabase.model;

import android.arch.persistence.room.Embedded;

public class UserAndPet {
    @Embedded
    Pet pet;
    @Embedded
    User user;

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
