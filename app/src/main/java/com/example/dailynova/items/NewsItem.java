package com.example.dailynova.items;

public class NewsItem {
   private String newsBody,newsCategory,newsTitle,newsOwner,newsUploadDate,newsImgDownloadLink;

    public NewsItem(String newsBody, String newsCategory, String newsTitle, String newsOwner, String newsUploadDate, String newsImgDownloadLink) {
        this.newsBody = newsBody;
        this.newsCategory = newsCategory;
        this.newsTitle = newsTitle;
        this.newsOwner = newsOwner;
        this.newsUploadDate = newsUploadDate;
        this.newsImgDownloadLink = newsImgDownloadLink;
    }

    public String getNewsBody() {
        return newsBody;
    }

    public String getNewsCategory() {
        return newsCategory;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsOwner() {
        return newsOwner;
    }

    public String getNewsUploadDate() {
        return newsUploadDate;
    }

    public String getNewsImgDownloadLink() {
        return newsImgDownloadLink;
    }
}
