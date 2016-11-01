package com.pigkins.asku.answer;

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
    private AnswerRepo answerRepo;

    public AnswerPresenter(AnswerContract.View answerView, int questionId, AnswerRepo answerRepo) {
        this.answerRepo = answerRepo;
        this.questionId = questionId;
        this.answerView = answerView;
        this.answerView.setPresenter(this);
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
    public void start() {
        loadAnswers();
    }
}
