package com.example.dailynova.items;

public class LyricsItem {
    String lyricsName,ArtistName,imgUrl,SongBody,songUrl,postedOn;

    public LyricsItem(String lyricsName, String artistName, String imgUrl, String songBody, String songUrl, String postedOn) {
        this.lyricsName = lyricsName;
        ArtistName = artistName;
        this.imgUrl = imgUrl;
        SongBody = songBody;
        this.songUrl = songUrl;
        this.postedOn = postedOn;
    }

    public String getLyricsName() {
        return lyricsName;
    }

    public String getArtistName() {
        return ArtistName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getSongBody() {
        return SongBody;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public String getPostedOn() {
        return postedOn;
    }
}
