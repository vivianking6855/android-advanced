package com.open.dynamicfragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class BookDescActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_desc);

        loadData();
    }

    private void loadData() {
        // Retrieve the book index from the Activity Intent
        Intent intent = getIntent();
        int bookIndex = intent.getIntExtra("bookIndex", -1);
        if (bookIndex != -1) {
            // Use FragmentManager to access BookDescFragment
            FragmentManager fm = getSupportFragmentManager();
            BookDescFragment bookDescFragment = (BookDescFragment)
                    fm.findFragmentById(R.id.fragmentDescription);
            // Display the book title
            bookDescFragment.setBook(bookIndex);
        }
    }

}
