package github.com.vasilievpavel96.itunesapi.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import github.com.vasilievpavel96.itunesapi.R;
import github.com.vasilievpavel96.itunesapi.rest.model.Song;

public class SongViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.songName)
    TextView songName;
    @BindView(R.id.songDuration)
    TextView songDuration;

    public SongViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindSong(Song song) {
        songName.setText(song.getTrackName());
        songDuration.setText(song.getTrackDuration());
    }
}
