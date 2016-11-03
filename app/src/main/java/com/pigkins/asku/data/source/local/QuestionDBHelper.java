package com.pigkins.asku.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.pigkins.asku.R;
import com.pigkins.asku.data.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qding on 10/29/16.
 */

public class QuestionDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 11;

    public static final String DATABASE_NAME = "Questions.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String INTEGER_TYPE = " INTEGER";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + QuestionDBContract.QuestionEntry.TABLE_NAME + " (" +
                    QuestionDBContract.QuestionEntry._ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                    QuestionDBContract.QuestionEntry.COLUMN_NAME_QID + INTEGER_TYPE + COMMA_SEP +
                    QuestionDBContract.QuestionEntry.COLUMN_NAME_MONTH + INTEGER_TYPE + COMMA_SEP +
                    QuestionDBContract.QuestionEntry.COLUMN_NAME_DAY + INTEGER_TYPE + COMMA_SEP +
                    QuestionDBContract.QuestionEntry.COLUMN_NAME_CONTENT + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + QuestionDBContract.QuestionEntry.TABLE_NAME;

    private Context context;

    public QuestionDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

        // Init db with questions stored in the resource file.
        updateQuestionDB(db, readQuestionFromResource(context));
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }

    public void updateQuestionDB(SQLiteDatabase db, List<Question> questionList) {
        Log.d(this.getClass().getSimpleName(), "Update Questions read from the resource file.");
        db.beginTransaction();
        for (Question question : questionList) {
            ContentValues insertValues = new ContentValues();
            insertValues.put(QuestionDBContract.QuestionEntry.COLUMN_NAME_QID, question.getQuestionId());
            insertValues.put(QuestionDBContract.QuestionEntry.COLUMN_NAME_MONTH, question.getMonth());
            insertValues.put(QuestionDBContract.QuestionEntry.COLUMN_NAME_DAY, question.getDay());
            insertValues.put(QuestionDBContract.QuestionEntry.COLUMN_NAME_CONTENT, question.getContent());
            db.insert(QuestionDBContract.QuestionEntry.TABLE_NAME, null, insertValues);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public List<Question> readQuestionFromResource(Context context) {
        Log.d(this.getClass().getSimpleName(), "Read Questions from the resource file.");
        List<Question> questions = new ArrayList<>();
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.questions);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] tokens = line.split("\\|");
                Question question = new Question(Integer.parseInt(tokens[0]),
                        Integer.parseInt(tokens[1]),
                        Integer.parseInt(tokens[2]),
                        tokens[3]);
                questions.add(question);
            }
        } catch (IOException e) {
            Log.d(this.getClass().getSimpleName(), "Exception when reading raw question list.");
            Log.d(this.getClass().getSimpleName(), e.getMessage());
        } finally {
            return questions;
        }
    }
}
