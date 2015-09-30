package ru.ltst.u2020mvp.base.mvp;

public interface BaseView {
    void showLoading();
    void showContent();
    void showEmpty();
    void showError(Throwable throwable);
}
