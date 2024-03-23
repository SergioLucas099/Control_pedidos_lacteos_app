package com.proyecto.ficaleche

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class InicioSesion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_sesion)

        val BotonAtrasInicioSesion = findViewById<ImageView>(R.id.BotonAtrasInicioSesion)
        val UsuarioEditText = findViewById<EditText>(R.id.UsuarioEditText)
        val ContaseñaEditText = findViewById<EditText>(R.id.ContaseñaEditText)
        val BotonIniciarSesion = findViewById<Button>(R.id.BotonIniciarSesion)

        BotonAtrasInicioSesion.setOnClickListener {
            val intent = Intent (this, VentanaPrincipal::class.java)
            startActivity(intent)
            finish()
        }

        BotonIniciarSesion.setOnClickListener{
            val preferences: SharedPreferences
            preferences = getSharedPreferences("typeUser", MODE_PRIVATE)
            val editor = preferences.edit()

            if(!UsuarioEditText.text.toString().equals("")){
                if(!ContaseñaEditText.text.toString().equals("")){
                    FirebaseAuth.getInstance()
                        .signInWithEmailAndPassword(UsuarioEditText.text.toString(), ContaseñaEditText.text.toString())
                        .addOnCompleteListener{
                            if(it.isSuccessful){
                                val intent = Intent(this, AccionesAdministrador::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                editor.putString("user", "Administrador")
                                editor.apply()
                                startActivity(intent)
                                finish()
                            }else{
                                Toast.makeText(this, "Este usuario no esta registrado", Toast.LENGTH_SHORT).show()
                            }
                        }
                }else{
                    Toast.makeText(this, "Debe ingresar la contraseña", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Debe ingresar el usuario", Toast.LENGTH_SHORT).show()
            }
        }
    }
}