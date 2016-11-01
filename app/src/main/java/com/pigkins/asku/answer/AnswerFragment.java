package com.pigkins.asku.answer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pigkins.asku.R;
import com.pigkins.asku.data.Answer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qding on 10/28/16.
 */

public class AnswerFragment extends Fragment implements AnswerContract.View {

    private AnswerAdapter answerAdapter;
    private RecyclerView recyclerView;
    private AnswerContract.Presenter presenter;

    public AnswerFragment() {}
    public static AnswerFragment newInstance() { return new AnswerFragment(); }


    @Override
    public void showAnswers(List<Answer> answerList) {
        answerAdapter.setAnswerMapList(answerList);
    }

    @Override
    public void setPresenter(AnswerContract.Presenter presenter) {
        this.presenter = presenter;
    }

    // Fragment overrides
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        answerAdapter = new AnswerAdapter(new ArrayList<Answer>(0));
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_answer, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.rv_answer_cards);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(answerAdapter);
        return root;
    }

}
