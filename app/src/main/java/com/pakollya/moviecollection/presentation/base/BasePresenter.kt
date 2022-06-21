package com.pakollya.moviecollection.presentation.base

import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

abstract class BasePresenter<V : BaseContract.View?> : BaseContract.Presenter<V?> {

    private var mView: WeakReference<V>? = null
    var compositeDisposable: CompositeDisposable? = null
    override val view: V?
        get() = mView?.get()

    override fun attachWithView(view: V?) {
        mView = WeakReference(view)
        compositeDisposable = CompositeDisposable()
    }

    override fun detachPresenter() {
        if (mView != null && compositeDisposable != null) {
            compositeDisposable?.clear()
            compositeDisposable = null
            mView?.clear()
            mView = null
        }
    }
}