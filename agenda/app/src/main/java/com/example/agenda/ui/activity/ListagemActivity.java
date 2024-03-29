package com.example.agenda.ui.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.agenda.R;
import com.example.agenda.asynctask.AllContatosTask;
import com.example.agenda.asynctask.ImportaContatosTask;
import com.example.agenda.asynctask.RemoveContatosTask;
import com.example.agenda.database.AgendaDatabase;
import com.example.agenda.database.dao.ContatoDao;
import com.example.agenda.dialog.Mensagem;
import com.example.agenda.model.Contato;
import com.example.agenda.ui.adapter.ListagemAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static com.example.agenda.ui.activity.ConstantsActivities.CHAVE_CONTATO;
import static com.example.agenda.ui.activity.ConstantsActivities.MY_PERMISSIONS_REQUEST_READ_CONTACTS;

public class ListagemActivity extends AppCompatActivity {

    private List<Contato> contatos = new ArrayList<>();

    private ListView listView;

    private ListagemAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);
        configuraLista();
        configuraFabNovoContato();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_listagem, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mi_action_importar:
                if (possuiPermissaoLeituraContatos()) {
                    recuperaListaDeContatos();
                } else {
                    solicitaPermissaoLeituraContatos();
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean possuiPermissaoLeituraContatos() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

    private void recuperaListaDeContatos() {

        final ProgressDialog dialog = configuraProgressDialog();
        dialog.show();

        new ImportaContatosTask(this, dialog, new ImportaContatosTask.Callback() {
            @Override
            public void onResult() {
                atualizaListagem();
            }
        }).execute();

    }

    private ProgressDialog configuraProgressDialog() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.activity_listagem_label_importar_contatos));
        dialog.setIndeterminate(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        return dialog;
    }

    private void solicitaPermissaoLeituraContatos() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    recuperaListaDeContatos();
                }
                return;
            }
        }
    }

    private void configuraLista() {
        listView = findViewById(R.id.activity_listagem_lista_contatos);
        configuraAdapter();
        configuraMenuDeEdicao();
        configuraMenuDeExclusao();
    }

    private void configuraFabNovoContato() {
        FloatingActionButton btnFab = findViewById(R.id.activity_listagem_fab_novo_contato);
        btnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreFormularioInclusao();
            }
        });
    }

    private void abreFormularioInclusao() {
        Intent it = new Intent(this, FormularioActivity.class);
        startActivity(it);
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizaListagem();
    }

    private void atualizaListagem() {
        new AllContatosTask(this, new AllContatosTask.Callback() {
            @Override
            public void onResult(List<Contato> data) {
                contatos.clear();
                contatos.addAll(data);
                adapter.notifyDataSetChanged();
            }
        }).execute();

    }

    private void configuraAdapter() {
        adapter = new ListagemAdapter(this, contatos);
        listView.setAdapter(adapter);
    }

    private void configuraMenuDeEdicao() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contato contato = contatos.get(position);
                abreFormularioEdicao(contato);
            }
        });
    }

    private void abreFormularioEdicao(Contato contato) {
        Intent it = new Intent(ListagemActivity.this, FormularioActivity.class);
        it.putExtra(CHAVE_CONTATO, contato);
        startActivity(it);
    }

    private void configuraMenuDeExclusao() {
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                int checkedCount = listView.getCheckedItemCount();
                mode.setTitle(String.format(getString(R.string.activity_listagem_label_itens_selecionados), checkedCount));
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater menuInflater = mode.getMenuInflater();
                menuInflater.inflate(R.menu.menu_contextual_listagem, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.mi_action_excluir:

                        Mensagem.excluir(ListagemActivity.this, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                removeContatosSelecionados(listView);
                                atualizaListagem();
                                mode.finish();
                            }
                        });
                        break;
                    case R.id.mi_action_compartilhar:
                        List<Contato> contatos = pegaContatosSelecionados(listView);
                        compartilharContatos(contatos);
                        break;
                    default:
                        mode.finish();
                }

                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
    }

    private void compartilharContatos(List<Contato> contatos) {

        String dados = pegaInformacoesDosContatos(contatos);//TODO transformar em arquivo vCard
        Intent it = new Intent(Intent.ACTION_SEND);
        it.setType("text/plain");
        it.putExtra(Intent.EXTRA_TEXT, dados);
        startActivity(it);
    }

    private String pegaInformacoesDosContatos(List<Contato> contatos) {
        StringBuffer dados = new StringBuffer();
        for (Contato contato : contatos) {
            dados.append(contato.toString());
            dados.append("\r\n");
        }
        return dados.toString();
    }

    private void removeContatosSelecionados(ListView listView) {
        List<Contato> contatos = pegaContatosSelecionados(listView);
        new RemoveContatosTask(this, contatos).execute();
    }

    private List<Contato> pegaContatosSelecionados(ListView listView) {
        List<Contato> contatos = new ArrayList<>();
        SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
        if (checkedItems != null) {
            for (int i = 0; i < checkedItems.size(); i++) {
                if (checkedItems.valueAt(i)) {
                    Contato contato = adapter.getItem(checkedItems.keyAt(i));
                    contatos.add(contato);
                }
            }
        }
        return contatos;
    }
}