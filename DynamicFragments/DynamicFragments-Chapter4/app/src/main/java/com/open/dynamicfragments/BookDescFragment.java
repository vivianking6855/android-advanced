package com.open.dynamicfragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link BookDescFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookDescFragment extends Fragment {
    private String[] mBookDescriptions;
    private TextView mBookDescriptionTextView;

    // Book index argument name
    public static final String BOOK_INDEX = "book index";
    // Book index default value
    private static final int BOOK_INDEX_NOT_SET = -1;

    public BookDescFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookDescFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookDescFragment newInstance(String param1, String param2) {
        BookDescFragment fragment = new BookDescFragment();
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
        View viewHierarchy = inflater.inflate(R.layout.fragment_book_desc, container, false);

        initView(viewHierarchy);

        return viewHierarchy;
    }

    private void initView(View viewHierarchy) {
        // Load array of book descriptions
        mBookDescriptions = getResources().getStringArray(R.array.bookDescriptions);
        // Get reference to book description text view
        mBookDescriptionTextView = (TextView)
                viewHierarchy.findViewById(R.id.bookDescription);

        // Retrieve the book index if attached
        Bundle args = getArguments();
        int bookIndex = args != null ?
                args.getInt(BOOK_INDEX, BOOK_INDEX_NOT_SET) :
                BOOK_INDEX_NOT_SET;

        // If we find the book index, use it
        if (bookIndex != BOOK_INDEX_NOT_SET) {
            setBook(bookIndex);
        }
    }

    public void setBook(int bookIndex) {
        if (bookIndex < 0 || bookIndex >= mBookDescriptions.length) {
            return;
        }
        // Lookup the book description
        String bookDescription = mBookDescriptions[bookIndex];
        // Display it
        mBookDescriptionTextView.setText(bookDescription);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}