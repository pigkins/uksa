package com.pigkins.asku.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.util.Log;

import com.pigkins.asku.R;
import com.pigkins.asku.data.Question;
import com.pigkins.asku.data.source.QuestionDataSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
                final List<Question> questions;
                List<Question> listFromDB = readQuestionFromDB();
                if (listFromDB == null) {
                    Log.d(this.getClass().getSimpleName(), "DB is empty, reading question from resource.");
                    questions = readQuestionFromResource(context);
                    updateQuestionDB(questions);
                } else {
                    questions = listFromDB;
                }
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
                QuestionDBContract.QuestionEntry._ID,
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
            Question question = new Question(c.getInt(c.getColumnIndexOrThrow(QuestionDBContract.QuestionEntry._ID)),
                    c.getInt(c.getColumnIndexOrThrow(QuestionDBContract.QuestionEntry.COLUMN_NAME_MONTH)),
                    c.getInt(c.getColumnIndexOrThrow(QuestionDBContract.QuestionEntry.COLUMN_NAME_DAY)),
                    c.getString(c.getColumnIndexOrThrow(QuestionDBContract.QuestionEntry.COLUMN_NAME_CONTENT)));
            questions.add(question);
        } while (c.moveToNext());
        c.close();
        db.close();
        return questions;
    }

    public void updateQuestionDB(List<Question> questionList) {
        Log.d(this.getClass().getSimpleName(), "Update Questions read from the resource file.");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        for (Question question: questionList) {
            ContentValues insertValues = new ContentValues();
            insertValues.put(QuestionDBContract.QuestionEntry.COLUMN_NAME_MONTH, question.getMonth());
            insertValues.put(QuestionDBContract.QuestionEntry.COLUMN_NAME_DAY, question.getDay());
            insertValues.put(QuestionDBContract.QuestionEntry.COLUMN_NAME_CONTENT, question.getContent());
            db.insert(QuestionDBContract.QuestionEntry.TABLE_NAME, null, insertValues);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
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
                Question question  = new Question(0, Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), tokens[2]);
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
