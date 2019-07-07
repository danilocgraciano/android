package com.example.agenda.database.dao;

import com.example.agenda.model.Contato;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ContatoDao {

    @Insert
    Long add(Contato contato);

    @Update
    void edit(@NonNull Contato contato);

    @Query("SELECT * FROM contato")
    List<Contato> all();

    @Delete
    void remove(@NonNull Contato contato);
}
