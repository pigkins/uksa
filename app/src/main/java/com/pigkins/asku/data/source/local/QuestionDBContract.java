package com.pigkins.asku.data.source.local;

import android.provider.BaseColumns;

import com.pigkins.asku.data.Question;
import com.pigkins.asku.data.source.QuestionDataSource;

/**
 * Created by qding on 10/29/16.
 */

public final class QuestionDBContract {
    private QuestionDBContract() {}

    public static abstract class QuestionEntry implements BaseColumns {
        public static final String TABLE_NAME = "question";
        public static final String COLUMN_NAME_MONTH = "month";
        public static final String COLUMN_NAME_DAY = "day";
        public static final String COLUMN_NAME_CONTENT = "content";
    }
}
