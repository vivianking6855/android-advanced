package com.open.dynamicfragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements
        BookListFragment.OnSelectedBookChangeListener {
    private static final String TAG = "MainActivity";
    private boolean mIsDynamic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment bookDescFragment =
                fm.findFragmentById(R.id.fragmentDescription);
        // If not found than we're doing dynamic mgmt
        mIsDynamic = bookDescFragment == null || !bookDescFragment.isInLayout();

        // Load the list fragment if necessary
        if (mIsDynamic) {
            // Begin transaction
            FragmentTransaction ft = fm.beginTransaction();
            // Create the Fragment and add
            BookListFragment listFragment = new BookListFragment();
            ft.add(R.id.layoutRoot, listFragment, "bookList");
            ft.commit();
        }
    }

    @Override
    public void onSelectedBookChanged(int bookIndex) {
        BookDescFragment bookDescFragment;
        FragmentManager fm = getSupportFragmentManager();

        // Check validity of fragment reference
        if (mIsDynamic) {
            // Handle dynamic switch to description fragment
            FragmentTransaction ft = fm.beginTransaction();
            bookDescFragment = new BookDescFragment();

            // Create the fragment and attach book index
            bookDescFragment = new BookDescFragment();
            Bundle args = new Bundle();
            args.putInt(BookDescFragment.BOOK_INDEX, bookIndex);
            bookDescFragment.setArguments(args);

            // Replace the book list with the description
            ft.replace(R.id.layoutRoot,
                    bookDescFragment, "bookDescription");
            ft.addToBackStack(null);

            //ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
            ft.commit();
        } else {
            // Use the already visible description fragment
            bookDescFragment = (BookDescFragment)
                    fm.findFragmentById(R.id.fragmentDescription);
            bookDescFragment.setBook(bookIndex);
        }
    }
}
