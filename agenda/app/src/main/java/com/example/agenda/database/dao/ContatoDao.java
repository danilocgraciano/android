package com.example.agenda.database.dao;

import com.example.agenda.model.Contato;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContatoDao {

    private final static List<Contato> contatos = new ArrayList<>();

    public void add(Contato contato) {
        contatos.add(contato);
    }

    public List<Contato> all() {
        return Collections.unmodifiableList(contatos);
    }
}
