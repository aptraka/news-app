package com.adityaputra.newsapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.adityaputra.newsapp.DetailActivity;
import com.adityaputra.newsapp.R;
import com.adityaputra.newsapp.model.News;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {
    private ArrayList<News> mdata;
    private Activity mActivity;

    public NewsAdapter(ArrayList<News> data, Activity activity) {
        this.mdata = data;
        this.mActivity = activity;
    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsHolder holder, int position) {
        final News news = mdata.get(position);

        holder.setTitle(news.getTitle());
        Glide.with(mActivity).load(news.getImageUrl()).into(holder.newsImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(mActivity,DetailActivity.class);
                intent.putExtra("title",news.getTitle());
                intent.putExtra("description",news.getDescription());
                intent.putExtra("content",news.getContent());
                intent.putExtra("publishedAt",news.getPublished());
                intent.putExtra("urlToImage",news.getImageUrl());
                intent.putExtra("url",news.getUrl());
                mActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mdata == null)
            return 0;
        return mdata.size();
    }


    public class NewsHolder extends RecyclerView.ViewHolder {

        ImageView newsImageView;
        TextView newsTitleTextView;
        TextView newsdesciptionTextView;


        public NewsHolder(@NonNull View itemView) {
            super(itemView);

            newsImageView = (ImageView) itemView.findViewById(R.id.imageview_news);
            newsTitleTextView = (TextView) itemView.findViewById(R.id.textView_Titlenews);
        }

        public void setTitle(String title) {
            newsTitleTextView.setText(title);
        }

        public void setDescription(String desc) {
            newsdesciptionTextView.setText(desc);
        }


        public void setNewsImageView(ImageView newsImageView) {
            this.newsImageView = newsImageView;
        }

    }


}
