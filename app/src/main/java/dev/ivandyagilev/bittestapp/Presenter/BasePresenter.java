package dev.ivandyagilev.bittestapp.Presenter;

import dev.ivandyagilev.bittestapp.Interface.MvpView;
import dev.ivandyagilev.bittestapp.Interface.Presenter;

public class BasePresenter<T extends MvpView> implements Presenter<T> {

    private T mMvpView;

    @Override
    public void attachView(T mvpView) {

        mMvpView = mvpView;

    }

    @Override
    public void detachView() {
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public T getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call FragmentPresenter.attachView(MvpView) before" +
                    " requesting data to the FragmentPresenter");
        }
    }
}
