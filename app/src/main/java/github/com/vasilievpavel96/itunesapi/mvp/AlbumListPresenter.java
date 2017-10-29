package github.com.vasilievpavel96.itunesapi.mvp;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import github.com.vasilievpavel96.itunesapi.common.CustomApplication;
import github.com.vasilievpavel96.itunesapi.rest.ItunesApi;
import github.com.vasilievpavel96.itunesapi.rest.dao.AlbumDao;
import github.com.vasilievpavel96.itunesapi.rest.model.Album;
import github.com.vasilievpavel96.itunesapi.rest.model.AlbumResults;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
@StateStrategyType(SingleStateStrategy.class)
public class AlbumListPresenter extends MvpPresenter<LceView<AlbumResults>> {
    @Inject
    ItunesApi itunesApi;
    @Inject
    AlbumDao albumDao;

    public AlbumListPresenter() {
        CustomApplication.getComponent().injectAlbumListPresenter(this);
    }

    public void findAlbum(String term) {
        itunesApi.findAlbums(term)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(albumResults -> {
                    albumDao.insert(albumResults.getResults());
                    return albumResults;
                })
                .map(albumResults -> {
                    Collections.sort(albumResults.getResults(),
                            (first, second) -> first.getAlbumName().compareTo(second.getAlbumName()));
                    return albumResults;
                })
                .onExceptionResumeNext(new Observable<AlbumResults>() {
                    @Override
                    protected void subscribeActual(Observer<? super AlbumResults> observer) {
                        List<Album> albums = albumDao.findAlbum("%" + term + "%");
                        if (albums.size() == 0) observer.onError(new Throwable());
                        AlbumResults results = new AlbumResults();
                        results.setResultCount(albums.size());
                        results.setResults(albums);
                        observer.onNext(results);
                        observer.onComplete();
                    }
                })
                .subscribe(getViewState()::showResults, getViewState()::showError,
                        () -> {
                        }, disposable -> getViewState().showLoading());
    }

}
