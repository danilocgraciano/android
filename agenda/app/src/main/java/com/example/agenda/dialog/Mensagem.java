package com.example.agenda.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.agenda.R;

public abstract class Mensagem {

    public static void excluir(Context context, DialogInterface.OnClickListener positiveListener) {
        new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.atencao))
                .setMessage(context.getString(R.string.mensagem_excluir))
                .setPositiveButton(context.getString(R.string.sim), positiveListener)
                .setNegativeButton(context.getString(R.string.nao), null)
                .show();
    }


}
