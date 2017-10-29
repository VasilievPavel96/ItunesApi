package github.com.vasilievpavel96.itunesapi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.transitionseverywhere.TransitionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import github.com.vasilievpavel96.itunesapi.common.OnAlbumClickListener;
import github.com.vasilievpavel96.itunesapi.R;
import github.com.vasilievpavel96.itunesapi.rest.model.Album;

public class AlbumViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.thumbnail)
    ImageView thumbnail;
    @BindView(R.id.albumName)
    TextView albumName;
    @BindView(R.id.artistName)
    TextView artistName;


    public AlbumViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindAlbum(Album album, OnAlbumClickListener listener) {
        Context context = thumbnail.getContext();
        String artworkUrl = album.getThumbnail().replace("100x100", "500x500");
        Glide.with(context).load(artworkUrl).into(thumbnail);
        TransitionManager.setTransitionName(thumbnail, String.valueOf(album.getAlbumId()));
        albumName.setText(album.getAlbumName());
        artistName.setText(album.getArtistName());
        albumName.getRootView().setOnClickListener(view -> listener.onAlbumClick(album, thumbnail));
    }
}
