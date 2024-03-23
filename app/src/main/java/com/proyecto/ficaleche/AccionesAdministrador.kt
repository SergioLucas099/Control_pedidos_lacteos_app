package com.proyecto.ficaleche

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.proyecto.ficaleche.includes.MiToolbar

class AccionesAdministrador : AppCompatActivity() {

    var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acciones_administrador)

        MiToolbar.show(this, "Orlando Pulido y Yenny Yaya", false)

        val ControlRegistros = findViewById<LinearLayout>(R.id.ControlRegistros)
        val CrearUsuarios = findViewById<LinearLayout>(R.id.CrearUsuarios)
        val BVerUsuarios = findViewById<LinearLayout>(R.id.VerUsuarios)
        val RecuperarContraseña = findViewById<LinearLayout>(R.id.RecuperarContraseña)

        ControlRegistros.setOnClickListener {
            val intent = Intent(this, Control_Informacion::class.java)
            startActivity(intent)
            finish()
        }

        CrearUsuarios.setOnClickListener {
            val intent = Intent(this, ControlRegistro::class.java)
            startActivity(intent)
            finish()
        }

        BVerUsuarios.setOnClickListener {
            val intent = Intent(this, ControlUsuarios::class.java)
            startActivity(intent)
            finish()
        }

        RecuperarContraseña.setOnClickListener {
            val intent = Intent(this, RecuperarPassword::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_admin, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.salir) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Advertencia")
            builder.setMessage("¿Desea cerrar sesión?")
            builder.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which ->
                auth.signOut()
                val intent = Intent(this, InicioSesion::class.java)
                startActivity(intent)
            })
            builder.setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(this, "Acción Cancelada", Toast.LENGTH_SHORT).show()
            })
            builder.show()
        }
        return super.onOptionsItemSelected(item)
    }
}