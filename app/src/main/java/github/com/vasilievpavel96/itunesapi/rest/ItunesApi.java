package github.com.vasilievpavel96.itunesapi.rest;

import github.com.vasilievpavel96.itunesapi.rest.model.AlbumResults;
import github.com.vasilievpavel96.itunesapi.rest.model.SongResults;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ItunesApi {
    String BASE_URL = "https://itunes.apple.com/";

    @GET("search?entity=album")
    Observable<AlbumResults> findAlbums(@Query("term") String term);

    @GET("lookup?entity=song")
    Observable<SongResults> getSongsInAlbum(@Query("id") long albumId);
}
