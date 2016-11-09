package com.pigkins.asku.answer;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.pigkins.asku.R;
import com.pigkins.asku.data.source.AnswerRepo;

public class AnswerActivity extends AppCompatActivity {

    private Toolbar toolbar;

    public static final String EXTRA_USER_ID = "USER_ID";
    public static final String EXTRA_QUESTION_ID = "QUESTION_ID";
    public static final String EXTRA_QUESTION_CONTENT = "QUESTION_CONTENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        String questionContent = getIntent().getStringExtra(EXTRA_QUESTION_CONTENT);
        int questionId = getIntent().getIntExtra(EXTRA_QUESTION_ID, 0);
        int userId = getIntent().getIntExtra(EXTRA_USER_ID, 0);

        Log.d(this.getClass().getSimpleName(), "Qid = " + questionId + " and Uid = " + userId);

        toolbar = (Toolbar) findViewById(R.id.answer_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(questionContent);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // set up fragment
        AnswerFragment answerFragment = (AnswerFragment) getSupportFragmentManager().findFragmentById(R.id.answer_fragment);
        if (answerFragment == null) {
            answerFragment = AnswerFragment.newInstance(questionId, userId);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.answer_fragment, answerFragment);
            transaction.commit();
        }

        new AnswerPresenter(answerFragment, questionId, userId, AnswerRepo.getInstance(getApplicationContext()));

    }
}
