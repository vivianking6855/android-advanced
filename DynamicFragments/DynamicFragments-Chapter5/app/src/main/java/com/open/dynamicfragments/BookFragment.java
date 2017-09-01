package com.open.dynamicfragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Book Fragment.
 */
public class BookFragment extends Fragment {
    private static final String BOOK_TITLE = "title";
    private static final String BOOK_DESCRIPTIONS = "des";

    private ImageView imageView;
    private TextView title;
    private TextView des;

    public BookFragment() {
        // Required empty public constructor
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static BookFragment newInstance(String title, String des) {
        BookFragment fragment = new BookFragment();
        Bundle args = new Bundle();
        args.putString(BOOK_TITLE, title);
        args.putString(BOOK_DESCRIPTIONS, des);
        fragment.setArguments(args);
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
        View viewHierarchy = inflater.inflate(R.layout.fragment_book, container, false);

        initView(viewHierarchy);

        return viewHierarchy;
    }

    private void initView(View viewHierarchy) {
        imageView = (ImageView) viewHierarchy.findViewById(R.id.img_book);
        title = (TextView) viewHierarchy.findViewById(R.id.tv_title);
        des = (TextView) viewHierarchy.findViewById(R.id.tv_des);
        if (getArguments() != null) {
            title.setText(getArguments().getString(BOOK_TITLE));
            des.setText(getArguments().getString(BOOK_DESCRIPTIONS));
        }
        imageView.setImageResource(R.drawable.book);
    }

}
