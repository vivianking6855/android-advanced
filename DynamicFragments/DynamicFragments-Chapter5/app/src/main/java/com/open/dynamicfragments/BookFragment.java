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
    public static final String BOOK_TITLE = "title";
    public static final String TOP_IMAGE = "image";
    public static final String BOOK_DESCRIPTIONS = "des";

    private ImageView imageView;
    private TextView title;
    private TextView des;

    public BookFragment() {
        // Required empty public constructor
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
            imageView.setImageResource(getArguments().getInt(TOP_IMAGE));
            title.setText(getArguments().getString(BOOK_TITLE));
            des.setText(getArguments().getString(BOOK_DESCRIPTIONS));
        }
    }

}
