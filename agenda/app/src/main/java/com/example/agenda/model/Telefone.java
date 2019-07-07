package com.example.agenda.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "telefone",
        foreignKeys = @ForeignKey(entity = Contato.class, parentColumns = "id", childColumns = "contato_id", onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
)
public class Telefone {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String numero;

    private TipoTelefone tipo;

    @ColumnInfo(name = "contato_id")
    private int contatoId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public TipoTelefone getTipo() {
        return tipo;
    }

    public void setTipo(TipoTelefone tipo) {
        this.tipo = tipo;
    }

    public int getContatoId() {
        return contatoId;
    }

    public void setContatoId(int contatoId) {
        this.contatoId = contatoId;
    }
}


