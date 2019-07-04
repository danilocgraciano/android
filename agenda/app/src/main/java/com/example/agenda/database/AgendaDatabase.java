package com.example.agenda.database;

import android.content.Context;

import com.example.agenda.database.dao.ContatoDao;
import com.example.agenda.model.Contato;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import static com.example.agenda.database.AgendaMigrations.TODAS_MIGRATIONS;

@Database(entities = {Contato.class}, version = 3, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AgendaDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "agenda.db";


    public abstract ContatoDao getContatoDao();

    private static AgendaDatabase instance;

    public static synchronized AgendaDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room
                    .databaseBuilder(context, AgendaDatabase.class, AgendaDatabase.DATABASE_NAME)
                    .allowMainThreadQueries()
                    //.fallbackToDestructiveMigration() SOMENTE ANTES DE O APLICATIVO SER PUBLICADO
                    .addMigrations(TODAS_MIGRATIONS)
                    .build();

        }
        return instance;
    }

}
