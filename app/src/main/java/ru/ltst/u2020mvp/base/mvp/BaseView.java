package ru.ltst.u2020mvp.base.mvp;

public interface BaseView {
    void showLoading();
    void showContent();
    void showError(Throwable throwable);
}
