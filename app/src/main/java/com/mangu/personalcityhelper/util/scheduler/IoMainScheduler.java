package com.mangu.personalcityhelper.util.scheduler;

import com.mangu.personalcityhelper.R;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rx.Observable;

/**
 * Created by lam on 2/6/17.
 */
public class IoMainScheduler<T> extends BaseScheduler<T> implements Observable.Transformer<String, R> {

    public IoMainScheduler() {
        super(Schedulers.io(), AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<R> call(Observable<String> stringObservable) {
        return null;
    }
}
