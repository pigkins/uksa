package com.pigkins.asku.data.source.local;

import android.content.Context;

import com.pigkins.asku.data.Answer;
import com.pigkins.asku.data.source.AnswerDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qding on 10/31/16.
 */

public class AnswerLocalDataSource implements AnswerDataSource {
    private static AnswerLocalDataSource instance = null;
    private QuestionDBHelper dbHelper;
    private Context context;

    private AnswerLocalDataSource(Context context) {
        this.dbHelper = new QuestionDBHelper(context);
        this.context = context;
    }

    public static AnswerLocalDataSource getInstance(Context context) {
        if (instance == null) {
            instance = new AnswerLocalDataSource(context);
        }
        return instance;
    }


    @Override
    public void loadSingleQuestionAnswers(int questionId, LoadAnswerCallback callback) {
        List<Answer> answerList = new ArrayList<>();
        answerList.add(new Answer(1, 2, 3, 2005, "asfbasfbasfbasfbasfbasfbasfbasfbasfb"));
        answerList.add(new Answer(1, 2, 4, 2005, "xvcxd"));
        answerList.add(new Answer(1, 2, 3, 2014, "bnm,bmn,bmn,bnm"));
        answerList.add(new Answer(1, 2, 4, 2015, "6796789678967"));
        answerList.add(new Answer(1, 2, 3, 2016, "34532523"));
        answerList.add(new Answer(1, 2, 4, 2016, "asdfadsf"));
        callback.onAnswerLoaded(answerList);
    }
}
