package com.pigkins.asku.data;

/**
 * Created by qding on 10/27/16.
 */

public class Answer {
    private String content;
    private int questionId;
    private int answerId;
    private int userId;
    private int year;

    public Answer(int answerId, int questionId, int userId, int year, String content) {
        this.content = content;
        this.questionId = questionId;
        this.answerId = answerId;
        this.userId = userId;
        this.year = year;
    }

    public String getContent() {
        return content;
    }

    public int getUserId() {
        return userId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public int getYear() {
        return year;
    }

    public String getYearString() {
        return String.valueOf(year);
    }
}
