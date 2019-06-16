package com.example.agenda.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.agenda.R;
import com.example.agenda.database.dao.ContatoDao;
import com.example.agenda.model.Contato;

import java.util.List;

public class ListagemActivity extends AppCompatActivity {

    private ContatoDao dao = new ContatoDao();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);

        configuraFabNovoContato();
    }

    private void configuraFabNovoContato() {
        FloatingActionButton btnFab = findViewById(R.id.activity_listagem_fab_novo_contato);
        btnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreFormularioContatoActivity();
            }
        });
    }

    private void abreFormularioContatoActivity() {
        Intent it = new Intent(this, FormularioActivity.class);
        startActivity(it);
    }

    @Override
    protected void onResume() {
        super.onResume();

        configuraLista();
    }

    private void configuraLista() {
        List<Contato> contatos = dao.all();
        ListView listView = findViewById(R.id.activity_listagem_lista_contatos);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contatos));
    }
}