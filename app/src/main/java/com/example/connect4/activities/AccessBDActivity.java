package com.example.connect4.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

import com.example.connect4.R;
import com.example.connect4.fragments.DetailFragment;
import com.example.connect4.fragments.QueryFragment;

public class AccessBDActivity extends FragmentActivity implements QueryFragment.OnClickListener {
    private QueryFragment queryFrag;
    private DetailFragment detailFrag;
    private long id;

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
        this.id = id;
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("queryId", this.id);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.id = savedInstanceState.getLong("queryId");
        if(this.detailFrag != null && this.detailFrag.isInLayout()) {
            onItemClick(this.id);
        }
    }
}
