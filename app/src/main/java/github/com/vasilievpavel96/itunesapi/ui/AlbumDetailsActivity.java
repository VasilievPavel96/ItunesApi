package github.com.vasilievpavel96.itunesapi.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;
import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Scene;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import github.com.vasilievpavel96.itunesapi.R;
import github.com.vasilievpavel96.itunesapi.adapter.SongsAdapter;
import github.com.vasilievpavel96.itunesapi.mvp.AlbumDetailsPresenter;
import github.com.vasilievpavel96.itunesapi.mvp.LceView;
import github.com.vasilievpavel96.itunesapi.rest.model.Album;
import github.com.vasilievpavel96.itunesapi.rest.model.SongResults;
import timber.log.Timber;

public class AlbumDetailsActivity extends MvpAppCompatActivity implements LceView<SongResults>, View.OnClickListener {
    private static final String EXTRA_ALBUM = "EXTRA_ALBUM";
    @InjectPresenter
    AlbumDetailsPresenter presenter;
    @BindView(R.id.thumbnail)
    ImageView thumbnail;
    @BindView(R.id.list)
    RecyclerView songsList;
    @BindView(R.id.artistName)
    TextView artistName;
    @BindView(R.id.albumName)
    TextView albumName;
    @BindView(R.id.genreName)
    TextView genreName;
    @Nullable
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.details_container)
    LinearLayout detailsContainer;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.errorView)
    RelativeLayout errorView;
    private SongsAdapter adapter;
    private Album album;
    private Scene expandedScene;
    private Scene collapsedScene;
    private boolean isCollapsed = true;
    private boolean isLoading;
    private boolean isError;

    public static Intent getStartIntent(Context context, Album album) {
        Intent intent = new Intent(context, AlbumDetailsActivity.class);
        intent.putExtra(EXTRA_ALBUM, album);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_details_collapsed);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementsUseOverlay(false);
        }
        if (fab != null) {
            fab.setOnClickListener(this);
        }
        album = getIntent().getExtras().getParcelable(EXTRA_ALBUM);
        long albumId = album.getAlbumId();
        Timber.d("selected album id = %d",albumId);
        expandedScene = getScene(false);
        collapsedScene = getScene(true);
        adapter = new SongsAdapter(new ArrayList<>());
        setupAlbum(album);
        setupList();
        if (savedInstanceState == null) {
            presenter.loadSongs(albumId);
        }
    }

    private void setupAlbum(Album album) {
        String thumbnailUrl = album.getThumbnail().replace("100x100", "500x500");
        Glide.with(this)
                .load(thumbnailUrl)
                .into(thumbnail);
        TransitionManager.setTransitionName(thumbnail, String.valueOf(album.getAlbumId()));
        albumName.setText(album.getAlbumName());
        artistName.setText(album.getArtistName());
        genreName.setText(album.getGenre());
    }

    private void setupList() {
        songsList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        songsList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        songsList.setAdapter(adapter);
        if (isLoading) {
            songsList.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
        if (isError) {
            songsList.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);
        }
    }

    private Scene getScene(boolean isCollapsed) {
        int layoutId = isCollapsed ? R.layout.activity_album_details_collapsed : R.layout.activity_album_details_expanded;
        Scene scene = Scene.getSceneForLayout(detailsContainer, layoutId, this);
        scene.setEnterAction(() -> {
            ButterKnife.bind(this);
            setupAlbum(album);
            setupList();
        });
        return scene;
    }

    @Override
    public void onClick(View view) {
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.setOrdering(TransitionSet.ORDERING_SEQUENTIAL);
        transitionSet.addTransition(new ChangeBounds());
        Fade fade = new Fade();
        fade.addTarget(R.id.list);
        transitionSet.addTransition(fade);
        if (isCollapsed) {
            TransitionManager.go(expandedScene, transitionSet);
        } else {
            TransitionManager.go(collapsedScene, transitionSet);
        }
        isCollapsed = !isCollapsed;
    }

    @Override
    public void showResults(SongResults results) {
        Timber.d(results.getResults().toString());
        isLoading = false;
        isError = false;
        progressBar.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        songsList.setVisibility(View.VISIBLE);
        adapter.setSongs(results.getResults());
    }

    @Override
    public void showLoading() {
        Timber.d("loading");
        isLoading = true;
        isError = false;
        songsList.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(Throwable throwable) {
        Timber.e(throwable);
        isLoading = false;
        isError = true;
        progressBar.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }
}
