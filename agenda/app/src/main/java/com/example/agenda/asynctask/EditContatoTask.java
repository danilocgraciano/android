package com.example.agenda.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.agenda.database.AgendaDatabase;
import com.example.agenda.database.dao.ContatoDao;
import com.example.agenda.model.Contato;

public class EditContatoTask extends AsyncTask<Void, Void, Void> {

    private final Contato contato;
    private Callback callback;
    private ContatoDao contatoDao;

    public EditContatoTask(Context context, Contato contato, Callback callback) {
        this.contatoDao = AgendaDatabase.getInstance(context).getContatoDao();
        this.contato = contato;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        contatoDao.edit(contato);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (this.callback != null)
            this.callback.onResult();
    }

    public interface Callback {
        void onResult();
    }
}
