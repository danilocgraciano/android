package com.example.agenda.model.provider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.example.agenda.model.Contato;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ContatosProvider {

    private Context context;
    private List<Contato> contatos;

    public ContatosProvider(Context context) {

        this.context = context;
        this.contatos = new ArrayList<>();
    }

    public List<Contato> recuperar() {
        ContentResolver contentResolver = this.context.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                String id = recuperaId(cursor);
                String nome = recuperaNome(cursor);
                String sobrenome = "";
                String telefone = "";
                String email = "";

                if (possuiTelefone(cursor))
                    telefone = recuperaTelefone(id);

                email = recuperaEmail(id);

                contatos.add(new Contato(nome, sobrenome, telefone, email));
            }
            cursor.close();
        }
        ordena(contatos);
        return contatos;
    }

    private String recuperaNome(Cursor cursor) {
        String nome = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        nome = (nome == null) ? "" : nome;
        return nome;
    }

    private String recuperaId(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
    }

    private boolean possuiTelefone(Cursor cursor) {
        return Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0;
    }

    private String recuperaTelefone(String id) {
        String telefone = "";

        Cursor phoneCursor = this.context.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id},
                null);
        if (phoneCursor != null) {
            if (phoneCursor.moveToNext()) {
                telefone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                telefone = telefone.replaceAll("[()\\s-]+", "");
            }
            phoneCursor.close();
        }
        return telefone;
    }

    private String recuperaEmail(String id) {
        String email = "";
        Cursor emailCursor = this.context.getContentResolver().query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id},
                null);
        if (emailCursor != null) {
            if (emailCursor.moveToNext()) {
                email = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
            }
            emailCursor.close();
        }
        return email;
    }

    private void ordena(List<Contato> contatos) {
        Collections.sort(contatos, new Comparator<Contato>() {
            @Override
            public int compare(Contato contato1, Contato contato2) {
                return contato1.getNome().compareTo(contato2.getNome());
            }
        });
    }
}
