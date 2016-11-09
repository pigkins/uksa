package com.pigkins.asku.home;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by qding on 11/8/16.
 */

public class DatePickerFragment extends AppCompatDialogFragment {
    interface DatePickDialogListener {
        void onDateSelected(int month, int day);
    }

    DatePickDialogListener datePickDialogListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d(getClass().getSimpleName(), "Picking year = " + year + " month = " + month + " day = " + dayOfMonth);
                datePickDialogListener.onDateSelected(month + 1, dayOfMonth);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        datePickDialogListener = (DatePickDialogListener) context;
    }
}
