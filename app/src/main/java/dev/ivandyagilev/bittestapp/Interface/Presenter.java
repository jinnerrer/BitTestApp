package dev.ivandyagilev.bittestapp.Interface;


public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);
    void detachView();

}
