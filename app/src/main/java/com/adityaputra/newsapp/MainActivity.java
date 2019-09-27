package com.adityaputra.newsapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.adityaputra.newsapp.adapter.NewsAdapter;
import com.adityaputra.newsapp.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView newsRecyclerView;
    private NewsAdapter adapter;
    private ArrayList<News> newsData;
    SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light,
                android.R.color.holo_purple);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                newsData.clear();
                new FetchData().execute();
            }
        });
        init();
        new FetchData().execute();
        if (newsData.isEmpty()) {
            swipeRefresh.setRefreshing(true);
            Toast.makeText(this, "Loading Your Data....", Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {
        newsRecyclerView = (RecyclerView) findViewById(R.id.news_recycler);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsRecyclerView.setHasFixedSize(true);
        newsData = new ArrayList<>();
        adapter = new NewsAdapter(newsData, this);
        newsRecyclerView.setAdapter(adapter);
    }

    public class FetchData extends AsyncTask<Void, Void, Void> {
        private String newsString;

        @Override
        protected void onPreExecute() {
            swipeRefresh.setRefreshing(true);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            Uri builtUri = Uri.parse("https://newsapi.org/v2/top-headlines?country=id&apiKey=74a2c4b03c684b3db3e444a5583209d4");
            URL url;

            try {
                url = new URL(builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }
                newsString = buffer.toString();
                JSONObject jsonObject = new JSONObject(newsString);
                Log.v("Response:", jsonObject.toString());

                JSONArray newsArray = jsonObject.getJSONArray("articles");

                for (int a = 0; a < newsArray.length(); a++) {

                    Log.v("Data_", a + "");
                    JSONObject jNews =newsArray.getJSONObject(a);
                    String title;
                    String desc;
                    String content;
                    String publishedDate;
                    String imageURL;
                    String urlD;

                    title = jNews.getString("title");
                    desc = jNews.getString("description");
                    content = jNews.getString("content");
                    publishedDate = jNews.getString("publishedAt");
                    imageURL = jNews.getString("urlToImage");
                    urlD =jNews.getString("url");

                    News news = new News();
                    news.setTitle(title);
                    news.setDescription(desc);
                    news.setContent(content);
                    news.setPublished(publishedDate);
                    news.setImageUrl(imageURL);
                    news.setUrl(urlD);
                    newsData.add(news);
                }
            } catch (MalformedURLException e) {
                Log.e("MainActivity", "Error: ", e);
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("MainActivity", "Error: ", e);
                e.printStackTrace();
            } catch (JSONException e) {
                Log.e("MainActivity", "Error: ", e);
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("MainActivity", "Error closing stream ", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            swipeRefresh.setRefreshing(false);
            adapter.notifyDataSetChanged();
        }
    }
}
