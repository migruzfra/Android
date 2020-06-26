package es.iessaladillo.miguelangelruz.androrecetas.base

import androidx.appcompat.widget.Toolbar

interface OnToolbarAvailableListener {
    fun onToolbarCreated(toolbar: Toolbar)
    fun onToolbarDestroyed()
}