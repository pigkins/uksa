package com.pigkins.asku.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pigkins.asku.R;
import com.pigkins.asku.answer.AnswerActivity;
import com.pigkins.asku.data.Answer;
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
    public void showTodayQuestions(List<Question> questionList) {
        homeAdapter.setQuestionList(questionList);
    }

    @Override
    public void showAnswersOfAQuestion(Question question) {
        Intent intent = new Intent(getContext(), AnswerActivity.class);
        intent.putExtra(AnswerActivity.EXTRA_QUESTION_CONTENT, question.getContent());
        intent.putExtra(AnswerActivity.EXTRA_QUESTION_ID, question.getQuestionId());
        startActivity(intent);
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private HomeAdapter.OnCardClickListener onCardClickListener = new HomeAdapter.OnCardClickListener() {

        @Override
        public void onQuestionClicked(Question question) {
            showAnswersOfAQuestion(question);
        }
    };
}
