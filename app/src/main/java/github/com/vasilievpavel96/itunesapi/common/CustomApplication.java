package github.com.vasilievpavel96.itunesapi.common;

import android.app.Application;

import github.com.vasilievpavel96.itunesapi.di.AppComponent;
import github.com.vasilievpavel96.itunesapi.di.AppModule;
import github.com.vasilievpavel96.itunesapi.di.DaggerAppComponent;
import timber.log.Timber;

public class CustomApplication extends Application {
    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        Timber.plant(new Timber.DebugTree() {
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                return super.createStackElementTag(element) + ":" + element.getLineNumber();
            }
        });
    }

    public static AppComponent getComponent() {
        return component;
    }
}
