package github.com.vasilievpavel96.itunesapi.rest.model;

import java.util.List;

public class SongResults {
    private int resultCount;
    private Album album;
    private List<Song> results;

    public SongResults() {
    }

    public int getResultCount() {
        return resultCount;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public List<Song> getResults() {
        return results;
    }

    public void setResults(List<Song> results) {
        this.results = results;
    }
}
