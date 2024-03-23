package com.proyecto.ficaleche

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class ControlRegistro : AppCompatActivity() {

    lateinit var bottomNav2 : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control_registro)
        loadFragment(AgregarUsuario())
        bottomNav2 = findViewById(R.id.bottomNav2) as BottomNavigationView
        bottomNav2.setOnItemSelectedListener {
            when(it.itemId){
                R.id.CrearProveedor -> {
                    loadFragment(AgregarUsuario())
                }
                R.id.CrearAdmin -> {
                    loadFragment(AgregarAdmin())
                }
            }
            true
        }
    }
    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.commit()
    }
}