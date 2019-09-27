package com.adityaputra.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.text.HtmlCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    Button openBrowser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        openBrowser = (Button) findViewById(R.id.button_browser);
        getIncome();

        openBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getIntent().getStringExtra("url");
                Intent bukabrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(bukabrowser);
            }
        });
    }

    public void getIncome() {
        if (getIntent().hasExtra("title") && getIntent().hasExtra("description") &&
                getIntent().hasExtra("content") && getIntent().hasExtra("publishedAt") &&
                getIntent().hasExtra("urlToImage") && getIntent().hasExtra("url")) {
            String judul = getIntent().getStringExtra("title");
            String konten = getIntent().getStringExtra("content");
            String deskripsi = getIntent().getStringExtra("description");
            String publish = getIntent().getStringExtra("publishedAt");
            String gambar = getIntent().getStringExtra("urlToImage");
            String url = getIntent().getStringExtra("url");

            setImage(judul, konten, deskripsi, publish, gambar, url);
        }
    }

    private void setImage(String titleNews, String contentNews,
                          String descNews, String publishNews, String imageNews, String urlNews) {
        TextView title = (TextView) findViewById(R.id.title_News);
        title.setText(titleNews);
        TextView content = (TextView) findViewById(R.id.content_news);
        content.setText(contentNews);
        TextView desc = (TextView) findViewById(R.id.description_news);
        desc.setText(HtmlCompat.fromHtml(descNews, 0));
        TextView publish = (TextView) findViewById(R.id.published_news);
        publish.setText(publishNews);
        ImageView image = (ImageView) findViewById(R.id.imageView_news);
        Glide.with(this).load(imageNews).into(image);
        TextView url = (TextView) findViewById(R.id.url_news);
        url.setText(urlNews);
    }


}
