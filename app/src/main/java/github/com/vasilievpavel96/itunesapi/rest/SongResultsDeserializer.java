package github.com.vasilievpavel96.itunesapi.rest;


import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import github.com.vasilievpavel96.itunesapi.rest.model.Album;
import github.com.vasilievpavel96.itunesapi.rest.model.Song;
import github.com.vasilievpavel96.itunesapi.rest.model.SongResults;

public class SongResultsDeserializer implements JsonDeserializer<SongResults> {
    @Override
    public SongResults deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        SongResults songResults = new SongResults();
        JsonObject jsonObject = json.getAsJsonObject();
        int resultsCount = jsonObject.get("resultCount").getAsInt();
        List<Song> songs = new ArrayList<>();
        JsonArray songsArray = jsonObject.get("results").getAsJsonArray();
        for (int i = 0; i < songsArray.size(); i++) {
            JsonObject songObject = songsArray.get(i).getAsJsonObject();
            if (i == 0) {
                songResults.setAlbum(context.deserialize(songObject, Album.class));
            } else {
                songs.add(context.deserialize(songObject, Song.class));
            }
        }
        songResults.setResultCount(resultsCount);
        songResults.setResults(songs);
        return songResults;
    }
}
