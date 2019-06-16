package com.example.agenda.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.agenda.R;
import com.example.agenda.database.dao.ContatoDao;
import com.example.agenda.model.Contato;

public class FormularioActivity extends AppCompatActivity {

    private EditText campoNome = null;
    private EditText campoTelefone = null;
    private EditText campoEmail = null;
    private final ContatoDao dao = new ContatoDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        inicializaCampos();

        habilitaMenuVoltar();

    }

    private void inicializaCampos() {
        campoNome = findViewById(R.id.activity_formulario_nome_contato);
        campoTelefone = findViewById(R.id.activity_formulario_telefone_contato);
        campoEmail = findViewById(R.id.activity_formulario_email_contato);
    }

    private void habilitaMenuVoltar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mi_action_salvar:
                salvaContato();
                finish();
                break;
            case R.id.mi_action_excluir:
                break;
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void salvaContato() {
        Contato contato = criaContato();
        dao.add(contato);
    }

    private Contato criaContato() {
        String nome = campoNome.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String email = campoEmail.getText().toString();

        return new Contato(nome, telefone, email);
    }
}