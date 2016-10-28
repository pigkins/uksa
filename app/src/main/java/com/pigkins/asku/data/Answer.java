package com.pigkins.asku.data;

/**
 * Created by qding on 10/27/16.
 */

public class Answer {
    private String content;
    private int questionId;
    private int answerId;
    private int year;

    public Answer(int answerId, int questionId, int year, String content) {
        this.content = content;
        this.questionId = questionId;
        this.answerId = answerId;
        this.year = year;
    }

    public String getContent() {
        return content;
    }

    public String getYear() {
        return String.valueOf(year);
    }
}
