package com.example.reportvehiclecomplaints.util.mvp;

public interface BaseView <T extends BasePresenter> {
    void setPresenter(T var1);

    void showConnectionError();

    void showContentLoading();

    void hideContentLoading();

    void showConnectionFailed();
}
