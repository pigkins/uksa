package com.pigkins.asku.answer;

import com.pigkins.asku.BasePresenter;
import com.pigkins.asku.BaseView;
import com.pigkins.asku.data.Answer;

import java.util.List;

/**
 * Created by qding on 10/27/16.
 */

public interface AnswerContract {
    interface View extends BaseView<Presenter> {
        void showAnswers(List<Answer> answerList);

        void updateThisYearAnswer(Answer answer);
    }

    interface Presenter extends BasePresenter {
        void loadAnswers();

        void saveAnswer(Answer answer);

        int getQuestionId();

        int getUserId();
    }
}
