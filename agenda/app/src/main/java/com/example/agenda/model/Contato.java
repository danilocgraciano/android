package com.example.agenda.model;

import android.support.annotation.NonNull;

public class Contato {

    private final String nome;
    private final String telefone;
    private final String email;

    public Contato(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    @NonNull
    @Override
    public String toString() {
        return this.nome;
    }
}
