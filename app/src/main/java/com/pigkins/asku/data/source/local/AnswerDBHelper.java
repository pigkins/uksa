package com.pigkins.asku.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by qding on 10/29/16.
 */

public class AnswerDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Answer.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String INTEGER_TYPE = " INTEGER";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + AnswerDBContract.AnswerEntry.TABLE_NAME + " (" +
                    AnswerDBContract.AnswerEntry._ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                    AnswerDBContract.AnswerEntry.COLUMN_NAME_QID + INTEGER_TYPE + COMMA_SEP +
                    AnswerDBContract.AnswerEntry.COLUMN_NAME_UID + INTEGER_TYPE + COMMA_SEP +
                    AnswerDBContract.AnswerEntry.COLUMN_NAME_YEAR + INTEGER_TYPE + COMMA_SEP +
                    AnswerDBContract.AnswerEntry.COLUMN_NAME_CONTENT + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + AnswerDBContract.AnswerEntry.TABLE_NAME;

    public AnswerDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }
}
