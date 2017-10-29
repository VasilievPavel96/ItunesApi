package github.com.vasilievpavel96.itunesapi.di;


import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import github.com.vasilievpavel96.itunesapi.rest.AppDatabase;
import github.com.vasilievpavel96.itunesapi.rest.dao.AlbumDao;
import github.com.vasilievpavel96.itunesapi.rest.dao.SongDao;

@Module
public class StorageModule {
    @Provides
    @Singleton
    public AppDatabase provideAppDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "itunes-db")
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    public AlbumDao provideAlbumDao(AppDatabase appDatabase) {
        return appDatabase.albumDao();
    }

    @Provides
    public SongDao provideSongDao(AppDatabase appDatabase) {
        return appDatabase.songDao();
    }
}
