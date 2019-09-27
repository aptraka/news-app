package com.adityaputra.newsapp.model;

public class News {
    private String title;
    private String description;
    private String imageUrl;
    private String published;
    private String content;
    private String url;

    public News() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public News(String url, String title, String description, String imageUrl, String published, String content) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.published = published;
        this.content = content;
        this.url=url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description)   {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
