package com.pigkins.asku.data;

import java.text.DateFormatSymbols;

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

    public String getMonth() {
        return DateFormatSymbols.getInstance().getMonths()[month-1].substring(0,3);
    }

    public String getDay() {
        return String.valueOf(day);
    }

    public String getContent() {
        return content;
    }

    public int getQuestionId() {
        return questionId;
    }
}
