package com.example.agenda.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.agenda.database.AgendaDatabase;
import com.example.agenda.database.dao.ContatoDao;
import com.example.agenda.database.dao.TelefoneDao;
import com.example.agenda.model.Contato;
import com.example.agenda.model.provider.ContatosProvider;

import java.util.List;

public class ImportaContatosTask extends AsyncTask<Void, Contato, Void> {

    private final Context context;
    private final ProgressDialog dialog;
    private final Callback callbak;
    private ContatoDao contatoDao;
    private TelefoneDao telefoneDao;

    public ImportaContatosTask(Context context, ProgressDialog dialog, Callback callbak) {

        this.context = context;
        this.contatoDao = AgendaDatabase.getInstance(context).getContatoDao();
        this.telefoneDao = AgendaDatabase.getInstance(context).getTelefoneDao();
        this.dialog = dialog;
        this.callbak = callbak;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            List<Contato> contatos = new ContatosProvider(context).recuperar();
            for (int i = 0; i < contatos.size(); i++) {
                Contato contato = contatos.get(i);
                contatoDao.add(contato);
                publishProgress(contato);
            }
        } catch (Exception ex) {
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("ERROR", "doInBackground: ", ex);
        } finally {
            dialog.dismiss();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Contato... values) {
        Contato contato = values[0];
        dialog.setMessage(contato.getNome());
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        dialog.dismiss();
        if (this.callbak != null)
            this.callbak.onResult();
    }

    public interface Callback {
        void onResult();
    }
}
