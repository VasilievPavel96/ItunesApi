package github.com.vasilievpavel96.itunesapi.rest.model;

import java.util.ArrayList;
import java.util.List;

public class AlbumResults {
    private int resultCount;
    private List<Album> results;

    public AlbumResults() {
        results = new ArrayList<>();
    }

    public int getResultCount() {
        return resultCount;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }

    public List<Album> getResults() {
        return results;
    }

    public void setResults(List<Album> results) {
        this.results = results;
    }
}
