package com.jatin.practiseandroid.classes

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.jatin.practiseandroid.R

object ProgressBar {
    private var spinner: CircularProgressIndicator? = null

    fun initializeSpinner(context: Context, container: View) {

        spinner = CircularProgressIndicator(context).apply {

            if (spinner != null) {

                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    foregroundGravity = android.view.Gravity.CENTER
                }

                indicatorSize = 100
                setIndicatorColor(ContextCompat.getColor(context, R.color.blue))
                isIndeterminate = true
            }
        }

        if (container is ViewGroup) {
            container.addView(spinner)
        }

    }

    fun showProgress() {
        spinner?.visibility = View.VISIBLE
    }

    fun hideProgress() {
        spinner?.visibility = View.GONE
    }

}