package com.example.agenda.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.agenda.database.AgendaDatabase;
import com.example.agenda.database.dao.TelefoneDao;
import com.example.agenda.model.Telefone;

public class AddTelefoneTask extends AsyncTask<Void, Void, Void> {

    private final Telefone telefone1;
    private final Telefone telefone2;
    private TelefoneDao telefoneDao;

    public AddTelefoneTask(Context context, Telefone telefone1, Telefone telefone2) {

        this.telefoneDao = AgendaDatabase.getInstance(context).getTelefoneDao();

        this.telefone1 = telefone1;
        this.telefone2 = telefone2;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        telefoneDao.add(telefone1, telefone2);
        return null;
    }
}
