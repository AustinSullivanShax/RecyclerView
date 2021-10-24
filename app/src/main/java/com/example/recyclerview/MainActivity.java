/*
Name: Austin Sullivan
username: Sullivana6
Notes:
-There is no "Hello World" text, it is actually the fragment_first.xml that contains the initial text.

 */

package com.example.recyclerview;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerview.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private final LinkedList<String> mWordList = new LinkedList<>(); // Linked List Variable

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private RecyclerView mRecyclerView;
    private WordListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        for (int i = 0; i < 20; i++) {
            mWordList.addLast("Word " + i);
        }

        mRecyclerView = findViewById(R.id.recyclerview); // Get a handle to the RecyclerView.
        mAdapter = new WordListAdapter(this, mWordList); // Create an adapter and supply the data to be displayed.
        mRecyclerView.setAdapter(mAdapter); // Connect the adapter with the RecyclerView.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // Give the RecyclerView a default layout manager.
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int wordListSize = mWordList.size();
                // Add a new word to the wordList.
                mWordList.addLast("+ Word " + wordListSize);
                // Notify the adapter, that the data has changed.
                mRecyclerView.getAdapter().notifyItemInserted(wordListSize);
                // Scroll to the bottom.
                mRecyclerView.smoothScrollToPosition(wordListSize);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
      mWordList.clear(); // Empty the LinkedList

        for (int i = 0; i < 20; i++) {
            mWordList.addLast("Word " + i); // Repopulate the linked list
        }

        mRecyclerView = findViewById(R.id.recyclerview); // Get a handle to the RecyclerView.
        mAdapter = new WordListAdapter(this, mWordList); // Create an adapter and supply the data to be displayed.
        mRecyclerView.setAdapter(mAdapter); // Connect the adapter with the RecyclerView.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // Give the RecyclerView a default layout manager.
        binding.toolbar.setOnClickListener(new View.OnClickListener() { // Change the binding
            @Override
            public void onClick(View view) {
                int wordListSize = mWordList.size(); // Get the size
                mRecyclerView.getAdapter().notifyItemInserted(wordListSize); // Notify the adapter
            }
        });


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}