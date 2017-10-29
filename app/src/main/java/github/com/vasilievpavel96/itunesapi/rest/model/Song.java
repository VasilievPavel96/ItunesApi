package github.com.vasilievpavel96.itunesapi.rest.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.concurrent.TimeUnit;

@Entity
public class Song {
    @PrimaryKey
    private long trackId;
    private long artistId;
    @SerializedName("collectionId")
    private long albumId;
    private String trackName;
    private float trackPrice;
    private long trackTimeMillis;

    public Song() {
    }

    public long getTrackId() {
        return trackId;
    }

    public void setTrackId(long trackId) {
        this.trackId = trackId;
    }

    public long getArtistId() {
        return artistId;
    }

    public void setArtistId(long artistId) {
        this.artistId = artistId;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public float getTrackPrice() {
        return trackPrice;
    }

    public void setTrackPrice(float trackPrice) {
        this.trackPrice = trackPrice;
    }

    public long getTrackTimeMillis() {
        return trackTimeMillis;
    }

    public void setTrackTimeMillis(long trackTimeMillis) {
        this.trackTimeMillis = trackTimeMillis;
    }

    public String getTrackDuration() {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(trackTimeMillis);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(trackTimeMillis)-minutes*60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    public String toString() {
        return "Song{" +
                "trackName='" + trackName + '\'' +
                '}';
    }
}
