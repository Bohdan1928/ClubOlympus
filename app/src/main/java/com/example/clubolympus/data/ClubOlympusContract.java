package com.example.clubolympus.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class ClubOlympusContract {
    private ClubOlympusContract() {

    }
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "OLYMPUS";
    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "com.example.clubolympus";
    public static final String PATH_MEMBERS = "members";
    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY);


    public static final class MemberEntry implements BaseColumns {
        public static final String TABLE_NAME = "MEMBERS";

        public static final String KEY_ID = BaseColumns._ID;
        public static final String KEY_NAME = "NAME";
        public static final String KEY_LASTNAME = "LASTNAME";
        public static final String KEY_GENDER = "GENDER";
        public static final String KEY_SPORT = "SPORT";

        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MEMBERS);
        public static final String CONTENT_MULTIPLE_ITEMS = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_MEMBERS;
        public static final String CONTENT_SINGLE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_MEMBERS;

    }
}
