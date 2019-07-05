package com.example.agenda.database;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

class AgendaMigrations {

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE contato ADD COLUMN sobrenome TEXT");
        }
    };

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE contato ADD COLUMN data_cadastro INTEGER");
        }
    };

    private static final Migration MIGRATION_3_4 = new Migration(3,4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("BEGIN TRANSACTION;");
            database.execSQL("CREATE TEMPORARY TABLE contato_temp (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nome TEXT, telefone TEXT, email TEXT, data_cadastro INTEGER);");
            database.execSQL("INSERT INTO contato_temp SELECT id, nome, telefone, email, data_cadastro FROM contato;");
            database.execSQL("DROP TABLE contato;");
            database.execSQL("CREATE TABLE contato (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nome TEXT, telefone TEXT, email TEXT, data_cadastro INTEGER);");
            database.execSQL("INSERT INTO contato SELECT id, nome, telefone, email, data_cadastro FROM contato_temp;");
            database.execSQL("DROP TABLE contato_temp;");
            database.execSQL("COMMIT;");
        }
    };

    static final Migration[] TODAS_MIGRATIONS = {MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4};
}
