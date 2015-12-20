package com.softwork.ydk_lsj.dessin.DataProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

public class DBProvider extends ContentProvider {
    static final public String SCHEDULE_TABLE = "Schedule";
    static final public String ADDITIONAL_SCHEDULE_TABLE = "AddSchedule";

    static final public String AUTHORITY = "Dessin.ydk_lsj.softwork.com";

    static final public Uri SCHEDULE_TABLE_CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/" + SCHEDULE_TABLE);
    static final public Uri ADDITIONAL_SCHEDULE_TABLE_CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/" + ADDITIONAL_SCHEDULE_TABLE);

    static final int SCHEDULE_TABLE_GETALL = 1;
    static final int SCHEDULE_TABLE_GETONE = 2;
    static final int ADDITIONAL_SCHEDULE_TABLE_GETALL = 3;
    static final int ADDITIONAL_SCHEDULE_TABLE_GETONE = 4;

    // For Schedule Table
    static final public String SCHEDULE_ID = "_id";
    static final public String SCHEDULE_TITLE = "schedule_title";
    static final public String SCHEDULE_INFORMATION = "schedule_info";
    static final public String SCHEDULE_START_DATE = "schedule_start_date";
    static final public String SCHEDULE_END_DATE = "schedule_end_date";

    // For Additional Schedule Table
    static final public String ADDITIONAL_SCHEDULE_ID = "_id";
    static final public String ADDITIONAL_SCHEDULE_TITLE = "add_schedule_title";
    static final public String ADDITIONAL_SCHEDULE_INFORMATION = "add_schedule_info";
    static final public String ADDITIONAL_SCHEDULE_START_DATE = "add_schedule_start_date";
    static final public String ADDITIONAL_SCHEDULE_END_DATE = "add_schedule_end_date";
    static final public String SCHEDULE_TABLE_FK = "schedule_table_fk";

    static final UriMatcher matcher;
    static {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, SCHEDULE_TABLE, SCHEDULE_TABLE_GETALL);
        matcher.addURI(AUTHORITY, SCHEDULE_TABLE + "/*", SCHEDULE_TABLE_GETONE);

