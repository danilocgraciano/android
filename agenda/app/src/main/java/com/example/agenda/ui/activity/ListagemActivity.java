package com.example.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.agenda.R;
import com.example.agenda.database.dao.ContatoDao;
import com.example.agenda.model.Contato;
import com.example.agenda.model.provider.ContatosProvider;

import java.util.List;

import static com.example.agenda.ui.activity.ConstantsActivities.CHAVE_CONTATO;

public class ListagemActivity extends AppCompatActivity {

    private ContatoDao dao = new ContatoDao();

    private List<Contato> contatos = dao.all();

    private ListView listView;

    private ArrayAdapter<Contato> adapter;

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
                recuperaListaDeContatos();
                atualizaListagem();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void recuperaListaDeContatos() {
        List<Contato> contatos = new ContatosProvider(this).recuperar();
        for (Contato contato : contatos)
            dao.add(contato);
        atualizaListagem();
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
        adapter.notifyDataSetChanged();
    }

    private void configuraAdapter() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, contatos);
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
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mi_action_excluir:
                        removeContatosSelecionados(listView);
                        atualizaListagem();
                        mode.finish();
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

    private void removeContatosSelecionados(ListView listView) {
        SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
        if (checkedItems != null) {
            for (int i = 0; i < checkedItems.size(); i++) {
                if (checkedItems.valueAt(i)) {
                    int index = checkedItems.keyAt(i);
                    dao.remove(index);//TODO remover pelo ID
                }
            }
        }
    }
}