15
https://raw.githubusercontent.com/Florizt/RxMVVM/master/rxmvvmlib/src/main/java/com/rx/rxmvvmlib/base/ICrashHandler.java
package com.rx.rxmvvmlib.base;

import android.content.Context;

/**
 * Created by wuwei
 * 2020/4/18
 * 佛祖保佑       永无BUG
 */
public interface ICrashHandler {
    void reportError(Context context, Throwable ex);
}
