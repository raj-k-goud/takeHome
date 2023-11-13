package com.raj.takehome.utils

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.raj.takehome.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineExceptionHandler

object Utility {

    fun loadProfileImage(url: String?, context: Context, view: ImageView) {
        url?.let {
            Picasso.with(context)
                .load(it)
                .into(view)
        }
        animateView(context, view)
    }

    fun animateView(context: Context, view: View) {
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
    }

     val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        //Handle when any exception thrown, so that app will not freeze
        //Main/parent Coroutine will wait until all the childrens complete their task
        // print to LOG ("CoroutineExceptionHandler got $exception")
    }

}