package com.pigkins.asku.home;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

import com.pigkins.asku.R;

/**
 * Created by qding on 11/7/16.
 */

public class AccountChooserFragment extends AppCompatDialogFragment {

    interface AccountChooserDialogListener {
        void onUserSelected(int which);
    }

    AccountChooserDialogListener accountChooserDialogListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose who you are")
                .setItems(R.array.userlist, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        accountChooserDialogListener.onUserSelected(which);
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        accountChooserDialogListener = (AccountChooserDialogListener) context;
    }
}
