package com.pigkins.asku.answer;

import com.pigkins.asku.BasePresenter;
import com.pigkins.asku.BaseView;
import com.pigkins.asku.data.Answer;
import com.pigkins.asku.data.Question;

import java.util.List;

/**
 * Created by qding on 10/27/16.
 */

public interface AnswerContract {
    interface View extends BaseView<Presenter> {
        void showQuestion(Question question);
        void showAnswers(List<Answer> answerList);
    }

    interface Presenter extends BasePresenter {
        void loadAnswers(Question question);
    }
}