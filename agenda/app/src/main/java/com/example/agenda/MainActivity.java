package com.example.agenda;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> contatos = new ArrayList<>(Arrays.asList("João", "Maria", "José", "Pedro"));

        ListView listView = findViewById(R.id.activity_main_lista_contatos);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contatos));
    }
}
