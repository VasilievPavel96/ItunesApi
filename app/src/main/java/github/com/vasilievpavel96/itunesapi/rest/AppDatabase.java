package github.com.vasilievpavel96.itunesapi.rest;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import github.com.vasilievpavel96.itunesapi.rest.dao.AlbumDao;
import github.com.vasilievpavel96.itunesapi.rest.dao.SongDao;
import github.com.vasilievpavel96.itunesapi.rest.model.Album;
import github.com.vasilievpavel96.itunesapi.rest.model.Song;

@Database(entities = {Album.class, Song.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract AlbumDao albumDao();

    public abstract SongDao songDao();
}
