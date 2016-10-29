package com.pigkins.asku.data.source;

import android.content.Context;

import com.pigkins.asku.data.Question;
import com.pigkins.asku.data.source.local.QuestionLocalDataSource;

/**
 * Created by qding on 10/29/16.
 */

public class QuestionRepo implements QuestionDataSource {

    private QuestionLocalDataSource questionLocalDataSource;

    private static QuestionRepo instance = null;

    private QuestionRepo(QuestionLocalDataSource questionLocalDataSource) {
        this.questionLocalDataSource = questionLocalDataSource;
    }

    public static QuestionRepo getInstance(Context context) {
        if (instance == null) {
            instance = new QuestionRepo(QuestionLocalDataSource.getInstance(context));
        }
        return instance;
    }

    @Override
    public void loadQuestions(LoadQuestionsCallback callback) {
        questionLocalDataSource.loadQuestions(callback);
    }
}
