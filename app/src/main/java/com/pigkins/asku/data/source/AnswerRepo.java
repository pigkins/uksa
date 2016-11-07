package com.pigkins.asku.data.source;

import android.content.Context;

import com.pigkins.asku.data.Answer;
import com.pigkins.asku.data.source.local.AnswerLocalDataSource;

/**
 * Created by qding on 10/31/16.
 */

public class AnswerRepo implements AnswerDataSource {
    private AnswerLocalDataSource answerLocalDataSource;

    private static AnswerRepo instance = null;

    private AnswerRepo(AnswerLocalDataSource answerLocalDataSource) {
        this.answerLocalDataSource = answerLocalDataSource;
    }

    public static AnswerRepo getInstance(Context context) {
        if (instance == null) {
            instance = new AnswerRepo(AnswerLocalDataSource.getInstance(context));
        }
        return instance;
    }


    @Override
    public void loadSingleQuestionAnswers(int questionId, LoadAnswerCallback callback) {
        answerLocalDataSource.loadSingleQuestionAnswers(questionId, callback);
    }

    @Override
    public void saveAnswer(Answer answer, SaveAnswerCallback callback) {
        answerLocalDataSource.saveAnswer(answer, callback);
    }
}
