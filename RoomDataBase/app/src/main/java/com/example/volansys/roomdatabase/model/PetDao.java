package com.example.volansys.roomdatabase.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;
@Dao
public interface PetDao {
    @Insert
    public void insertPet(Pet ... pets);
    @Query("select * from pet")
    public List<Pet> getAllPet();
    @Query("SELECT * FROM pet WHERE petname LIKE :name")
    public List<Pet> getPetByName(String name);
    @Query("SELECT * FROM pet WHERE user_id IN(:uid)")
    Pet getPetById(String uid);
}
