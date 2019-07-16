package com.example.agenda.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.agenda.database.AgendaDatabase;
import com.example.agenda.database.dao.TelefoneDao;
import com.example.agenda.model.Telefone;
import com.example.agenda.model.TipoTelefone;

public class FindTelefoneByUserAndTypeTask extends AsyncTask<Void, Void, Telefone> {

    private final int contatoId;
    private final TipoTelefone tipoTelefone;
    private final Callback callback;
    private TelefoneDao dao;

    public FindTelefoneByUserAndTypeTask(Context context, int contatoId, TipoTelefone tipoTelefone, Callback callback) {

        this.dao = AgendaDatabase.getInstance(context).getTelefoneDao();
        this.contatoId = contatoId;
        this.tipoTelefone = tipoTelefone;
        this.callback = callback;
    }

    @Override
    protected Telefone doInBackground(Void... voids) {
        return dao.findByUserAndType(contatoId, tipoTelefone);
    }

    @Override
    protected void onPostExecute(Telefone telefone) {
        if (this.callback != null)
            this.callback.onResult(telefone);
    }

    public interface Callback {
        void onResult(Telefone telefone);
    }
}
