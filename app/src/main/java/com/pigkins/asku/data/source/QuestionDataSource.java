package com.pigkins.asku.data.source;

import com.pigkins.asku.data.Question;

import java.util.List;

/**
 * Created by qding on 10/29/16.
 */

public interface QuestionDataSource {
    interface LoadQuestionsCallback {

        void onQuestionsLoaded(List<Question> questionList);

        void onDataNotAvailable();
    }

    void loadQuestions(LoadQuestionsCallback callback);
}
