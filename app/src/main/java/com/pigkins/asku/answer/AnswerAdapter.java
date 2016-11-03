package com.pigkins.asku.answer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pigkins.asku.R;
import com.pigkins.asku.data.Answer;

import java.util.List;

import static com.pigkins.asku.data.Constants.BEAR_USERID;
import static com.pigkins.asku.data.Constants.PIGGY_USERID;

/**
 * Created by qding on 10/28/16.
 */

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder> {

    private List<Answer> answerList;

    public AnswerAdapter(List<Answer> answerList) {
        this.answerList = answerList;
    }


    public void setAnswerMapList(List<Answer> list) {
        this.answerList = list;
        this.notifyDataSetChanged();
    }

    @Override
    public AnswerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_answer_card, parent, false);
        return new AnswerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnswerViewHolder holder, int position) {
        final Answer answer = answerList.get(position);
        holder.yearTextView.setText(answer.getYearString());
        holder.contentTextView.setText(answer.getContent());
        if (answer.getUserId() == PIGGY_USERID) {
            holder.iconImageView.setImageResource(R.drawable.ic_piggy);
        } else if (answer.getUserId() == BEAR_USERID) {
            holder.iconImageView.setImageResource(R.drawable.ic_bear);
        } else {
            holder.iconImageView.setImageResource(R.drawable.ic_today_black_24dp);
        }
    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }


    // Static classes
    public static class AnswerViewHolder extends RecyclerView.ViewHolder {
        TextView yearTextView;
        TextView contentTextView;
        ImageView iconImageView;

        AnswerViewHolder(View itemView) {
            super(itemView);
            yearTextView = (TextView) itemView.findViewById(R.id.card_answer_year);
            iconImageView = (ImageView) itemView.findViewById(R.id.card_answer_icon);
            contentTextView = (TextView) itemView.findViewById(R.id.card_answer_text);
        }
    }
}
