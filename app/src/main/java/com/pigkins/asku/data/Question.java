package com.pigkins.asku.data;

/**
 * Created by qding on 10/26/16.
 */

public class Question {
    private String content;
    private int questionId;
    private int month;
    private int day;

    public Question(int questionId, int month, int day, String content) {
        this.content = content;
        this.questionId = questionId;
        this.month = month;
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public String getDate() {
        return month + "-" + day;
    }

    public String getContent() {
        return content;
    }
}
