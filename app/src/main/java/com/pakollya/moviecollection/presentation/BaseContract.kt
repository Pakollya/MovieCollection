package com.pakollya.moviecollection.presentation

import android.content.Context

interface BaseContract {
    interface View {
        val context: Context?
    }

    interface Presenter<V : View?> {
        val view: V

        fun attachWithView(view: V)
        fun detachPresenter()
    }
}