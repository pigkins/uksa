package com.pigkins.asku.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pigkins.asku.R;
import com.pigkins.asku.answer.AnswerActivity;
import com.pigkins.asku.data.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qding on 10/26/16.
 */

public class HomeFragment extends Fragment implements HomeContract.View {

    private HomeContract.Presenter presenter;

    private HomeAdapter homeAdapter;

    private RecyclerView recyclerView;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeAdapter = new HomeAdapter(new ArrayList<Question>(0), onCardClickListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.rv_question_cards);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(homeAdapter);
        return root;
    }

    @Override
    public void showTodayQuestions(List<Question> questionList, int userId) {
        homeAdapter.setUserId(userId);
        homeAdapter.setQuestionList(questionList);
    }

    @Override
    public void showAnswersOfAQuestion(Question question, int userId) {
        Intent intent = new Intent(getContext(), AnswerActivity.class);
        intent.putExtra(AnswerActivity.EXTRA_QUESTION_CONTENT, question.getContent());
        intent.putExtra(AnswerActivity.EXTRA_QUESTION_ID, question.getQuestionId());
        intent.putExtra(AnswerActivity.EXTRA_USER_ID, userId);
        startActivity(intent);
    }

    @Override
    public void scrollToParticularQuestion(int month, int day) {
        Log.d(getClass().getSimpleName(), "month = " + month + " day = " + day);
        int position = homeAdapter.getPositionMonthAndDay(month, day);
        Log.d(getClass().getSimpleName(), "position = " + position);
        ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(position, 0);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private HomeAdapter.OnCardClickListener onCardClickListener = new HomeAdapter.OnCardClickListener() {
        @Override
        public void onQuestionClicked(Question question, int userId) {
            showAnswersOfAQuestion(question, userId);
        }
    };
}
