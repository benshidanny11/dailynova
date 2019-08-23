package com.example.dailynova.builders;

import com.example.dailynova.items.LifeStyleItem;

public class LifestyleBuilder {
    String lifeStyleTitle,lifestyleContent,lifestyleSource,lifestyleUploadDate,lifestyleImageUrl,lifestyleCategory;

    public LifestyleBuilder setLifeStyleTitle(String lifeStyleTitle) {
        this.lifeStyleTitle = lifeStyleTitle;
        return this;
    }

    public LifestyleBuilder setLifestyleContent(String lifestyleContent) {
        this.lifestyleContent = lifestyleContent;
        return this;
    }

    public LifestyleBuilder setLifestyleSource(String lifestyleSource) {
        this.lifestyleSource = lifestyleSource;
        return this;
    }

    public LifestyleBuilder setLifestyleUploadDate(String lifestyleUploadDate) {
        this.lifestyleUploadDate = lifestyleUploadDate;
        return this;
    }

    public LifestyleBuilder setLifestyleImageUrl(String lifestyleImageUrl) {
        this.lifestyleImageUrl = lifestyleImageUrl;
        return this;
    }

    public LifestyleBuilder setLifestyleCategory(String lifestyleCategory) {
        this.lifestyleCategory = lifestyleCategory;
        return this;
    }

    public LifeStyleItem build(){
        return new LifeStyleItem(lifeStyleTitle,lifestyleContent,lifestyleSource,lifestyleUploadDate,lifestyleImageUrl,lifestyleCategory);
    }
}
