package com.example.dailynova.items;

public class UserItem {
    String displayName,userImgageUrl,userKey,tokenId;

    public UserItem(String displayName, String userImgageUrl, String userKey, String tokenId) {
        this.displayName = displayName;
        this.userImgageUrl = userImgageUrl;
        this.userKey = userKey;
        this.tokenId = tokenId;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setUserImgageUrl(String userImgageUrl) {
        this.userImgageUrl = userImgageUrl;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUserImgageUrl() {
        return userImgageUrl;
    }

    public String getUserKey() {
        return userKey;
    }

    public String getTokenId() {
        return tokenId;
    }
}
