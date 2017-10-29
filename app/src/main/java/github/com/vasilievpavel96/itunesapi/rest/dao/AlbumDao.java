package github.com.vasilievpavel96.itunesapi.rest.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import github.com.vasilievpavel96.itunesapi.rest.model.Album;

@Dao
public interface AlbumDao {
    @Query("SELECT * FROM Album WHERE albumName LIKE :term ORDER BY albumName ASC")
    List<Album> findAlbum(String term);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Album> albums);
}
