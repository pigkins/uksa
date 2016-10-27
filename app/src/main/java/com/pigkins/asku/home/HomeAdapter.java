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

    private List<Question> questionList;

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
        this.notifyDataSetChanged();
    }

    public HomeAdapter(List<Question> questionList) {
        this.questionList = questionList;
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_question_card, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {
        holder.dateTextView.setText(questionList.get(position).getDate());
        holder.contentTextView.setText(questionList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }


    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView;
        TextView contentTextView;

        QuestionViewHolder(View itemView) {
            super(itemView);
            dateTextView = (TextView) itemView.findViewById(R.id.card_date);
            contentTextView = (TextView) itemView.findViewById(R.id.card_content);
        }
    }
}
