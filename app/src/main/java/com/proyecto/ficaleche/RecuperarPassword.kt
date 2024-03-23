package com.proyecto.ficaleche

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RecuperarPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_password)

        val BotonAtrasRecuperarContraseña = findViewById<ImageView>(R.id.BotonAtrasRecuperarContraseña)
        val EditTextCorreoRecuperar = findViewById<EditText>(R.id.EditTextCorreoRecuperar)
        val BotonRecuperarContraseña = findViewById<Button>(R.id.BotonRecuperarContraseña)

        BotonAtrasRecuperarContraseña.setOnClickListener {
            val intent = Intent(this, AccionesAdministrador::class.java)
            startActivity(intent)
            finish()
        }

        BotonRecuperarContraseña.setOnClickListener {
            FirebaseAuth.getInstance().setLanguageCode("es")
            if(EditTextCorreoRecuperar.text.toString().isNotEmpty()){
                FirebaseAuth.getInstance().sendPasswordResetEmail(EditTextCorreoRecuperar.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(this, "Se le ha enviado un mensaje a su correo", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@RecuperarPassword, AccionesAdministrador::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this, "Correo ingresado no válido", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this, "Ingrese un correo", Toast.LENGTH_SHORT).show()
            }
        }
    }
}