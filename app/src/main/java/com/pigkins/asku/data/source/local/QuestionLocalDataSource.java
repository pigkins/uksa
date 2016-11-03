package com.pigkins.asku.data.source.local;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.util.Log;

import com.pigkins.asku.data.Question;
import com.pigkins.asku.data.source.QuestionDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qding on 10/29/16.
 */

public class QuestionLocalDataSource implements QuestionDataSource {

    private static QuestionLocalDataSource instance = null;
    private QuestionDBHelper dbHelper;
    private Context context;
    private List<Question> questionCache;

    private QuestionLocalDataSource(Context context) {
        this.dbHelper = new QuestionDBHelper(context);
        this.context = context;
        questionCache = null;
    }

    public static QuestionLocalDataSource getInstance(Context context) {
        if (instance == null) {
            instance = new QuestionLocalDataSource(context);
        }
        return instance;
    }

    @Override
    public void loadQuestions(final LoadQuestionsCallback callback) {
        // Read From Cache if existed.
        if (questionCache != null) {
            callback.onQuestionsLoaded(questionCache);
            return;
        }

        // Go through DB in another thread.
        final Handler handler = new Handler();
        Thread readDBThread = new Thread(new Runnable() {
            @Override
            public void run() {
                final List<Question> questions = readQuestionFromDB();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        questionCache = questions;
                        callback.onQuestionsLoaded(questions);
                    }
                });
            }
        });
        readDBThread.start();
    }

    public List<Question> readQuestionFromDB() {
        Log.d(this.getClass().getSimpleName(), "Read  Questions from SQLite.");

        String[] projection = {
                QuestionDBContract.QuestionEntry.COLUMN_NAME_QID,
                QuestionDBContract.QuestionEntry.COLUMN_NAME_MONTH,
                QuestionDBContract.QuestionEntry.COLUMN_NAME_DAY,
                QuestionDBContract.QuestionEntry.COLUMN_NAME_CONTENT
        };

        String sortOrder = QuestionDBContract.QuestionEntry.COLUMN_NAME_MONTH + " ASC," +
                QuestionDBContract.QuestionEntry.COLUMN_NAME_DAY + " ASC";

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query(QuestionDBContract.QuestionEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder);


        // Empty
        if (!c.moveToFirst()) {
            return null;
        }

        List<Question> questions = new ArrayList<>();
        do {
            Question question = new Question(c.getInt(c.getColumnIndexOrThrow(QuestionDBContract.QuestionEntry.COLUMN_NAME_QID)),
                    c.getInt(c.getColumnIndexOrThrow(QuestionDBContract.QuestionEntry.COLUMN_NAME_MONTH)),
                    c.getInt(c.getColumnIndexOrThrow(QuestionDBContract.QuestionEntry.COLUMN_NAME_DAY)),
                    c.getString(c.getColumnIndexOrThrow(QuestionDBContract.QuestionEntry.COLUMN_NAME_CONTENT)));
            questions.add(question);
        } while (c.moveToNext());
        c.close();
        db.close();
        return questions;
    }
}
