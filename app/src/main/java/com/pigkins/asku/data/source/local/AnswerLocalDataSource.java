package com.pigkins.asku.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.util.Log;
import android.util.LruCache;

import com.pigkins.asku.data.Answer;
import com.pigkins.asku.data.source.AnswerDataSource;

import java.util.ArrayList;
import java.util.List;

import static com.pigkins.asku.data.source.local.AnswerDBContract.AnswerEntry.TABLE_NAME;

/**
 * Created by qding on 10/31/16.
 */

public class AnswerLocalDataSource implements AnswerDataSource {
    private static AnswerLocalDataSource instance = null;
    private AnswerDBHelper dbHelper;
    private LruCache<Integer, List<Answer>> answerLruCache;
    private static int MAX_LRU_SIZE = 10000;

    private AnswerLocalDataSource(Context context) {
        this.dbHelper = new AnswerDBHelper(context);
        this.answerLruCache = new LruCache<>(MAX_LRU_SIZE);
    }

    public static AnswerLocalDataSource getInstance(Context context) {
        if (instance == null) {
            instance = new AnswerLocalDataSource(context);
        }
        return instance;
    }

    @Override
    public void loadSingleQuestionAnswers(final int questionId, final LoadAnswerCallback callback) {
        List<Answer> cached = answerLruCache.get(questionId);
        if (cached != null) {
            callback.onAnswerLoaded(cached);
            return;
        }

        // Go through DB in another thread.
        final Handler handler = new Handler();
        Thread readDBThread = new Thread(new Runnable() {
            @Override
            public void run() {
                final List<Answer> answers = readAnswerFromDB(questionId);
                if (answers != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (answerLruCache.get(questionId) == null) {
                                answerLruCache.put(questionId, answers);
                            }
                            callback.onAnswerLoaded(answers);
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onDataNotAvailable();
                        }
                    });
                }
            }
        });
        readDBThread.start();
    }

    @Override
    public void saveAnswer(final Answer answer, final SaveAnswerCallback callback) {
        final Handler handler = new Handler();
        Thread writeDBThread = new Thread(new Runnable() {
            @Override
            public void run() {
                final long answerId = saveAnswerToDB(answer);
                if (answerId != -1) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSaveSuccess(answerId);
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSaveFailure();
                        }
                    });
                }
            }
        });
        writeDBThread.start();
    }

    public long saveAnswerToDB(Answer answer) {
        Log.d(this.getClass().getSimpleName(), "Save answer to SQLite.");
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.beginTransaction();

        ContentValues values = new ContentValues();
        values.put(AnswerDBContract.AnswerEntry.COLUMN_NAME_QID, answer.getQuestionId());
        values.put(AnswerDBContract.AnswerEntry.COLUMN_NAME_UID, answer.getUserId());
        values.put(AnswerDBContract.AnswerEntry.COLUMN_NAME_YEAR, answer.getYear());
        values.put(AnswerDBContract.AnswerEntry.COLUMN_NAME_CONTENT, answer.getContent());

        long answerId = db.insert(AnswerDBContract.AnswerEntry.TABLE_NAME, null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        return answerId;
    }

    public List<Answer> readAnswerFromDB(int questionId) {
        Log.d(this.getClass().getSimpleName(), "Read Answer of question " + questionId + " from SQLite.");

        String[] projection = {
                AnswerDBContract.AnswerEntry._ID,
                AnswerDBContract.AnswerEntry.COLUMN_NAME_QID,
                AnswerDBContract.AnswerEntry.COLUMN_NAME_UID,
                AnswerDBContract.AnswerEntry.COLUMN_NAME_YEAR,
                AnswerDBContract.AnswerEntry.COLUMN_NAME_CONTENT
        };

        String sortOrder = AnswerDBContract.AnswerEntry.COLUMN_NAME_YEAR + " ASC," +
                AnswerDBContract.AnswerEntry.COLUMN_NAME_UID + " ASC";

        String selection = AnswerDBContract.AnswerEntry.COLUMN_NAME_QID + " = ?";
        String[] selectionArg = {String.valueOf(questionId)};

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query(TABLE_NAME,
                projection,
                selection,
                selectionArg,
                null,
                null,
                sortOrder);


        // Empty
        if (!c.moveToFirst()) {
            return null;
        }

        List<Answer> answerList = new ArrayList<>();
        do {
            Answer answer = new Answer(c.getInt(c.getColumnIndexOrThrow(AnswerDBContract.AnswerEntry._ID)),
                    c.getInt(c.getColumnIndexOrThrow(AnswerDBContract.AnswerEntry.COLUMN_NAME_QID)),
                    c.getInt(c.getColumnIndexOrThrow(AnswerDBContract.AnswerEntry.COLUMN_NAME_UID)),
                    c.getInt(c.getColumnIndexOrThrow(AnswerDBContract.AnswerEntry.COLUMN_NAME_YEAR)),
                    c.getString(c.getColumnIndexOrThrow(AnswerDBContract.AnswerEntry.COLUMN_NAME_CONTENT)));
            answerList.add(answer);
        } while (c.moveToNext());
        c.close();
        db.close();
        return answerList;
    }

}
