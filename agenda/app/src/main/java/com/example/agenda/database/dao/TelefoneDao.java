package com.example.agenda.database.dao;

import com.example.agenda.model.Telefone;
import com.example.agenda.model.TipoTelefone;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TelefoneDao {

    @Insert
    void add(Telefone... telefone);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void edit(@NonNull Telefone telefone);

    @Query("SELECT * FROM telefone")
    List<Telefone> all();

    @Delete
    void remove(@NonNull Telefone telefone);

    @Query("SELECT * FROM telefone WHERE contato_id = :id AND tipo = :tipo")
    Telefone findByUserAndType(int id, TipoTelefone tipo);
}
