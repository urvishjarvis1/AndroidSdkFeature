package com.example.volansys.roomdatabase.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "pet",foreignKeys = @ForeignKey(entity = User.class,parentColumns = "userid",childColumns = "user_id" ,onDelete = CASCADE))
public class Pet {

    @ColumnInfo(name = "petname")
    String petName;
    @ColumnInfo(name ="pettype")
    String petType;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "user_id")
    String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }
}
