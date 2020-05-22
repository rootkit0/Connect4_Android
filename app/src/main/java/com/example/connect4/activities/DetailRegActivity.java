package com.example.connect4.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

import com.example.connect4.R;
import com.example.connect4.fragments.DetailFragment;

public class DetailRegActivity extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Get intent from other activity
        Bundle data = getIntent().getExtras();
        long id = data.getLong("id");

        Button btn_back = (Button) findViewById(R.id.button10);
        btn_back.setOnClickListener(new goBack());

        DetailFragment detailFrag = (DetailFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentDetail);
        detailFrag.getDetails(id);
    }

    public class goBack implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }
}