package com.example.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.agenda.R;
import com.example.agenda.asynctask.AddContatoTask;
import com.example.agenda.asynctask.AddTelefoneTask;
import com.example.agenda.asynctask.EditContatoTask;
import com.example.agenda.asynctask.EditTelefoneTask;
import com.example.agenda.asynctask.FindTelefoneByUserAndTypeTask;
import com.example.agenda.model.Contato;
import com.example.agenda.model.Telefone;
import com.example.agenda.model.TipoTelefone;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.agenda.ui.activity.ConstantsActivities.CHAVE_CONTATO;

public class FormularioActivity extends AppCompatActivity {

    private EditText campoNome = null;
    private EditText campoTelefone1 = null;
    private EditText campoTelefone2 = null;
    private EditText campoEmail = null;
    private Contato contato = new Contato();
    private Telefone telefone1 = new Telefone();
    private Telefone telefone2 = new Telefone();

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
        campoTelefone1 = findViewById(R.id.activity_formulario_telefone_1_contato);
        campoTelefone2 = findViewById(R.id.activity_formulario_telefone_2_contato);
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
                carregaTelefones(contato.getId());
                preencheCampos();
            }
        }
    }

    private void preencheCampos() {
        campoNome.setText(contato.getNome());
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
            new EditContatoTask(this, contato, new EditContatoTask.Callback() {
                @Override
                public void onResult() {
                    preencheTelefones(contato.getId());
                    new EditTelefoneTask(FormularioActivity.this, telefone1, telefone2).execute();
                    finish();
                }
            }).execute();

        } else {
            new AddContatoTask(this, contato, new AddContatoTask.Callback() {
                @Override
                public void onResult(Long contatoId) {
                    preencheTelefones(contatoId.intValue());
                    new AddTelefoneTask(FormularioActivity.this, telefone1, telefone2).execute();
                    finish();
                }
            }).execute();
        }
    }

    private void preencheTelefones(int contatoId) {

        telefone1.setNumero(campoTelefone1.getText().toString());
        telefone1.setContatoId(contatoId);
        telefone1.setTipo(TipoTelefone.TELEFONE_1);

        telefone2.setNumero(campoTelefone2.getText().toString());
        telefone2.setContatoId(contatoId);
        telefone2.setTipo(TipoTelefone.TELEFONE_2);
    }

    private void preencheContato() {
        contato.setNome(campoNome.getText().toString());
        contato.setEmail(campoEmail.getText().toString());
    }

    private void carregaTelefones(final int contatoId) {

        new FindTelefoneByUserAndTypeTask(this, contatoId, TipoTelefone.TELEFONE_1, new FindTelefoneByUserAndTypeTask.Callback() {
            @Override
            public void onResult(Telefone telefone) {
                telefone1 = telefone;
                if (telefone1 == null) {
                    telefone1 = new Telefone();
                    telefone1.setTipo(TipoTelefone.TELEFONE_1);
                    telefone1.setContatoId(contatoId);
                }
                campoTelefone1.setText(telefone1.getNumero());
            }
        }).execute();

        new FindTelefoneByUserAndTypeTask(this, contatoId, TipoTelefone.TELEFONE_2, new FindTelefoneByUserAndTypeTask.Callback() {
            @Override
            public void onResult(Telefone telefone) {
                telefone2 = telefone;
                if (telefone2 == null) {
                    telefone2 = new Telefone();
                    telefone2.setTipo(TipoTelefone.TELEFONE_2);
                    telefone2.setContatoId(contatoId);
                }
                campoTelefone2.setText(telefone2.getNumero());
            }
        }).execute();
    }
}