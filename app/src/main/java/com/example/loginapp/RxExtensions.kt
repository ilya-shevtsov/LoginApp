package com.example.loginapp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.Event
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import io.reactivex.disposables.Disposable

fun Disposable.disposeOnDestroy(lifecycle: Lifecycle) {
  lifecycle.addObserver(object : LifecycleEventObserver {
    override fun onStateChanged(source: LifecycleOwner, event: Event) {
      if (lifecycle.currentState <= Lifecycle.State.DESTROYED) {
        lifecycle.removeObserver(this)
        dispose()
      }
    }
  })
}

fun Disposable.disposeOnDestroy(lifecycleOwner: LifecycleOwner) =
  disposeOnDestroy(lifecycleOwner.lifecycle)
