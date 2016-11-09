package com.pigkins.asku.answer;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pigkins.asku.R;
import com.pigkins.asku.data.Answer;

import java.util.Calendar;
import java.util.List;

import static com.pigkins.asku.data.Constants.BEAR_USERID;
import static com.pigkins.asku.data.Constants.PIGGY_USERID;

/**
 * Created by qding on 10/28/16.
 */

public class AnswerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Click events
    public interface OnItemClickListener {
        void onSaveButtonClicked(Answer answer);
    }


    private List<Answer> answerList;
    private OnItemClickListener onItemClickListener;
    private int thisYear;
    private int userId;
    private int questionId;
    private boolean answered; // This year's answer


    public static int EDIT_ANSWER_CARD = 1;
    public static int VIEW_ANSWER_CARD = 2;

    public AnswerAdapter(List<Answer> answerList, int questionId, int userId, OnItemClickListener onItemClickListener) {
        this.answerList = answerList;
        this.userId = userId;
        this.questionId = questionId;
        this.onItemClickListener = onItemClickListener;
        this.thisYear = Calendar.getInstance().get(Calendar.YEAR);
        this.answered = false;
    }

    public void setThisYearAnswer(Answer answer) {
        if (!this.answered) {
            int position = this.answerList.size();
            this.answerList.add(answer);
            this.answered = true;
            this.notifyItemChanged(position);
        }
    }

    public void setAnswerMapList(List<Answer> list) {
        this.answerList = list;
        for (Answer answer : list) {
            if (answer.getUserId() == userId && answer.getYear() == thisYear) {
                answered = true;
                break;
            }
        }
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ANSWER_CARD) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_answer_card, parent, false);
            return new AnswerViewHolder(view);
        } else if (viewType == EDIT_ANSWER_CARD) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_answer_edit_card, parent, false);
            return new EditAnswerViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int itemType = getItemViewType(position);
        if (itemType == VIEW_ANSWER_CARD) {
            final Answer answer = answerList.get(position);
            ((AnswerViewHolder) holder).yearTextView.setText(answer.getYearString());
            ((AnswerViewHolder) holder).contentTextView.setText(answer.getContent());
            if (answer.getUserId() == PIGGY_USERID) {
                ((AnswerViewHolder) holder).iconImageView.setImageResource(R.drawable.ic_piggy);
            } else if (answer.getUserId() == BEAR_USERID) {
                ((AnswerViewHolder) holder).iconImageView.setImageResource(R.drawable.ic_bear);
            } else {
                ((AnswerViewHolder) holder).iconImageView.setImageResource(R.drawable.ic_today_black_24dp);
            }
        } else if (itemType == EDIT_ANSWER_CARD) {
            if (this.userId == PIGGY_USERID) {
                ((EditAnswerViewHolder) holder).iconImageView.setImageResource(R.drawable.ic_piggy);
            } else if (this.userId == BEAR_USERID) {
                Log.d("TMP WTF", "WTFFFFFFFFFFFFFFFF");
                ((EditAnswerViewHolder) holder).iconImageView.setImageResource(R.drawable.ic_bear);
            }

            final EditText editText = ((EditAnswerViewHolder) holder).editTextView;
            ((EditAnswerViewHolder) holder).saveImageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Answer answer = new Answer(0,
                            AnswerAdapter.this.questionId,
                            AnswerAdapter.this.userId,
                            AnswerAdapter.this.thisYear,
                            editText.getText().toString()
                    );
                    if (AnswerAdapter.this.onItemClickListener != null) {
                        AnswerAdapter.this.onItemClickListener.onSaveButtonClicked(answer);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        // +1 for the edit view
        int itemCount = answerList.size();
        if (!answered) {
            itemCount += 1;
        }
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == answerList.size()) {
            return EDIT_ANSWER_CARD;
        }
        return VIEW_ANSWER_CARD;
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

    public static class EditAnswerViewHolder extends RecyclerView.ViewHolder {
        TextView yearTextView;
        EditText editTextView;
        ImageView iconImageView;
        ImageView saveImageView;

        EditAnswerViewHolder(View itemView) {
            super(itemView);
            yearTextView = (TextView) itemView.findViewById(R.id.card_answer_year);
            iconImageView = (ImageView) itemView.findViewById(R.id.card_answer_icon);
            editTextView = (EditText) itemView.findViewById(R.id.card_answer_edit);
            saveImageView = (ImageView) itemView.findViewById(R.id.card_answer_save);
        }
    }
}
