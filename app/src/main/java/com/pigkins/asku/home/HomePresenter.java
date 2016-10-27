package com.pigkins.asku.home;

import com.pigkins.asku.data.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qding on 10/26/16.
 */

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View homeView;

    public HomePresenter(HomeContract.View homeView) {
        this.homeView = homeView;
        this.homeView.setPresenter(this);
    }

    @Override
    public void loadTodayQuestions() {
        List<Question> list = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            for (int day = 1; day <= 30; day++) {
                list.add(new Question(month * 12 + day, month, day, "What is this" + month + " " + day));
            }
        }
        homeView.showTodayQuestions(list);
    }

    @Override
    public void start() {
        loadTodayQuestions();
    }
}
