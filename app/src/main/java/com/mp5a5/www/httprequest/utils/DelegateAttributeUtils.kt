package com.mp5a5.www.httprequest.utils

import com.trello.rxlifecycle3.components.support.RxAppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @describe
 * @author ：king9999 on 2018/6/28 15：37
 * @email：wangwenbinc@enn.cn
 */

fun <T> Observable<T>.switchSchedulers(activity: RxAppCompatActivity): Observable<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).compose(activity.bindToLifecycle())
}

