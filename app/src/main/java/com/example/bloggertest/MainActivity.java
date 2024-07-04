package com.example.bloggertest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private BlogAdapter blogAdapter;
    private List<BlogEntryActivity.Blog> blogList;
    private android.content.Context Context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        FirebaseApp.initializeApp(Context);
        firebaseAuth = FirebaseAuth.getInstance();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) {
            // If the user is not signed in, navigate to the login activity
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return;
        }

        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerView);

        fab.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, BlogEntryActivity.class)));

        // Initialize RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        blogList = new ArrayList<>();
        blogAdapter = new BlogAdapter(blogList);
        recyclerView.setAdapter(blogAdapter);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("blogs");

        // Load user's blogs
        loadUserBlogs();
    }

    private void loadUserBlogs() {
        String userId = firebaseAuth.getCurrentUser().getUid();
        databaseReference.orderByChild("userId").equalTo(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                blogList.clear();
                for (DataSnapshot blogSnapshot : snapshot.getChildren()) {
                    BlogEntryActivity.Blog blog = blogSnapshot.getValue(BlogEntryActivity.Blog.class);
                    blogList.add(blog);
                }
                blogAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }
}
