package com.pakollya.moviecollection.presentation.base

import android.content.Context

interface BaseContract {
    interface View {
        fun getContext(): Context?
    }

    interface Presenter<V : View?> {
        val view: V

        fun attachWithView(view: V)
        fun detachPresenter()
    }
}