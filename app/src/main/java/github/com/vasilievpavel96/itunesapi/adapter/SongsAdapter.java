package github.com.vasilievpavel96.itunesapi.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import github.com.vasilievpavel96.itunesapi.R;
import github.com.vasilievpavel96.itunesapi.rest.model.Song;

public class SongsAdapter extends RecyclerView.Adapter<SongViewHolder> {
    private List<Song> songs;

    public SongsAdapter(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View songView = inflater.inflate(R.layout.song_item, viewGroup, false);
        return new SongViewHolder(songView);
    }

    @Override
    public void onBindViewHolder(SongViewHolder songViewHolder, int i) {
        Song song = songs.get(i);
        songViewHolder.bindSong(song);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {

        this.songs.clear();
        this.songs.addAll(songs);
        notifyDataSetChanged();
    }
}
