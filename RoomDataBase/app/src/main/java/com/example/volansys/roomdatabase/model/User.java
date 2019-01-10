package com.example.volansys.roomdatabase.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(indices = {@Index(value = "name",unique = true),@Index(value = "userid",unique = true)}, tableName = "users")
public class User {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name="userid")
    private String uId;

    @ColumnInfo(name = "date")
    private Date date;

    @ColumnInfo(name="name")

    private String userName;

    public User(@NonNull String uId, String userName, Date date) {
        this.uId = uId;
        this.userName = userName;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUId() {
        return uId;
    }

    public void setUId(String uId) {
        this.uId = uId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User() {
    }
}
