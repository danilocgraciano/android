package com.example.agenda;

import android.app.Application;
import android.widget.Toast;

public class AgendaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //ESTE TRECHO DE CÓDIGO É EXECUTADO ANTES DA CRIAÇÃO DA ACTIVITY, UMA ÚNICA VEZ
        //SERIA O STARTUP DA APLICAÇÃO
        //USAR COM CAUTELA!
        //VERIFIQUE O ARQUIVO AndroidManifest.xml
        Toast.makeText(this, "Bem vindo!", Toast.LENGTH_LONG).show();
    }
}
