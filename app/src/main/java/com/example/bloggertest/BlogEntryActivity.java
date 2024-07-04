package com.example.bloggertest;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BlogEntryActivity extends AppCompatActivity {

    private EditText titleEditText, contentEditText;
    private Button submitButton;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_entry);

        titleEditText = findViewById(R.id.editTextTitle);
        contentEditText = findViewById(R.id.editTextContent);
        submitButton = findViewById(R.id.buttonSubmit);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("blogs");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitBlog();
            }
        });
    }

    private void submitBlog() {
        String title = titleEditText.getText().toString().trim();
        String content = contentEditText.getText().toString().trim();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = firebaseAuth.getCurrentUser().getUid();
        String blogId = databaseReference.push().getKey();
        Blog blog = new Blog(blogId, userId, title, content);

        databaseReference.child(blogId).setValue(blog).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(BlogEntryActivity.this, "Blog posted", Toast.LENGTH_SHORT).show();
                finish(); // Go back to MainActivity
            } else {
                Toast.makeText(BlogEntryActivity.this, "Failed to post blog", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class Blog {
        public String blogId;
        public String userId;
        public String title;
        public String content;

        public Blog() {
            // Default constructor required for calls to DataSnapshot.getValue(Blog.class)
        }

        public Blog(String blogId, String userId, String title, String content) {
            this.blogId = blogId;
            this.userId = userId;
            this.title = title;
            this.content = content;
        }
    }
}
