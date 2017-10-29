package github.com.vasilievpavel96.itunesapi.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import github.com.vasilievpavel96.itunesapi.rest.SongResultsDeserializer;
import github.com.vasilievpavel96.itunesapi.rest.ItunesApi;
import github.com.vasilievpavel96.itunesapi.rest.model.SongResults;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static github.com.vasilievpavel96.itunesapi.rest.ItunesApi.BASE_URL;

@Module
public class RestModule {


    @Provides
    public Gson provideGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .registerTypeAdapter(SongResults.class, new SongResultsDeserializer())
                .create();
    }

    @Provides
    public Retrofit provideRetrofit(Gson gson) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    @Provides
    @Singleton
    public ItunesApi provideItunesApi(Retrofit retrofit) {
        return retrofit.create(ItunesApi.class);
    }
}
