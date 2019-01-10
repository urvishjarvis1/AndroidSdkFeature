package com.example.volansys.roomdatabase.model;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;

import com.example.volansys.roomdatabase.Converter;

@Database(entities = {User.class, Pet.class}, version = 2)
@TypeConverters({Converter.class})
public abstract class UserDataBase extends RoomDatabase {
    private static UserDataBase INSTANCE;
    public abstract UserDao userDao();
    public abstract PetDao petDao();

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE users ADD 'date' INTEGER");
        }
    };
}
