package com.example.connect4.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

import com.example.connect4.R;

public class AccessBDActivity extends FragmentActivity implements QueryFragment.OnClickListener {

    private QueryFragment queryFrag;
    private DetailFragment detailFrag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        Button btn_backMenu = (Button) findViewById(R.id.button9);
        btn_backMenu.setOnClickListener(new backMenu());
        //Query fragment
        this.queryFrag = (QueryFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentQuery);
        this.queryFrag.setClickListener(this);
        //Detail fragment
        this.detailFrag = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentDetail);
    }

    @Override
    public void onItemClick(long id) {
        if(this.detailFrag != null && this.detailFrag.isInLayout()) {
            //Show details on same activity
            detailFrag.getDetails(id);
        }
        else {
            //Show details on new activity
            Intent i = new Intent(this, DetailRegActivity.class);
            i.putExtra("id", id);
            startActivity(i);
        }
    }

    public class backMenu implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }
}
