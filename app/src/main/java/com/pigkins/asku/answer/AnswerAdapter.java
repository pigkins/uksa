package com.pigkins.asku.answer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pigkins.asku.R;
import com.pigkins.asku.data.Answer;

import java.util.List;

/**
 * Created by qding on 10/28/16.
 */

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder> {

    private List<Answer> answerList;

    public AnswerAdapter(List<Answer> answerList) {
        this.answerList = answerList;
    }

    @Override
    public AnswerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_answer_card, parent, false);
        return new AnswerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnswerViewHolder holder, int position) {
        holder.answerTextView.setText(answerList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }

    public static class AnswerViewHolder extends RecyclerView.ViewHolder {
        TextView answerTextView;
        AnswerViewHolder(View itemView) {
            super(itemView);
            answerTextView = (TextView) itemView.findViewById(R.id.card_answer_text);
        }
    }

    public void setAnswerList(List<Answer> list) {
        this.answerList = list;
        this.notifyDataSetChanged();
    }
}
