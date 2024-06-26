package com.proyecto.ficaleche.includes

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.proyecto.ficaleche.R

object MiToolbar {
    fun show(activity: AppCompatActivity, titulo: String?, upButton: Boolean) {
        val Toolbar = activity.findViewById<Toolbar>(R.id.toolbar)
        activity.setSupportActionBar(Toolbar)
        activity.supportActionBar!!.title = titulo
        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(upButton)
    }
}