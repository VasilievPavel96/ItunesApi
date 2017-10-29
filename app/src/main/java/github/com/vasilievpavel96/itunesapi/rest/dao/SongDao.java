package github.com.vasilievpavel96.itunesapi.rest.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import github.com.vasilievpavel96.itunesapi.rest.model.Song;

@Dao
public interface SongDao {
    @Query("SELECT * FROM Song WHERE albumId=:albumId")
    List<Song> findSongsInAlbum(long albumId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Song> songs);
}
