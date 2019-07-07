package com.example.agenda.database;

import com.example.agenda.model.TipoTelefone;

import java.util.Calendar;

import androidx.room.TypeConverter;

public class Converters {

    @TypeConverter
    public Long fromCalendar(Calendar value) {
        if (value != null) {
            return value.getTimeInMillis();
        }
        return null;
    }

    @TypeConverter
    public Calendar toCalendar(Long value) {
        if (value != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(value);
            return calendar;
        }
        return null;
    }

    @TypeConverter
    public String fromTipoTelefone(TipoTelefone tipo) {

        if (tipo == null)
            return TipoTelefone.TELEFONE_1.toString();

        switch (tipo) {
            case TELEFONE_1:
                return TipoTelefone.TELEFONE_1.toString();
            case TELEFONE_2:
                return TipoTelefone.TELEFONE_2.toString();
            default:
                throw new IllegalArgumentException("Tipo inválido");
        }
    }

    @TypeConverter
    public TipoTelefone toTipoTelefone(String tipo) {

        if (tipo == null || tipo.trim().isEmpty())
            return TipoTelefone.TELEFONE_1;

        if (tipo.equalsIgnoreCase(TipoTelefone.TELEFONE_1.toString()))
            return TipoTelefone.TELEFONE_1;

        if (tipo.equalsIgnoreCase(TipoTelefone.TELEFONE_2.toString()))
            return TipoTelefone.TELEFONE_2;

        throw new IllegalArgumentException("Tipo inválido");

    }
}
