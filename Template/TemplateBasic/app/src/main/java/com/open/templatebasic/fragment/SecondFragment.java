package com.open.templatebasic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.templatebasic.R;

/**
 * second fragment.
 */
public class SecondFragment extends Fragment {
    private final static String TAG = "SecondFragment";
    private static final String ARG_PARAM = "param";

    public SecondFragment() {
    }

    public static SecondFragment newInstance(String param) {
        Log.d(TAG, "SecondFragment newInstance: " + param);
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewHierarchy = inflater.inflate(R.layout.fragment_second, container, false);

        initView(viewHierarchy);

        return viewHierarchy;
    }

    private void initView(View viewHierarchy) {
        Bundle args = getArguments();
        if (args != null) {
            TextView content = (TextView) viewHierarchy.findViewById(R.id.content);
            String builder = "ARG_PARAM: " + args.getString(ARG_PARAM);
            content.setText(builder);
        }
    }

}
