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

    private List<AnswerCardContent> answerCardContentList;

    public AnswerAdapter(List<AnswerCardContent> answerCardContentList) {
        this.answerCardContentList = answerCardContentList;
    }


    public void setAnswerMapList(List<AnswerCardContent> list) {
        this.answerCardContentList = list;
        this.notifyDataSetChanged();
    }

    @Override
    public AnswerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_answer_card, parent, false);
        return new AnswerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnswerViewHolder holder, int position) {
        final AnswerCardContent answerCardContent = answerCardContentList.get(position);
        holder.yearTextView.setText(String.valueOf(answerCardContent.year));
        holder.answerBearyTextView.setText(answerCardContent.bearyAnswer.getContent());
        holder.answerPiggyTextView.setText(answerCardContent.piggyAnswer.getContent());
    }

    @Override
    public int getItemCount() {
        return answerCardContentList.size();
    }


    // Static classes
    public static class AnswerViewHolder extends RecyclerView.ViewHolder {
        TextView yearTextView;
        TextView answerPiggyTextView;
        TextView answerBearyTextView;

        AnswerViewHolder(View itemView) {
            super(itemView);
            yearTextView = (TextView) itemView.findViewById(R.id.answer_year);
            answerPiggyTextView = (TextView) itemView.findViewById(R.id.card_answer_piggy_text);
            answerBearyTextView = (TextView) itemView.findViewById(R.id.card_answer_bear_text);
        }
    }


    public static class AnswerCardContent {
        public int year;
        public Answer piggyAnswer;
        public Answer bearyAnswer;

        AnswerCardContent(int year, Answer piggyAnswer, Answer bearyAnswer) {
            this.year = year;
            this.bearyAnswer = bearyAnswer;
            this.piggyAnswer = piggyAnswer;
        }
    }

}
