package com.example.agenda.model;

import java.io.Serializable;
import java.util.Calendar;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "contato")
public class Contato implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nome;
    private String telefone;
    private String email;
    @ColumnInfo(name = "data_cadastro")
    private Calendar dataCadastro = Calendar.getInstance();

    public Contato() {

    }

    @Ignore
    public Contato(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Calendar getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Calendar dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @Override
    public String toString() {
        return this.getNome() + " - " + this.getTelefone();
    }
}