        matcher.addURI(AUTHORITY, ADDITIONAL_SCHEDULE_TABLE, ADDITIONAL_SCHEDULE_TABLE_GETALL);
        matcher.addURI(AUTHORITY, ADDITIONAL_SCHEDULE_TABLE + "/*", ADDITIONAL_SCHEDULE_TABLE_GETONE);
    }

    SQLiteDatabase timeTableDB;
    class timeTableDBHelper extends SQLiteOpenHelper {
        public timeTableDBHelper(Context c) {
            super(c, "dessin.de", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL("CREATE TABLE IF NOT EXISTS " + SCHEDULE_TABLE
                    + " (" + SCHEDULE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + SCHEDULE_TITLE + " TEXT, "
                    + SCHEDULE_INFORMATION + " TEXT, "
                    + SCHEDULE_START_DATE + " DATE, "
                    + SCHEDULE_END_DATE + " DATE);");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + ADDITIONAL_SCHEDULE_TABLE
                    + " (" + ADDITIONAL_SCHEDULE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ADDITIONAL_SCHEDULE_TITLE + " TEXT, "
                    + ADDITIONAL_SCHEDULE_INFORMATION + " TEXT, "
                    + ADDITIONAL_SCHEDULE_START_DATE + " DATE, "
                    + ADDITIONAL_SCHEDULE_END_DATE + " DATE, "
                    + SCHEDULE_TABLE_FK + " INTEGER, "
                    + "FOREIGN KEY (" + SCHEDULE_TABLE_FK + ") REFERENCES "
                    + SCHEDULE_TABLE + "(" + SCHEDULE_ID +"));)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + ADDITIONAL_SCHEDULE_TABLE + ";");
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        timeTableDBHelper timeTableHelper = new timeTableDBHelper(getContext());

        timeTableDB = timeTableHelper.getWritableDatabase();
        timeTableDB.execSQL("PRAGMA foreign_keys=ON;"); // Foreign Key 사용 허용

        Log.e("DB", timeTableDB.toString());

        return (timeTableDB != null);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int cnt = 0;
        String where;

        switch (matcher.match(uri)) {
            case ADDITIONAL_SCHEDULE_TABLE_GETALL:
                cnt = timeTableDB.delete(ADDITIONAL_SCHEDULE_TABLE, selection, selectionArgs);
                break;

            case ADDITIONAL_SCHEDULE_TABLE_GETONE:
                where = ADDITIONAL_SCHEDULE_INFORMATION + " = '" + uri.getPathSegments().get(1) + "'";
                if(TextUtils.isEmpty(selection) == false) {
                    where += " AND " + selection;
                }
                cnt = timeTableDB.delete(ADDITIONAL_SCHEDULE_TABLE, where, selectionArgs);
                break;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String sql = null;

        switch(matcher.match(uri)) {
            case SCHEDULE_TABLE_GETALL:
            case ADDITIONAL_SCHEDULE_TABLE_GETALL:
                Log.e("SELECT * FROM", uri.getPathSegments().get(0));
                sql = "SELECT * FROM ";
                sql += uri.getPathSegments().get(0);
                break;

            case SCHEDULE_TABLE_GETONE:
            case ADDITIONAL_SCHEDULE_TABLE_GETONE:
                Log.e("SELECT * FROM ONE", uri.getPathSegments().get(0));
                sql = "SELECT * FROM ";
                sql += uri.getPathSegments().get(0);
                try{
                    Integer.parseInt(selectionArgs[0]);
                    sql += " WHERE " + selection + " = ";
                    sql += selectionArgs[0];
                } catch (Exception e) {
                    sql += " WHERE " + selection + " = '";
                    sql += selectionArgs[0];
                    sql += "'";
                }
                break;
        }

        if(sortOrder != null) {
            sql += sortOrder;
        }

        sql += ";";

        Cursor cur = timeTableDB.rawQuery(sql, null);
        return cur;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (matcher.match(uri)) {
            case ADDITIONAL_SCHEDULE_TABLE_GETALL:
                return "vnd.android.cursor.dir/vnd.ydksoftwork.lecture";
            case ADDITIONAL_SCHEDULE_TABLE_GETONE:
                return "vnd.android.cursor.item/vnd.ydksoftwork.lecture";
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long row = 0;
        Uri notiuri = null;
        switch (matcher.match(uri)) {
            case SCHEDULE_TABLE_GETALL:
                row = timeTableDB.insert(SCHEDULE_TABLE, null, values);
                notiuri = ContentUris.withAppendedId(SCHEDULE_TABLE_CONTENT_URI, row);
                break;

            case ADDITIONAL_SCHEDULE_TABLE_GETALL:
                row = timeTableDB.insert(ADDITIONAL_SCHEDULE_TABLE, null, values);
                notiuri = ContentUris.withAppendedId(ADDITIONAL_SCHEDULE_TABLE_CONTENT_URI, row);
                break;
        }
        if(row > 0) {
            getContext().getContentResolver().notifyChange(notiuri, null);
            return notiuri;
        }
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int cnt = 0;
        String where;

        switch (matcher.match(uri)) {
            case ADDITIONAL_SCHEDULE_TABLE_GETALL:
                cnt = timeTableDB.update(ADDITIONAL_SCHEDULE_TABLE, values, selection, selectionArgs);
                break;

            case ADDITIONAL_SCHEDULE_TABLE_GETONE:
                where = ADDITIONAL_SCHEDULE_ID + " = '" + uri.getPathSegments().get(1) + "'";
                if(TextUtils.isEmpty(selection) == false) {
                    where += " AND " + selection;
                }
                cnt = timeTableDB.update(ADDITIONAL_SCHEDULE_TABLE, values, where, selectionArgs);
                break;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }
}
