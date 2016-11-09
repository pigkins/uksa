package com.pigkins.asku.home;

import com.pigkins.asku.data.Question;
import com.pigkins.asku.data.source.QuestionDataSource;
import com.pigkins.asku.data.source.QuestionRepo;

import java.util.List;

/**
 * Created by qding on 10/26/16.
 */

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View homeView;
    private QuestionRepo questionRepo;
    private int userId;

    public HomePresenter(HomeContract.View homeView, QuestionRepo questionRepo, int userId) {
        this.homeView = homeView;
        this.homeView.setPresenter(this);
        this.questionRepo = questionRepo;
        this.userId = userId;
    }

    @Override
    public void loadTodayQuestions() {
        questionRepo.loadQuestions(new QuestionDataSource.LoadQuestionsCallback() {
            @Override
            public void onQuestionsLoaded(List<Question> questionList) {
                if (homeView.isActive()) {
                    homeView.showTodayQuestions(questionList, userId);
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void scrollToQuestion(int month, int day) {
        homeView.scrollToParticularQuestion(month, day);
    }

    @Override
    public void start() {
        loadTodayQuestions();
    }
}
