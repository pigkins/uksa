package com.pigkins.asku.data.source.local;

import android.content.Context;
import android.os.Handler;

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

    private QuestionLocalDataSource(Context context) {
        dbHelper = new QuestionDBHelper(context);
    }

    public static QuestionLocalDataSource getInstance(Context context) {
        if (instance == null) {
            instance = new QuestionLocalDataSource(context);
        }
        return instance;
    }


    @Override
    public void loadQuestions(final LoadQuestionsCallback callback) {
        final Handler handler = new Handler();
        Thread readDBThread = new Thread(new Runnable() {
            @Override
            public void run() {
                final List<Question> list = new ArrayList<>();
                for (int month = 1; month <= 12; month++) {
                    for (int day = 1; day <= 30; day++) {
                        list.add(new Question(month * 12 + day, month, day, "What is this" + month + " " + day));
                    }
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onQuestionsLoaded(list);
                    }
                });
            }
        });
        readDBThread.start();
    }
}
