package com.pigkins.asku.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pigkins.asku.data.Question;

/**
 * Created by qding on 10/29/16.
 */

public class QuestionDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 7;

    public static final String DATABASE_NAME = "Questions.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String INTEGER_TYPE = " INTEGER";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + QuestionDBContract.QuestionEntry.TABLE_NAME + " (" +
                    QuestionDBContract.QuestionEntry._ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                    QuestionDBContract.QuestionEntry.COLUMN_NAME_MONTH + INTEGER_TYPE + COMMA_SEP +
                    QuestionDBContract.QuestionEntry.COLUMN_NAME_DAY + INTEGER_TYPE + COMMA_SEP +
                    QuestionDBContract.QuestionEntry.COLUMN_NAME_CONTENT + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + QuestionDBContract.QuestionEntry.TABLE_NAME;

    public QuestionDBHelper(Context context) {
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
