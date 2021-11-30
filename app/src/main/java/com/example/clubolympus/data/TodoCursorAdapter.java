package com.example.clubolympus.data;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.clubolympus.R;

public class TodoCursorAdapter extends CursorAdapter {


    public TodoCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_todo, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView itemFirstName = view.findViewById(R.id.itemFirstName);
        TextView itemLastName = view.findViewById(R.id.itemLastName);
        TextView itemSport = view.findViewById(R.id.itemSport);

        String firstName = cursor.getString(cursor.getColumnIndexOrThrow(ClubOlympusContract.MemberEntry.KEY_NAME));
        String lastName = cursor.getString(cursor.getColumnIndexOrThrow(ClubOlympusContract.MemberEntry.KEY_LASTNAME));
        String sport = cursor.getString(cursor.getColumnIndexOrThrow(ClubOlympusContract.MemberEntry.KEY_SPORT));

        itemFirstName.setText(firstName);
        itemLastName.setText(lastName);
        itemSport.setText(sport);
    }
}
