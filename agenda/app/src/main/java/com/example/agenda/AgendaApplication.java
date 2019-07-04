package com.example.agenda;

import android.app.Application;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

public class AgendaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //ESTE TRECHO DE CÓDIGO É EXECUTADO ANTES DA CRIAÇÃO DAS ACTIVITYS, UMA ÚNICA VEZ
        //SERIA O STARTUP DA APLICAÇÃO
        //USAR COM CAUTELA!
        //VERIFIQUE O ARQUIVO AndroidManifest.xml
        String message = "Bem vindo!";
        if (BuildConfig.DEBUG) {
            message = "Dev Mode...";

            Stetho.initializeWithDefaults(this);
        }

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
