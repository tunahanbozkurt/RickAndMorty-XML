package com.example.rickandmorty_xml.util

import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import androidx.core.view.isVisible
import com.example.rickandmorty_xml.databinding.LoadingScreenBinding

fun LoadingScreenBinding.setupLoadingScreen(isLoading: Boolean, durationMillis: Long = 1000) {
    this.loadingScreen.isVisible = isLoading
    this.loadingImage.rotateIndefinitely(isLoading, durationMillis)
}

fun ImageView.rotateIndefinitely(isActive: Boolean, durationMillis: Long = 1000) {
    if (isActive) {
        val anim = RotateAnimation(
            0f,
            359f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        anim.interpolator = LinearInterpolator()
        anim.repeatCount = Animation.INFINITE
        anim.duration = durationMillis

        this.startAnimation(anim)
    } else {
        this.animation = null
    }
}