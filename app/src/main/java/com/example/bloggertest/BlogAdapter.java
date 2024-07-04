package com.example.bloggertest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.BlogViewHolder> {

    private List<BlogEntryActivity.Blog> blogList;

    public BlogAdapter(List<BlogEntryActivity.Blog> blogList) {
        this.blogList = blogList;
    }

    @NonNull
    @Override
    public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_item, parent, false);
        return new BlogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BlogViewHolder holder, int position) {
        BlogEntryActivity.Blog blog = blogList.get(position);
        holder.title.setText(blog.title);
        holder.content.setText(blog.content);
    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }

    static class BlogViewHolder extends RecyclerView.ViewHolder {

        TextView title, content;

        public BlogViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            content = itemView.findViewById(R.id.tvContent);
        }
    }
}

