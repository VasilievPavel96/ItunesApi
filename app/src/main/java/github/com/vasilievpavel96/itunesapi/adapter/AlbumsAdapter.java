package github.com.vasilievpavel96.itunesapi.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import github.com.vasilievpavel96.itunesapi.common.OnAlbumClickListener;
import github.com.vasilievpavel96.itunesapi.R;
import github.com.vasilievpavel96.itunesapi.rest.model.Album;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumViewHolder> {
    private List<Album> albums;
    private OnAlbumClickListener listener;

    public AlbumsAdapter(List<Album> albums,OnAlbumClickListener listener) {
        this.albums = albums;
        this.listener = listener;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View albumView = inflater.inflate(R.layout.album_item, viewGroup, false);
        return new AlbumViewHolder(albumView);
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder albumViewHolder, int i) {
        Album album = albums.get(i);
        albumViewHolder.bindAlbum(album, listener);
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public void setAlbums(List<Album> albums) {
        this.albums.clear();
        this.albums.addAll(albums);
        notifyDataSetChanged();
    }
}
