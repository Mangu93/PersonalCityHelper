package com.mangu.personalcityhelper.ui.base;


import rx.Observable;
import rx.Single;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BasePresenter<T extends MvpView> implements Presenter<T> {

    private final CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    private T mMvpView;

    @Override
    public void attachView(T mvpView) {
        mMvpView = mvpView;
        if (!mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription.clear();
        }

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

    public void addSubscription(Subscription subs) {
        mCompositeSubscription.add(subs);
    }

    private static class MvpViewNotAttachedException extends RuntimeException {
        MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }

    protected static class DataResult<T> {
        private T mData;
        private Throwable mError;

        public DataResult(T data) {
            mData = data;
        }

        public DataResult(Throwable error) {
            mError = error;
        }

        public Single<T> toSingle() {
            if (mError != null) {
                return Single.error(mError);
            }
            return Single.just(mData);
        }

        public Observable<T> toObservable() {
            if (mError != null) {
                return Observable.error(mError);
            }
            return Observable.just(mData);
        }
    }
}
