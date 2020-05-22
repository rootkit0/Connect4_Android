package com.example.connect4.fragments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.fragment.app.ListFragment;

import com.example.connect4.R;
import com.example.connect4.activities.DBHelper;

public class QueryFragment extends ListFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //List the DB matches
        DBHelper logDbHelper = new DBHelper(getActivity(), "Matches", null, 3);
        SQLiteDatabase db = logDbHelper.getReadableDatabase();
        String[] dbColumns = new String[]{"_id", "nickname", "date", "result"};
        Cursor cursor = db.query("Matches", dbColumns, null, null, null, null, null);
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.cursor_adapter,
                cursor,
                new String[] {"nickname", "date", "result"},
                new int[] {R.id.textView11, R.id.textView12, R.id.textView13},
                0);
        this.setListAdapter(cursorAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        this.listener.onItemClick(id);
    }

    OnClickListener listener;

    public interface OnClickListener {
        void onItemClick(long id);
    }

    public void setClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnClickListener) context;
        }
        catch(ClassCastException e) {
            throw new ClassCastException(getActivity().toString());
        }
    }
}
