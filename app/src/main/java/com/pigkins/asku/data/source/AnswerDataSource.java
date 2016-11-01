package com.pigkins.asku.data.source;

import com.pigkins.asku.data.Answer;

import java.util.List;

/**
 * Created by qding on 10/31/16.
 */

public interface AnswerDataSource {
    interface LoadAnswerCallback {
        void onDataNotAvailable();

        void onAnswerLoaded(List<Answer> answerList);
    }

    void loadSingleQuestionAnswers(int questionId, LoadAnswerCallback callback);
}
