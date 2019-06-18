package com.example.agenda.database.dao;

import android.support.annotation.NonNull;

import com.example.agenda.model.Contato;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContatoDao {

    private static int contadorId = 0;
    private final static List<Contato> contatos = new ArrayList<>();

    public void add(@NonNull Contato contato) {
        contato.setId(++contadorId);
        contatos.add(contato);
    }

    public void edit(@NonNull Contato contato) {

        for (int i = 0; i < contatos.size(); i++) {
            if (contatos.get(i).getId() == contato.getId()) {
                contatos.set(i, contato);
                break;
            }
        }
    }

    public List<Contato> all() {
        return Collections.unmodifiableList(contatos);
    }

    public void remove(int index) {
        contatos.remove(index);
    }
}
