package com.example.agenda.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.agenda.database.AgendaDatabase;
import com.example.agenda.database.dao.ContatoDao;
import com.example.agenda.model.Contato;

public class AddContatoTask extends AsyncTask<Void, Void, Long> {

    private final Contato contato;
    private final Callback callback;
    private ContatoDao contatoDao;

    public AddContatoTask(Context context, Contato contato, Callback callback) {

        this.contatoDao = AgendaDatabase.getInstance(context).getContatoDao();
        this.contato = contato;
        this.callback = callback;
    }

    @Override
    protected Long doInBackground(Void... voids) {

        return contatoDao.add(contato);
    }

    @Override
    protected void onPostExecute(Long contatoId) {
        if (this.callback != null)
            this.callback.onResult(contatoId);
    }

    public interface Callback {
        void onResult(Long contatoId);
    }
}
