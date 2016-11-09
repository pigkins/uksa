package com.pigkins.asku.home;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pigkins.asku.R;
import com.pigkins.asku.data.Question;

import java.util.List;

/**
 * Created by qding on 10/26/16.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.QuestionViewHolder> {

    public interface OnCardClickListener {
        void onQuestionClicked(Question question, int userId);
    }

    private int userId;
    private List<Question> questionList;
    private OnCardClickListener onCardClickListener;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
        this.notifyDataSetChanged();
    }

    public HomeAdapter(List<Question> questionList, OnCardClickListener onCardClickListener) {
        this.userId = 0;
        this.questionList = questionList;
        this.onCardClickListener = onCardClickListener;
    }

    public int getPositionMonthAndDay(int month, int day) {
        int pos = 0;
        for (Question question : questionList) {
            if (question.getDay() == day && question.getMonth() == month) {
                return pos;
            }
            pos += 1;
        }
        return pos;
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_question_card, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {
        final Question question = questionList.get(position);
        holder.dayTextView.setText(question.getDayString());
        holder.monthTextView.setText(question.getMonthString());
        holder.contentTextView.setText(question.getContent());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HomeAdapter.this.onCardClickListener != null) {
                    HomeAdapter.this.onCardClickListener.onQuestionClicked(question, userId);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }


    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView dayTextView;
        TextView monthTextView;
        TextView contentTextView;

        QuestionViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.question_card);
            monthTextView = (TextView) itemView.findViewById(R.id.card_month);
            dayTextView = (TextView) itemView.findViewById(R.id.card_day);
            contentTextView = (TextView) itemView.findViewById(R.id.card_content);
        }
    }
}
