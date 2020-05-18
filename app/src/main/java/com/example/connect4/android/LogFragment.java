package com.example.connect4.android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.connect4.R;

public class LogFragment extends Fragment {

    private Bundle data;
    TextView logs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.log_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Init the log content
        this.logs = getView().findViewById(R.id.logs);
    }

    public void setParameters(Bundle data) {
        this.data = data;
    }

    public void addLog() {

    }
}
