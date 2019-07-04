package com.example.agenda.database;

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
        }
        return null;
    }
}
