package github.com.vasilievpavel96.itunesapi.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import github.com.vasilievpavel96.itunesapi.R;
import github.com.vasilievpavel96.itunesapi.adapter.AlbumsAdapter;
import github.com.vasilievpavel96.itunesapi.common.VerticalSpaceItemDecoration;
import github.com.vasilievpavel96.itunesapi.mvp.AlbumListPresenter;
import github.com.vasilievpavel96.itunesapi.mvp.LceView;
import github.com.vasilievpavel96.itunesapi.rest.model.AlbumResults;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class AlbumListActivity extends MvpAppCompatActivity implements LceView<AlbumResults> {
    private static final String BUNDLE_TEXT = "BUNDLE_TEXT";
    @InjectPresenter
    AlbumListPresenter presenter;
    @BindView(R.id.searchEditText)
    EditText searchEditText;
    @BindView(R.id.list)
    RecyclerView albumsList;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.errorView)
    RelativeLayout errorView;
    private AlbumsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_list);
        ButterKnife.bind(this);
        setupList();
        String initialText;
        if (savedInstanceState != null) {
            initialText = savedInstanceState.getString(BUNDLE_TEXT);
        } else {
            initialText = "";
        }
        RxTextView.textChanges(searchEditText)
                .map(CharSequence::toString)
                .debounce(800, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(term -> {
                    if (!term.equals(initialText)) {
                        presenter.findAlbum(term);
                    }
                })
                .subscribe();
    }

    @Override
    public void showResults(AlbumResults results) {
        Timber.d(results.getResults().toString());
        progressBar.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        albumsList.setVisibility(View.VISIBLE);
        adapter.setAlbums(results.getResults());
    }

    @Override
    public void showLoading() {
        Timber.d("loading");
        albumsList.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(Throwable throwable) {
        Timber.e(throwable);
        progressBar.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BUNDLE_TEXT, searchEditText.getText().toString());
    }

    private void setupList() {
        adapter = new AlbumsAdapter(new ArrayList<>(), (album, thumbnail) -> {
            ActivityOptionsCompat activityOptions = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(this, thumbnail, String.valueOf(album.getAlbumId()));
            startActivity(AlbumDetailsActivity.getStartIntent(this, album), activityOptions.toBundle());
        });
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            int spanCount = getResources().getInteger(R.integer.spanCount);
            albumsList.setLayoutManager(new GridLayoutManager(this, spanCount, GridLayoutManager.VERTICAL, false));
        } else {
            albumsList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        }
        int verticalSpace = getResources().getInteger(R.integer.verticalSpace);
        albumsList.addItemDecoration(new VerticalSpaceItemDecoration(verticalSpace));
        albumsList.setAdapter(adapter);
    }

}
