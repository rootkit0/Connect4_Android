package com.example.connect4.activities;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.example.connect4.R;
import com.example.connect4.fragments.GridFragment;
import com.example.connect4.fragments.LogFragment;
import com.example.connect4.logic.Position;

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
        //Log fragment
        this.logFragment = (LogFragment)getSupportFragmentManager().findFragmentById(R.id.logFragment);
    }

    @Override
    public void onChange(Position pos, String timer_value, String start, String end) {
        if(this.logFragment != null && this.logFragment.isInLayout()) {
            this.logFragment.addPosition(pos);
            this.logFragment.addTime(timer_value, start, end);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("game", this.gridFragment.getGameState());
        if(this.logFragment != null && this.logFragment.isInLayout()) {
            outState.putString("logs", this.logFragment.getLogs());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.gridFragment.onRestoreInstance(savedInstanceState);
        if(this.logFragment != null && this.logFragment.isInLayout()) {
            this.logFragment.onRestoreInstance(savedInstanceState);
        }
    }
}
