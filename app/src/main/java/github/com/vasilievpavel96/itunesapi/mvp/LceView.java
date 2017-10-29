package github.com.vasilievpavel96.itunesapi.mvp;

import com.arellomobile.mvp.MvpView;

public interface LceView<T> extends MvpView {
    void showResults(T results);

    void showLoading();

    void showError(Throwable throwable);
}
