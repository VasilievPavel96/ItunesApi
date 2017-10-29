package github.com.vasilievpavel96.itunesapi.mvp;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import javax.inject.Inject;

import github.com.vasilievpavel96.itunesapi.common.CustomApplication;
import github.com.vasilievpavel96.itunesapi.rest.ItunesApi;
import github.com.vasilievpavel96.itunesapi.rest.dao.SongDao;
import github.com.vasilievpavel96.itunesapi.rest.model.Song;
import github.com.vasilievpavel96.itunesapi.rest.model.SongResults;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
@StateStrategyType(SingleStateStrategy.class)
public class AlbumDetailsPresenter extends MvpPresenter<LceView<SongResults>> {
    @Inject
    ItunesApi itunesApi;
    @Inject
    SongDao songDao;

    public AlbumDetailsPresenter() {
        CustomApplication.getComponent().injectAlbumDetailsPresenter(this);
    }

    public void loadSongs(long albumId) {
        itunesApi.getSongsInAlbum(albumId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(songResults -> {
                    songDao.insert(songResults.getResults());
                    return songResults;
                })
                .onExceptionResumeNext(new Observable<SongResults>() {
                    @Override
                    protected void subscribeActual(Observer<? super SongResults> observer) {
                        List<Song> songs = songDao.findSongsInAlbum(albumId);
                        if (songs.size() == 0) observer.onError(new Throwable());
                        SongResults results = new SongResults();
                        results.setResultCount(songs.size() - 1);
                        results.setResults(songs);
                        observer.onNext(results);
                        observer.onComplete();
                    }
                })
                .subscribe(getViewState()::showResults, getViewState()::showError,
                        () -> {
                        }, disposable -> getViewState().showLoading());
    }
}
