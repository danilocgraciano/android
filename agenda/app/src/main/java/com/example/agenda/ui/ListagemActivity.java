package com.example.agenda.ui;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.agenda.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListagemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);

        List<String> contatos = new ArrayList<>(Arrays.asList(
                "Contato 1",
                "Contato 2",
                "Contato 3",
                "Contato 4",
                "Contato 5",
                "Contato 6",
                "Contato 7",
                "Contato 8",
                "Contato 9",
                "Contato 10",
                "Contato 11",
                "Contato 12",
                "Contato 13",
                "Contato 14",
                "Contato 15"));

        ListView listView = findViewById(R.id.activity_listagem_lista_contatos);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contatos));

        FloatingActionButton btnFab = findViewById(R.id.activity_listagem_fab_novo_contato);
        btnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ListagemActivity.this, FormularioActivity.class);
                startActivity(it);
            }
        });
    }
}
