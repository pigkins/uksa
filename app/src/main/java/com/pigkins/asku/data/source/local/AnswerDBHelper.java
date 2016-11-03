package com.pigkins.asku.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.pigkins.asku.R;
import com.pigkins.asku.data.Answer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by qding on 10/29/16.
 */

public class AnswerDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 4;

    public static final String DATABASE_NAME = "Answer.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String INTEGER_TYPE = " INTEGER";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + AnswerDBContract.AnswerEntry.TABLE_NAME + " (" +
                    AnswerDBContract.AnswerEntry._ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                    AnswerDBContract.AnswerEntry.COLUMN_NAME_AID + INTEGER_TYPE + COMMA_SEP +
                    AnswerDBContract.AnswerEntry.COLUMN_NAME_QID + INTEGER_TYPE + COMMA_SEP +
                    AnswerDBContract.AnswerEntry.COLUMN_NAME_UID + INTEGER_TYPE + COMMA_SEP +
                    AnswerDBContract.AnswerEntry.COLUMN_NAME_YEAR + INTEGER_TYPE + COMMA_SEP +
                    AnswerDBContract.AnswerEntry.COLUMN_NAME_CONTENT + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + AnswerDBContract.AnswerEntry.TABLE_NAME;

    private Context context;

    public AnswerDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        updateAnswerDB(db, readAnswerFromResource(context));
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }

    public void updateAnswerDB(SQLiteDatabase db, HashMap<Integer, List<Answer>> answerListMap) {
        Log.d(this.getClass().getSimpleName(), "Update Answers read from the resource file.");
        db.beginTransaction();
        for (List<Answer> answerList : answerListMap.values()) {
            for (Answer answer : answerList) {
                ContentValues insertValues = new ContentValues();
                insertValues.put(AnswerDBContract.AnswerEntry.COLUMN_NAME_AID, answer.getAnswerId());
                insertValues.put(AnswerDBContract.AnswerEntry.COLUMN_NAME_UID, answer.getUserId());
                insertValues.put(AnswerDBContract.AnswerEntry.COLUMN_NAME_QID, answer.getQuestionId());
                insertValues.put(AnswerDBContract.AnswerEntry.COLUMN_NAME_YEAR, answer.getYear());
                insertValues.put(AnswerDBContract.AnswerEntry.COLUMN_NAME_CONTENT, answer.getContent());
                db.insert(AnswerDBContract.AnswerEntry.TABLE_NAME, null, insertValues);
            }
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public HashMap<Integer, List<Answer>> readAnswerFromResource(Context context) {
        Log.d(this.getClass().getSimpleName(), "Read Questions from the resource file.");
        HashMap<Integer, List<Answer>> answers = new HashMap<>();
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.existinganswers);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] tokens = line.split("\\|");
                Log.d("TMP2", line);
                int answerId = Integer.parseInt(tokens[0]);
                int questionId = Integer.parseInt(tokens[1]);
                int userId = Integer.parseInt(tokens[2]);
                int year = Integer.parseInt(tokens[3]);
                Answer answer = new Answer(answerId, questionId, userId, year, tokens[4]);
                if (answers.containsKey(questionId)) {
                    answers.get(questionId).add(answer);
                } else {
                    List<Answer> answerlist = new ArrayList<>();
                    answerlist.add(answer);
                    answers.put(questionId, answerlist);
                }
            }
        } catch (IOException e) {
            Log.d(this.getClass().getSimpleName(), "Exception when reading raw answer list.");
            Log.d(this.getClass().getSimpleName(), e.getMessage());
        } finally {
            return answers;
        }
    }
}
