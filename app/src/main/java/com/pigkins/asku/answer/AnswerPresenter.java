package com.pigkins.asku.answer;

import com.pigkins.asku.data.Answer;
import com.pigkins.asku.data.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qding on 10/28/16.
 */

public class AnswerPresenter implements AnswerContract.Presenter {

    private AnswerContract.View answerView;
    private int questionId;

    public AnswerPresenter(AnswerContract.View answerView, int questionId) {
        this.questionId = questionId;
        this.answerView = answerView;
        this.answerView.setPresenter(this);
    }

    @Override
    public void loadAnswers() {
        List<Answer> answerList = new ArrayList<>();
        for (int year = 2000; year <= 2016; year++) {
            answerList.add(new Answer(year, questionId, year, "hahahah "+year));
        }
        answerView.showAnswers(answerList);
    }

    @Override
    public void start() {
        loadAnswers();
    }
}
