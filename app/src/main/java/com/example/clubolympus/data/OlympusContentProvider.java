
package com.example.clubolympus.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class OlympusContentProvider extends ContentProvider {
    DatabaseHandler databaseHandler;

    private static final int MEMBERS = 111;
    private static final int MEMBER_ID = 222;


    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(ClubOlympusContract.AUTHORITY, ClubOlympusContract.PATH_MEMBERS, MEMBERS);
        uriMatcher.addURI(ClubOlympusContract.AUTHORITY, ClubOlympusContract.PATH_MEMBERS + "/#", MEMBER_ID);
    }

    @Override
    public boolean onCreate() {
        databaseHandler = new DatabaseHandler(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) { // strings - name of column
        SQLiteDatabase database = databaseHandler.getReadableDatabase();
        Cursor cursor;
        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS:
                cursor = database.query(ClubOlympusContract.MemberEntry.TABLE_NAME, strings, s, strings1, null, null, s1);
                break;
            case MEMBER_ID:
                s = ClubOlympusContract.MemberEntry.KEY_ID + "=?";
                strings1 = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(ClubOlympusContract.MemberEntry.TABLE_NAME, strings, s, strings1, null, null, s1);
                break;
            default:
                throw new IllegalArgumentException("Can't query incorrect URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        if (contentValues.containsKey(ClubOlympusContract.MemberEntry.KEY_NAME)) {
            String firstName = contentValues.getAsString(ClubOlympusContract.MemberEntry.KEY_NAME);
            if (firstName == null) {
                throw new IllegalArgumentException("You have to input first name");
            }
        }
        if (contentValues.containsKey(ClubOlympusContract.MemberEntry.KEY_LASTNAME)) {
            String lastName = contentValues.getAsString(ClubOlympusContract.MemberEntry.KEY_LASTNAME);
            if (lastName == null) {
                throw new IllegalArgumentException("You have to input last name");
            }
        }

        if (contentValues.containsKey(ClubOlympusContract.MemberEntry.KEY_GENDER)) {
            Integer gender = contentValues.getAsInteger(ClubOlympusContract.MemberEntry.KEY_GENDER);
            if (gender == null ||
                    !(gender == ClubOlympusContract.MemberEntry.GENDER_UNKNOWN ||
                            gender == ClubOlympusContract.MemberEntry.GENDER_FEMALE ||
                            gender == ClubOlympusContract.MemberEntry.GENDER_MALE)) {
                throw new IllegalArgumentException("You have to choose gender");
            }
        }

        if (contentValues.containsKey(ClubOlympusContract.MemberEntry.KEY_SPORT)) {
            String sport = contentValues.getAsString(ClubOlympusContract.MemberEntry.KEY_SPORT);
            if (sport == null) {
                throw new IllegalArgumentException("You have to input sport");
            }
        }
        SQLiteDatabase database = databaseHandler.getWritableDatabase();

        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS:
                long id = database.insert(ClubOlympusContract.MemberEntry.TABLE_NAME, null, contentValues);
                if (id == -1) {
                    Log.d("insertMethod", "Inserting of data the table failed for " + uri);
                    return null;
                }

                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            default:
                throw new IllegalArgumentException("Can't query incorrect URI " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int rowsDelete;

        switch (match) {
            case MEMBERS:
                rowsDelete = database.delete(ClubOlympusContract.MemberEntry.TABLE_NAME, s, strings);
                break;
            case MEMBER_ID:
                s = ClubOlympusContract.MemberEntry.KEY_ID + "=?";
                strings = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDelete = database.delete(ClubOlympusContract.MemberEntry.TABLE_NAME, s, strings);
                break;
            default:
                throw new IllegalArgumentException("Can't delete this URI " + uri);
        }
        if (rowsDelete != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDelete;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {

        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int rowsUpdate;

        switch (match) {
            case MEMBERS:
                rowsUpdate = database.update(ClubOlympusContract.MemberEntry.TABLE_NAME, contentValues, s, strings);
                break;
            case MEMBER_ID:
                s = ClubOlympusContract.MemberEntry.KEY_ID + "=?";
                strings = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsUpdate = database.update(ClubOlympusContract.MemberEntry.TABLE_NAME, contentValues, s, strings);
                break;
            default:
                throw new IllegalArgumentException("Can't update  this URI " + uri);
        }
        if (rowsUpdate != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdate;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int match = uriMatcher.match(uri);

        Cursor cursor;
        switch (match) {
            case MEMBERS:

                return ClubOlympusContract.MemberEntry.CONTENT_MULTIPLE_ITEMS;
            case MEMBER_ID:

                return ClubOlympusContract.MemberEntry.CONTENT_SINGLE_ITEM;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}
