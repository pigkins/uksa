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

    private static final String ARGUMENT_QID = "QUESTION_ID";
    private static final String ARGUMENT_UID = "USER_ID";

    private AnswerAdapter answerAdapter;
    private RecyclerView recyclerView;
    private AnswerContract.Presenter presenter;


    public AnswerFragment() {}

    public static AnswerFragment newInstance(int questionId, int userId) {
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_QID, questionId);
        arguments.putInt(ARGUMENT_UID, userId);
        AnswerFragment fragment = new AnswerFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void showAnswers(List<Answer> answerList) {
        answerAdapter.setAnswerMapList(answerList);
    }

    @Override
    public void updateThisYearAnswer(Answer answer) {
        answerAdapter.setThisYearAnswer(answer);
    }

    @Override
    public void setPresenter(AnswerContract.Presenter presenter) {
        // TODO(qding): need to check null.
        this.presenter = presenter;
    }

    // Fragment overrides
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int questionId = this.getArguments().getInt(ARGUMENT_QID);
        int userId = this.getArguments().getInt(ARGUMENT_UID);
        answerAdapter = new AnswerAdapter(new ArrayList<Answer>(0), questionId, userId, onItemClickListener);
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

    private AnswerAdapter.OnItemClickListener onItemClickListener = new AnswerAdapter.OnItemClickListener() {
        @Override
        public void onSaveButtonClicked(Answer answer) {
            if (AnswerFragment.this.presenter != null && answer != null) {
                AnswerFragment.this.presenter.saveAnswer(answer);
            }
        }
    };

}
