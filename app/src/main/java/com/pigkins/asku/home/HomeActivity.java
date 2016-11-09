package com.pigkins.asku.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.pigkins.asku.R;
import com.pigkins.asku.data.source.QuestionRepo;

public class HomeActivity extends AppCompatActivity implements AccountChooserFragment.AccountChooserDialogListener, DatePickerFragment.DatePickDialogListener {

    private Toolbar toolbar;
    private HomePresenter homePresenter;

    public static final String ACTIVITY_UID = "USER_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Get user
        int userId = getUserIdFromPref();
        if (userId == -1) {
            DialogFragment dialogFragment = new AccountChooserFragment();
            dialogFragment.show(getSupportFragmentManager(), "accountchooser");
        } else {
            setUpHomeFragment(userId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_pickday) {
            DialogFragment dialogFragment = new DatePickerFragment();
            dialogFragment.show(getSupportFragmentManager(), "pickaday");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onUserSelected(int which) {
        // check index
        int userId = getResources().getIntArray(R.array.userids)[which];
        Log.d(getClass().getSimpleName(), "Getting userid = " + userId);
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(ACTIVITY_UID, userId);
        editor.commit();
        setUpHomeFragment(userId);
    }

    int getUserIdFromPref() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getInt(ACTIVITY_UID, -1);
    }

    void setUpHomeFragment(int userId) {
        // set up fragment
        Log.d(getClass().getSimpleName(), "Creating fragment with userid = " + userId);

        HomeFragment homeFragment =
                (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (homeFragment == null) {
            homeFragment = HomeFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.contentFrame, homeFragment);
            transaction.commit();
        }

        // set up presenter
        homePresenter = new HomePresenter(homeFragment, QuestionRepo.getInstance(getApplicationContext()), userId);
    }

    @Override
    public void onDateSelected(int month, int day) {
        homePresenter.scrollToQuestion(month, day);
    }
}
