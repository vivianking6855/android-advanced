package com.open.dynamicfragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link BookListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookListFragment extends Fragment implements
        RadioGroup.OnCheckedChangeListener {

    public BookListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookListFragment newInstance(String param1, String param2) {
        BookListFragment fragment = new BookListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewHierarchy = inflater.inflate(R.layout.fragment_book_list, container, false);

        initView(viewHierarchy);

        return viewHierarchy;
    }

    private void initView(View viewHierarchy) {
        // Connect the listener to the radio group
        RadioGroup group = (RadioGroup)
                viewHierarchy.findViewById(R.id.bookSelectGroup);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                // Translate radio button to book index
                int bookIndex = translateIdToIndex(checkedId);

                // Get parent Activity and send notification
                OnSelectedBookChangeListener listener =
                        (OnSelectedBookChangeListener) getActivity();
                if (listener != null) {
                    listener.onSelectedBookChanged(bookIndex);
                }
            }
        });
    }

    private int translateIdToIndex(int id) {
        int index = -1;
        switch (id) {
            case R.id.dynamicUiBook:
                index = 0;
                break;
            case R.id.android4NewBook:
                index = 1;
                break;
            default:
                index = 0;
                break;
        }
        return index;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

    }

    public interface OnSelectedBookChangeListener {
        void onSelectedBookChanged(int bookIndex);
    }
}
