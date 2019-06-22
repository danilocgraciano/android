package com.example.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.agenda.R;
import com.example.agenda.database.dao.ContatoDao;
import com.example.agenda.model.Contato;

import static com.example.agenda.ui.activity.ConstantsActivities.CHAVE_CONTATO;

public class FormularioActivity extends AppCompatActivity {

    private EditText campoNome = null;
    private EditText campoTelefone = null;
    private EditText campoEmail = null;
    private final ContatoDao dao = new ContatoDao();
    private Contato contato = new Contato();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        inicializaCampos();

        configuraMenuVoltar();

        carregaContato();

    }

    private void inicializaCampos() {
        campoNome = findViewById(R.id.activity_formulario_nome_contato);
        campoTelefone = findViewById(R.id.activity_formulario_telefone_contato);
        campoEmail = findViewById(R.id.activity_formulario_email_contato);
    }

    private void configuraMenuVoltar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void carregaContato() {
        Intent it = getIntent();
        if (it.hasExtra(CHAVE_CONTATO)) {
            contato = (Contato) it.getSerializableExtra(CHAVE_CONTATO);
            if (contato != null) {
                preencheCampos();
            }
        }
    }

    private void preencheCampos() {
        campoNome.setText(contato.getNome());
        campoTelefone.setText(contato.getTelefone());
        campoEmail.setText(contato.getEmail());
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
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void salvaContato() {
        preencheContato();
        if (contato.getId() > 0)
            dao.edit(contato);
        else
            dao.add(contato);
    }

    private void preencheContato() {
        contato.setNome(campoNome.getText().toString());
        contato.setTelefone(campoTelefone.getText().toString());
        contato.setEmail(campoEmail.getText().toString());
    }
}