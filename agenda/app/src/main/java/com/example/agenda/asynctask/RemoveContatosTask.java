package com.example.agenda.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.agenda.database.AgendaDatabase;
import com.example.agenda.database.dao.ContatoDao;
import com.example.agenda.model.Contato;

import java.util.List;

public class RemoveContatosTask extends AsyncTask<Void, Void, Void> {

    private final ContatoDao contatoDao;
    private final List<Contato> contatos;

    public RemoveContatosTask(Context context, List<Contato> contatos) {

        this.contatoDao = AgendaDatabase.getInstance(context).getContatoDao();
        this.contatos = contatos;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        for (int i = contatos.size() - 1; i >= 0; i--) {
            contatoDao.remove(contatos.get(i));
        }
        return null;
    }
}
