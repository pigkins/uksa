package com.pigkins.asku.home;

import com.pigkins.asku.BasePresenter;
import com.pigkins.asku.BaseView;
import com.pigkins.asku.data.Question;

import java.util.List;

/**
 * Created by qding on 10/26/16.
 */

public interface HomeContract {
    interface View extends BaseView<Presenter> {
        void showTodayQuestions(List<Question> questionList);
        void showAnswersOfAQuestion(Question question);
        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void loadTodayQuestions();
    }
}
