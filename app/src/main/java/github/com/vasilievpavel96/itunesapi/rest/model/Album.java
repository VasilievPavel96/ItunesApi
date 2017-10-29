package github.com.vasilievpavel96.itunesapi.rest.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity
public class Album implements Parcelable {
    private long artistId;
    @PrimaryKey
    @SerializedName("collectionId")
    private long albumId;
    private String artistName;
    @SerializedName("collectionName")
    private String albumName;
    @SerializedName("artworkUrl100")
    private String thumbnail;
    private int trackCount;
    private Date releaseDate;
    @SerializedName("primaryGenreName")
    private String genre;

    public Album() {
    }

    public Album(Parcel in) {
        artistId = in.readLong();
        albumId = in.readLong();
        artistName = in.readString();
        albumName = in.readString();
        thumbnail = in.readString();
        trackCount = in.readInt();
        releaseDate = new Date(in.readLong());
        genre = in.readString();
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

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getTrackCount() {
        return trackCount;
    }

    public void setTrackCount(int trackCount) {
        this.trackCount = trackCount;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Album{" +
                "artistName='" + artistName + '\'' +
                ", albumName='" + albumName + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(artistId);
        parcel.writeLong(albumId);
        parcel.writeString(artistName);
        parcel.writeString(albumName);
        parcel.writeString(thumbnail);
        parcel.writeInt(trackCount);
        parcel.writeLong(releaseDate.getTime());
        parcel.writeString(genre);
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };
}
