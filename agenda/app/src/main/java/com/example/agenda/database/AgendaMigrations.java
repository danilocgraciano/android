package com.example.agenda.database;

import com.example.agenda.model.TipoTelefone;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

class AgendaMigrations {

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //INCLUSÃO DO CAMPO SOBRENOME
            database.execSQL("ALTER TABLE contato ADD COLUMN sobrenome TEXT");
        }
    };

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //INCLUSÃO DO CAMPO DATA DE CADASTRO
            database.execSQL("ALTER TABLE contato ADD COLUMN data_cadastro INTEGER");
        }
    };

    private static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //REMOÇÃO DO CAMPO SOBRENOME
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

    private static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("BEGIN TRANSACTION;");

            //RETIRADA DE CAMPO TELEFONE DA TABELA DE CONTATO
            database.execSQL("CREATE TABLE contato_bkp (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nome TEXT, telefone TEXT, email TEXT, data_cadastro INTEGER);");
            database.execSQL("INSERT INTO contato_bkp SELECT id, nome, telefone, email, data_cadastro FROM contato;");
            database.execSQL("DROP TABLE contato;");
            database.execSQL("CREATE TABLE contato (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nome TEXT, email TEXT, data_cadastro INTEGER);");
            database.execSQL("INSERT INTO contato SELECT id, nome, email, data_cadastro FROM contato_bkp;");

            //CRIACAO DA TABELA DE TELEFONES
            database.execSQL("CREATE TABLE telefone " +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "numero TEXT, " +
                    "tipo TEXT, " +
                    "contato_id INTEGER NOT NULL, " +
                    "FOREIGN KEY (contato_id) REFERENCES contato(id) " +
                    "ON UPDATE CASCADE ON DELETE CASCADE);");

            //PREENCHIMENTO DA TABELA DE TELEFONES
            database.execSQL("INSERT INTO telefone(numero, contato_id) SELECT telefone, id FROM contato_bkp;");
            database.execSQL("UPDATE telefone SET tipo = ?", new TipoTelefone[]{TipoTelefone.TELEFONE_1});

            database.execSQL("DROP TABLE contato_bkp;");

            database.execSQL("COMMIT;");
        }
    };

    static final Migration[] TODAS_MIGRATIONS = {MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5};
}
