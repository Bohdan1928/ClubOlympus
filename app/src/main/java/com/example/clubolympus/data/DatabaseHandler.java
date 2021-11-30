package com.example.clubolympus.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.clubolympus.data.ClubOlympusContract.MemberEntry;

import androidx.annotation.Nullable;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(Context context) {
        super(context, ClubOlympusContract.DATABASE_NAME, null, ClubOlympusContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_MEMBERS = " CREATE TABLE "
                + MemberEntry.TABLE_NAME
                + "("
                + MemberEntry.KEY_ID + " INTEGER PRIMARY KEY ,"
                + MemberEntry.KEY_NAME + " TEXT ,"
                + MemberEntry.KEY_LASTNAME + " TEXT ,"
                + MemberEntry.KEY_GENDER + " INTEGER NOT NULL,"
                + MemberEntry.KEY_SPORT + " TEXT"
                + ")";
        sqLiteDatabase.execSQL(CREATE_TABLE_MEMBERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ClubOlympusContract.DATABASE_NAME);
        onCreate(sqLiteDatabase);
    }
}
