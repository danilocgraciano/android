package com.example.agenda.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.agenda.R;
import com.example.agenda.asynctask.FindTelefoneByUserAndTypeTask;
import com.example.agenda.model.Contato;
import com.example.agenda.model.Telefone;
import com.example.agenda.model.TipoTelefone;

import java.util.List;

public class ListagemAdapter extends BaseAdapter {

    private final Context context;
    private final List<Contato> contatos;

    public ListagemAdapter(Context context, List<Contato> contatos) {
        this.context = context;
        this.contatos = contatos;
    }

    @Override
    public int getCount() {
        return contatos.size();
    }

    @Override
    public Contato getItem(int position) {
        return contatos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_contato, parent, false);

        Contato contato = getItem(position);
        configuraCampoNome(view, contato);
        configuraCampoTelefone(view, contato);

        return view;
    }

    private void configuraCampoNome(View view, Contato contato) {
        TextView campoNome = view.findViewById(R.id.item_contato_nome);
        campoNome.setText(contato.getNome());
    }

    private void configuraCampoTelefone(View view, final Contato contato) {
        final TextView campoTelefone = view.findViewById(R.id.item_contato_telefone);
        new FindTelefoneByUserAndTypeTask(context, contato.getId(), TipoTelefone.TELEFONE_1, new FindTelefoneByUserAndTypeTask.Callback() {
            @Override
            public void onResult(Telefone telefone) {
                if (telefone != null)
                    campoTelefone.setText(telefone.getNumero());
            }
        }).execute();

    }
}
