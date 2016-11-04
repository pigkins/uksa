package com.pigkins.asku.answer;

import android.util.Log;

import com.pigkins.asku.data.Answer;
import com.pigkins.asku.data.source.AnswerDataSource;
import com.pigkins.asku.data.source.AnswerRepo;

import java.util.List;

/**
 * Created by qding on 10/28/16.
 */

public class AnswerPresenter implements AnswerContract.Presenter {

    private AnswerContract.View answerView;
    private int questionId;
    private int userId;
    private AnswerRepo answerRepo;


    public AnswerPresenter(AnswerContract.View answerView, int questionId, int userId, AnswerRepo answerRepo) {
        this.answerRepo = answerRepo;
        this.questionId = questionId;
        this.userId = userId;
        this.answerView = answerView;
        this.answerView.setPresenter(this);
    }

    @Override
    public int getQuestionId() {
        return questionId;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public void loadAnswers() {
        answerRepo.loadSingleQuestionAnswers(questionId, new AnswerDataSource.LoadAnswerCallback() {
            @Override
            public void onDataNotAvailable() {

            }

            @Override
            public void onAnswerLoaded(List<Answer> answerList) {
                answerView.showAnswers(answerList);
            }
        });
    }

    @Override
    public void saveAnswer(Answer answer) {
        Log.d(this.getClass().getSimpleName(), "Saving Answer = " + answer.toString());
    }

    @Override
    public void start() {
        loadAnswers();
    }
}
