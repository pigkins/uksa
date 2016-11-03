package com.pigkins.asku.data.source.local;

import android.provider.BaseColumns;

/**
 * Created by qding on 10/29/16.
 */

public final class AnswerDBContract {
    private AnswerDBContract() {
    }

    public static abstract class AnswerEntry implements BaseColumns {
        public static final String TABLE_NAME = "answer";
        public static final String COLUMN_NAME_AID = "answerid";
        public static final String COLUMN_NAME_QID = "questionid";
        public static final String COLUMN_NAME_UID = "userid";
        public static final String COLUMN_NAME_YEAR = "year";
        public static final String COLUMN_NAME_CONTENT = "content";
    }
}
