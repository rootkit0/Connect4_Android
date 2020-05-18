package com.example.connect4.android;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.example.connect4.R;

public class GameActivity extends FragmentActivity implements GridFragment.OnChangeListener {

    private GridFragment gridFragment;
    private LogFragment logFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Grid fragment
        this.gridFragment = (GridFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentGame);
        this.gridFragment.setChangeListener(this);
        this.logFragment = (LogFragment)getSupportFragmentManager().findFragmentById(R.id.logFragment);
        this.logFragment.setParameters(getIntent().getExtras());
    }

    public Bundle getParameters() {
        return this.getIntent().getExtras();
    }

    @Override
    public void onChange() {
        if(this.logFragment != null && this.logFragment.isInLayout()) {
            this.logFragment.addLog();
        }
    }
}
