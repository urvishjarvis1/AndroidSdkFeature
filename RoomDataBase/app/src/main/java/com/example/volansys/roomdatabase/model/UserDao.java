package com.example.volansys.roomdatabase.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;
@Dao
public interface UserDao {
    @Query("Select * from users")
    List<User> getUsers();
    @Query("SELECT * FROM users WHERE userid IN(:uid)")
    User getUserById(String uid);
    @Query("SELECT * FROM USERS WHERE name LIKE :name")
    List<User> getUserByName(String name);
    @Query("SELECT name from users")
    List<String> getUserName();
    @Insert
    void insertToDatabase(User...users);
    @Delete
    void delete(User user);
    @Query("Select pet.*,users.* from pet INNER JOIN users ON pet.user_id=users.userid")
    LiveData<List<UserAndPet>> getJoinData();

}
