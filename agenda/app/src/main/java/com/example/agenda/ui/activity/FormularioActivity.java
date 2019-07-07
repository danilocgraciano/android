package com.example.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.agenda.R;
import com.example.agenda.database.AgendaDatabase;
import com.example.agenda.database.dao.ContatoDao;
import com.example.agenda.database.dao.TelefoneDao;
import com.example.agenda.model.Contato;
import com.example.agenda.model.Telefone;
import com.example.agenda.model.TipoTelefone;

import static com.example.agenda.ui.activity.ConstantsActivities.CHAVE_CONTATO;

public class FormularioActivity extends AppCompatActivity {

    private EditText campoNome = null;
    private EditText campoTelefone1 = null;
    private EditText campoTelefone2 = null;
    private EditText campoEmail = null;
    private ContatoDao contatoDao;
    private TelefoneDao telefoneDao;
    private Contato contato = new Contato();
    private Telefone telefone1 = new Telefone();
    private Telefone telefone2 = new Telefone();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        inicializaCampos();

        configuraDao();

        configuraMenuVoltar();

        carregaContato();

    }

    private void inicializaCampos() {
        campoNome = findViewById(R.id.activity_formulario_nome_contato);
        campoTelefone1 = findViewById(R.id.activity_formulario_telefone_1_contato);
        campoTelefone2 = findViewById(R.id.activity_formulario_telefone_2_contato);
        campoEmail = findViewById(R.id.activity_formulario_email_contato);
    }

    private void configuraDao() {
        this.contatoDao = AgendaDatabase.getInstance(this).getContatoDao();
        this.telefoneDao = AgendaDatabase.getInstance(this).getTelefoneDao();
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
                carregaTelefones(contato.getId());
                preencheCampos();
            }
        }
    }

    private void preencheCampos() {
        campoNome.setText(contato.getNome());
        campoEmail.setText(contato.getEmail());
        campoTelefone1.setText(telefone1.getNumero());
        campoTelefone2.setText(telefone2.getNumero());
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
        if (contato.getId() > 0) {
            contatoDao.edit(contato);
            carregaTelefones(contato.getId());

            telefone1.setNumero(campoTelefone1.getText().toString());
            telefoneDao.edit(telefone1);

            telefone2.setNumero(campoTelefone2.getText().toString());
            telefoneDao.edit(telefone2);
        } else {
            int contatoId = contatoDao.add(contato).intValue();
            carregaTelefones(contatoId);
            telefoneDao.add(telefone1, telefone2);
        }
    }

    private void preencheContato() {
        contato.setNome(campoNome.getText().toString());
        contato.setEmail(campoEmail.getText().toString());
    }

    private void carregaTelefones(int contatoId) {

        telefone1 = telefoneDao.findByUserAndType(contatoId, TipoTelefone.TELEFONE_1);
        if (telefone1 == null) {
            telefone1 = new Telefone();
            telefone1.setTipo(TipoTelefone.TELEFONE_1);
            telefone1.setContatoId(contatoId);
        }

        telefone2 = telefoneDao.findByUserAndType(contatoId, TipoTelefone.TELEFONE_2);
        if (telefone2 == null) {
            telefone2 = new Telefone();
            telefone2.setTipo(TipoTelefone.TELEFONE_2);
            telefone2.setContatoId(contatoId);
        }
    }
}