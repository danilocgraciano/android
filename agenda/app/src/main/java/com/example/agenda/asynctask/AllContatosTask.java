package com.example.agenda.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.agenda.database.AgendaDatabase;
import com.example.agenda.database.dao.ContatoDao;
import com.example.agenda.model.Contato;

import java.util.List;

public class AllContatosTask extends AsyncTask<Void, Void, List<Contato>> {

    private final ContatoDao contatoDao;
    private final Callback callback;

    public AllContatosTask(Context context, Callback callback) {

        this.contatoDao = AgendaDatabase.getInstance(context).getContatoDao();
        this.callback = callback;
    }

    @Override
    protected List<Contato> doInBackground(Void... voids) {
        return contatoDao.all();
    }

    @Override
    protected void onPostExecute(List<Contato> data) {
        if (this.callback != null)
            this.callback.onResult(data);
    }

    public interface Callback {
        void onResult(List<Contato> data);
    }
}
