package com.example.connect4;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

public class GameActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Grid fragment
        GridFragment gridFrag = (GridFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentGame);
    }

    public Bundle getParameters() {

        return this.getIntent().getExtras();
    }
}
