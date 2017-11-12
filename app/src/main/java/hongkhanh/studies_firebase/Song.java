package hongkhanh.studies_firebase;

/**
 * Created by HONGKHANH on 11/11/2017.
 */

public class Song {
    public String image;
    public String lyrics;
    public String singer;
    public String song;

    public Song(String image, String lyrics, String singer, String song) {
        this.image = image;
        this.lyrics = lyrics;
        this.singer = singer;
        this.song = song;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public Song() {
//        important
    }

}
