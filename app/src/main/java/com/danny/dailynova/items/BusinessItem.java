package com.danny.dailynova.items;

public class BusinessItem {

    String businessTitle,businessContent,businessImageUrl,businessCategory,businessSource,businessUploadDate;

    public BusinessItem(String businessTitle, String businessContent, String businessImageUrl, String businessCategory, String businessSource, String businessUploadDate) {
        this.businessTitle = businessTitle;
        this.businessContent = businessContent;
        this.businessImageUrl = businessImageUrl;
        this.businessCategory = businessCategory;
        this.businessSource = businessSource;
        this.businessUploadDate = businessUploadDate;
    }

    public String getBusinessTitle() {
        return businessTitle;
    }

    public String getBusinessContent() {
        return businessContent;
    }

    public String getBusinessImageUrl() {
        return businessImageUrl;
    }

    public String getBusinessCategory() {
        return businessCategory;
    }

    public String getBusinessSource() {
        return businessSource;
    }

    public String getBusinessUploadDate() {
        return businessUploadDate;
    }
}
