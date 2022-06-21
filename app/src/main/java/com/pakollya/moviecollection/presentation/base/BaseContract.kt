package com.pakollya.moviecollection.presentation.base

import android.content.Context

interface BaseContract {
    interface View {
        fun context(): Context?
    }

    interface Presenter<V : View?> {
        val view: V

        fun attachWithView(view: V)
        fun detachPresenter()
    }
}