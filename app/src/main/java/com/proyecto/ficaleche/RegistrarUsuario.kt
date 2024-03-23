package com.proyecto.ficaleche

import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.proyecto.ficaleche.Model.UsuariosModel

class RegistrarUsuario : AppCompatActivity() {

    private lateinit var EditTextIngresoCedulaUsuario: EditText
    private lateinit var EditTextIngresoNombreUsuario: EditText
    private lateinit var TextViewIngresoRutaUsuario: TextView
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_usuario)

        EditTextIngresoCedulaUsuario = findViewById(R.id.EditTextIngresoCedulaUsuario)
        EditTextIngresoNombreUsuario = findViewById(R.id.EditTextIngresoNombreUsuario)
        TextViewIngresoRutaUsuario = findViewById(R.id.TextViewIngresoRutaUsuario)

        reference = FirebaseDatabase.getInstance().getReference()

        val BotonAtras = findViewById<ImageView>(R.id.BotonAtrasRegistroUsuario)
        val items = listOf("Ruta 1","Ruta 2","Ruta 3")
        val adapter = ArrayAdapter(this, R.layout.list_item,items)
        val autoComplete: AutoCompleteTextView = findViewById(R.id.auto_complete_usuario)
        val BotonRegistro = findViewById<Button>(R.id.BotonRegistrarUsuario)

        val con = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = con.activeNetworkInfo

        BotonAtras.setOnClickListener {
            val intent = Intent(this, AccionesAdministrador::class.java)
            startActivity(intent)
            finish()
        }

        autoComplete.setAdapter(adapter)

        autoComplete.onItemClickListener = AdapterView.OnItemClickListener {
                parent, view, position, id ->

            val itemSelected = parent.getItemAtPosition(position)
            TextViewIngresoRutaUsuario.setText(itemSelected.toString())
        }

        BotonRegistro.setOnClickListener {
            if(networkInfo!=null && networkInfo.isConnected){
                SubirInformacion()
                val intent = Intent(this, ControlUsuarios::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this,"No hay conexión a internet\nVerifique el acceso a internet e intente nuevamente",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun SubirInformacion()
    {
        val Cedula = EditTextIngresoCedulaUsuario.text.toString()
        val Nombre = EditTextIngresoNombreUsuario.text.toString()
        val Ruta = TextViewIngresoRutaUsuario.text.toString()

        if(!Cedula.isEmpty())
        {
            if(!Nombre.isEmpty())
            {
                if(!Ruta.isEmpty())
                {
                    val map: MutableMap<String, Any> = HashMap()
                    map["Cedula"] = Cedula
                    map["Nombre"] = Nombre
                    map["Ruta"] = Ruta

                    reference.child("Usuarios").child(Cedula).setValue(map).addOnCompleteListener {
                        Toast.makeText(this, "Se ha creado un nuevo usuario", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, VentanaPrincipal::class.java)
                        startActivity(intent)
                        finish()
                    }.addOnFailureListener{
                        Toast.makeText(this, "Error al subir la información", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}