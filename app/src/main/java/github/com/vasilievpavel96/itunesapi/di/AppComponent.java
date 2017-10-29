package github.com.vasilievpavel96.itunesapi.di;

import javax.inject.Singleton;

import dagger.Component;
import github.com.vasilievpavel96.itunesapi.mvp.AlbumDetailsPresenter;
import github.com.vasilievpavel96.itunesapi.mvp.AlbumListPresenter;

@Singleton
@Component(modules = {AppModule.class, RestModule.class, StorageModule.class})
public interface AppComponent {
    void injectAlbumListPresenter(AlbumListPresenter presenter);

    void injectAlbumDetailsPresenter(AlbumDetailsPresenter presenter);
}
