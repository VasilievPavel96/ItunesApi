package github.com.vasilievpavel96.itunesapi.common;

import android.widget.ImageView;

import github.com.vasilievpavel96.itunesapi.rest.model.Album;

public interface OnAlbumClickListener {
    void onAlbumClick(Album album, ImageView thumbnail);
}
