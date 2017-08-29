package com.open.dynamicfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by vivian on 2017/8/28.
 */

public class BookListFragment extends ListFragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] bookTitles =
                getResources().getStringArray(R.array.bookTitles);
        ArrayAdapter<String> bookTitlesAdapter =
                new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1, bookTitles);
        setListAdapter(bookTitlesAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        // Access the Activity and cast to the inteface
        OnSelectedBookChangeListener listener =
                (OnSelectedBookChangeListener)getActivity();

        // Notify the Activity of the selection
        listener.onSelectedBookChanged(position);
    }

    public interface OnSelectedBookChangeListener {
        void onSelectedBookChanged(int bookIndex);
    }
}
