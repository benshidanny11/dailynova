package com.danny.dailynova.items;

public class LifeStyleItem {
    String lifeStyleTitle,lifestyleContent,lifestyleSource,lifestyleUploadDate,lifestyleImageUrl,lifestyleCategory;

    public LifeStyleItem(String lifeStyleTitle, String lifestyleContent, String lifestyleSource, String lifestyleUploadDate, String lifestyleImageUrl, String lifestyleCategory) {
        this.lifeStyleTitle = lifeStyleTitle;
        this.lifestyleContent = lifestyleContent;
        this.lifestyleSource = lifestyleSource;
        this.lifestyleUploadDate = lifestyleUploadDate;
        this.lifestyleImageUrl = lifestyleImageUrl;
        this.lifestyleCategory = lifestyleCategory;
    }

    public String getLifeStyleTitle() {
        return lifeStyleTitle;
    }

    public String getLifestyleContent() {
        return lifestyleContent;
    }

    public String getLifestyleSource() {
        return lifestyleSource;
    }

    public String getLifestyleUploadDate() {
        return lifestyleUploadDate;
    }

    public String getLifestyleImageUrl() {
        return lifestyleImageUrl;
    }

    public String getLifestyleCategory() {
        return lifestyleCategory;
    }
}
