1
https://raw.githubusercontent.com/pengfeigao/AgoraCallApi/master/call-plugin-api/src/main/java/com/basetools/net/rx/RxSchedulers.java
package com.basetools.net.rx;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public final class RxSchedulers {

    public static <T> ObservableTransformer<T, T> apply() {
        return upstream ->
                upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> ObservableTransformer<T, T> applyIO() {
        return upstream ->
                upstream.subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io());
    }

}